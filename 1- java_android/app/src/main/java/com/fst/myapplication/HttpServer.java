package com.fst.myapplication;
import static android.content.Context.WIFI_SERVICE;

import android.content.Context;
import android.database.Cursor;
import android.net.wifi.WifiManager;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.fst.myapplication.db.Configuration;
import com.fst.myapplication.db.DatabaseHelper;
import com.fst.myapplication.ui.Configuration.ConfigurationFragment;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {


    static EditText editTextPortNumber;
    static int portNumber;

    private Fragment fragment;
    public ServerSocket serverSocket;
    public boolean status = false;
    public int port = 12345;

    public HttpServer(Fragment fragment) {
        this.fragment = fragment;
    }

    public void startServer(){

        DatabaseHelper db = new DatabaseHelper(this.fragment);
        //Cursor cursor = db.alldata();

        //System.out.println("server Starting...");

        //Configuration.getInstance().loadConfiguration("/storage/file");

        //System.out.println("Using port: " + conf.getPort());
        //System.out.println("Using webroot " + conf.getwebroot());
        //portNumber = Integer.parseInt (editTextPortNumber.getText().toString());

        Configuration config = db.getConfiguration(1);
        //int port = config.getPortNumber();
        int port = 12345;
        Toast.makeText(this.fragment.getContext(), "portNumber = " + port, Toast.LENGTH_SHORT).show();
        Log.d("log","portNumber:" + port);

        /*try{

            ServerListenerThread server = new ServerListenerThread(port);
            Log.d("log","Server is running on port: " + port);
        }
        catch (IOException ioException){
            String error = ioException.getMessage();
            Log.d("log","Exception: " + error);

        } finally {
            try {
                serverSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }*/
        try {
            ServerListenerThread serverListener = new ServerListenerThread(port);
            serverListener.start();


        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            Log.d("log", "Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }



    }

    /*class ServerListenerThread extends Thread {
        private int port;

        public ServerListenerThread(int port) throws IOException {
            this.port = port;
            serverSocket = new ServerSocket(port);
            Log.d("log", "Server is running on port: " + port);
        }
    }

    class HttpConnectionThread extends Thread {
        private Socket socket;

        public HttpConnectionThread(Socket s) {
            this.socket = s;
        }
    }*/
    class ServerListenerThread extends Thread {
        private int port;

        public ServerListenerThread(int port) throws IOException {
            this.port=port;
            serverSocket = new ServerSocket(port) ;
            Log.d("log", "Server is running on port: " + port);
            status = true;
        }
        public void run() {
            try {
                while (serverSocket.isBound() && !serverSocket.isClosed()) {
                    //while (true) {
                    Socket socket = serverSocket.accept();
                    Log.d("log", "New Connection accepted :" + socket.getInetAddress());

                    HttpConnectionThread httpConnectionThread = new HttpConnectionThread(socket);
                    httpConnectionThread.start();

                }

            } catch (IOException ex) {
                System.out.println("Server exception: " + ex.getMessage());
                Log.d("log", "Server exception: " + ex.getMessage());
                ex.printStackTrace();
            } finally {
                try {
                    serverSocket.close();
                    Log.d("log", "ServerSocket closed!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class HttpConnectionThread extends Thread {
        private Socket socket;

        public HttpConnectionThread(Socket s){
            this.socket=s;
        }

        public void run() {
            InputStream is=null;
            OutputStream os=null;

            try {
                is = socket.getInputStream();
                os = socket.getOutputStream();

                String html = "<html><head><title>" +
                        "Simple Java HTTP Server" +
                        "</title></head>" +
                        "<body>" +
                        "<h1>" +
                        "This page was served using my Simple Java HTTP Server 1-DEV" +
                        "</h1>" +
                        "</body>" +
                        "</html>";

                final String CRLF="\n\r";
                String response = "HTTP/1.1 200 OK" + CRLF
                        + "Content-Length: " + html.getBytes().length + CRLF
                        + CRLF
                        + html
                        + CRLF + CRLF ;

                os.write(response.getBytes());
                Log.d("log", "Response returned to the browser!");

            } catch (IOException ex) {
                System.out.println("Server exception: " + ex.getMessage());
                Log.d("log", "Server exception: " + ex.getMessage());
                ex.printStackTrace();

            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    socket.close();
                    Log.d("log", "Socket closed!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String wifiIpAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();

        return Formatter.formatIpAddress(ipAddress);
    }
}


