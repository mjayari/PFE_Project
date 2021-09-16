package com.fst.myapplication;
import static android.content.Context.WIFI_SERVICE;

import android.content.Context;
import android.database.Cursor;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.fst.myapplication.db.Configuration;
import com.fst.myapplication.db.DatabaseHelper;
import com.fst.myapplication.ui.Configuration.ConfigurationFragment;
import com.google.gson.JsonObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class HttpServer {


    static EditText editTextPortNumber;
    static int portNumber;

    private Fragment fragment;
    public ServerSocket serverSocket;
    public boolean status = false;
    public int   port = 12345;
    //public String port = editTextPortNumber.getText().toString();

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

        //Configuration config = db.getConfiguration(1);
        //int port = config.getPortNumber();
        int port = 12345;

        Toast.makeText(this.fragment.getContext(), "portNumber = " +port , Toast.LENGTH_SHORT).show();
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



    /*public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress().toUpperCase();
                        boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 port suffix
                                return delim<0 ? sAddr : sAddr.substring(0, delim);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }*/


    public Void urlConnect(String weburl) {
            Log.d("log","url : " + weburl);
            HttpURLConnection urlConnection = null;

                try {
                    URL url = new URL(weburl);
                    urlConnection = (HttpURLConnection) url.openConnection();

                    int code = urlConnection.getResponseCode();
                    Log.d("log","code: " + code);
                    if (code != 200) {
                        throw new IOException("Invalid response from server: " + code);
                    }

                    BufferedReader rd = new BufferedReader(new InputStreamReader(
                            urlConnection.getInputStream()));
                    String line;
                    while ((line = rd.readLine()) != null) {
                        Log.d("log","Line:" +line);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("log","Exception: " + e.getMessage());
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

                return null;
        }



       /* private static class HTTPReqTask1 extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... params) {
                HttpURLConnection urlConnection = null;


                try {
                    JsonObject postData = new JsonObject();
                    postData.addProperty("name", "morpheus");
                    postData.addProperty("job", "leader");

                    URL url = new URL("https://reqres.in/api/users");
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestProperty("Content-Type", "application/json");
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setDoOutput(true);
                    urlConnection.setDoInput(true);
                    urlConnection.setChunkedStreamingMode(0);

                    OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                            out, "UTF-8"));
                    writer.write(postData.toString());
                    writer.flush();

                    int code = urlConnection.getResponseCode();
                    if (code !=  201) {
                        throw new IOException("Invalid response from server: " + code);
                    }

                    BufferedReader rd = new BufferedReader(new InputStreamReader(
                            urlConnection.getInputStream()));
                    String line;
                    while ((line = rd.readLine()) != null) {
                        Log.i("data", line);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

                return null;
            }
        }*/


    }







    



