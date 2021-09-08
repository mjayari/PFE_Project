package com.fst.myapplication;
import static android.content.Context.WIFI_SERVICE;

import android.content.Context;
import android.database.Cursor;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.fst.myapplication.db.Configuration;
import com.fst.myapplication.db.DatabaseHelper;
import com.fst.myapplication.ui.Configuration.ConfigurationFragment;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.ByteOrder;

public class HttpServer {


    static EditText editTextPortNumber;
    static int portNumber;

    private Fragment fragment;
    ServerSocket serverSocket;

    public HttpServer(Fragment fragment) {
        this.fragment = fragment;
    }

    public void startServer(){

        DatabaseHelper db = new DatabaseHelper(this.fragment);

        Configuration config = db.getConfiguration(1);
        int port = config.getPortNumber();
        Toast.makeText(this.fragment.getContext(), "portNumber = " + port, Toast.LENGTH_SHORT).show();

        port = 12345;
        //String ip = wifiIpAddress(this.fragment.getContext());
        //Log.d("log", "ip address = " + ip);

        try {
            ServerListenerThread serverListener = new ServerListenerThread(port);
            serverListener.start();

        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            Log.d("log", "Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }


        /*Runnable runnable = new MyRunnable(); // or an anonymous class, or lambda...
        Thread thread3 = new Thread(runnable);
        thread3.start();*/
    }

    class ServerListenerThread extends Thread {
        private int port;

        public ServerListenerThread(int port) throws IOException {
            this.port=port;
            serverSocket = new ServerSocket(port) ;
            Log.d("log", "Server is running on port: " + port);
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
                        "This page was served using my Simple Java HTTP Server" +
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

    class MyRunnable implements Runnable {
        public void run() {

        }
    }

    protected String wifiIpAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();

        // Convert little-endian to big-endianif needed
        if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
            ipAddress = Integer.reverseBytes(ipAddress);
        }

        byte[] ipByteArray = BigInteger.valueOf(ipAddress).toByteArray();

        String ipAddressString;
        try {
            ipAddressString = InetAddress.getByAddress(ipByteArray).getHostAddress();
        } catch (UnknownHostException ex) {
            Log.e("WIFIIP", "Unable to get host address.");
            ipAddressString = null;
        }

        return ipAddressString;
    }

}

