package com.fst.myapplication;
import android.database.Cursor;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.fst.myapplication.db.Configuration;
import com.fst.myapplication.db.DatabaseHelper;
import com.fst.myapplication.ui.Configuration.ConfigurationFragment;

import java.io.IOException;
import java.net.ServerSocket;

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

        System.out.println("server Starting...");

        //Configuration.getInstance().loadConfiguration("/storage/file");

        //System.out.println("Using port: " + conf.getPort());
        //System.out.println("Using webroot " + conf.getwebroot());
        //portNumber = Integer.parseInt (editTextPortNumber.getText().toString());

        Configuration config = db.getConfiguration(1);
        int pn = config.getPortNumber();
        Toast.makeText(this.fragment.getContext(), "portNumber = " + pn, Toast.LENGTH_SHORT).show();


        /*try {
            ServerSocket serverSocket = new ServerSocket();
            //ServerSocket = ServerSocket.class;


        } catch (IOException e) {
            e.printStackTrace();
        }*/


    }
}
