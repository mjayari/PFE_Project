package com.fst.myapplication;
import android.database.Cursor;
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
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {


    static EditText editTextPortNumber;
    static int portNumber;

    private Fragment fragment;

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
        int port = 123456;
        Toast.makeText(this.fragment.getContext(), "portNumber = " + port, Toast.LENGTH_SHORT).show();
        Log.d("log","portNumber:" + port);

        Thread bgThread = new Thread() {
            public void run() {
                try {
                    ServerSocket serverSocket = new ServerSocket(port);
                    Socket socket = serverSocket.accept();
                    Log.d("log","Server is running on port:" + port);

                    serverSocket.close();


                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("log","Exception:" + e.getMessage());

                }


            }
        };
        //bgThread.start();

        Runnable runnable = new MyRunnable();
        Thread thread = new Thread(runnable);
        thread.start();


    }



    public class MyRunnable implements Runnable {
        public void run() {
            int port = 12346;
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                //Socket socket = serverSocket.accept();
                Log.d("log","Server is running on port:" + port);

               /* InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();

             String html ="<html> <head><title> HTTP Server </title></head><body><h1> Welcome to server </h1></body> </html>";

             final String CRLF="/n/r";

             String response = "HTTP/1.1 200 OK" + CRLF + "content-length" + html.getBytes().length + CRLF +
                     CRLF +
                     html +
                     CRLF + CRLF;

             outputStream.write(response.getBytes());

             inputStream.close();
             outputStream.close();*/
             //socket.close();

             serverSocket.close();


            } catch (IOException e) {
                e.printStackTrace();
                Log.d("log","Exception:" + e.getMessage());

            }

        }
    }
}


