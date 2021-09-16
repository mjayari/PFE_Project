package com.fst.myapplication;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.fst.myapplication.db.DatabaseHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.RequiresApi;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.fst.myapplication.databinding.ActivityMainBinding;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ServerSocket;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    /*protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String Fpath = data.getDataString();
        Log.d("log","Fpath=" + Fpath);

        //TODO handle your request here
        super.onActivityResult(requestCode, resultCode, data);
    }*/


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //DatabaseHelper db = new DatabaseHelper(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_connexion, R.id.nav_configuration , R.id.nav_Filetranfer,R.id.nav_Administration)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        Runnable runnable = new MyRunnable(); // or an anonymous class, or lambda...

        /*Thread thread = new Thread(runnable);
        thread.start();*/

        /*if ( getApplication().checkCallingOrSelfPermission(Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED ) {
            requestPermissions(new String[] {Manifest.permission.READ_CONTACTS}, 1);
        }*/
        /*
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
        String ip = Formatter.formatIpAddress(ipAddress);
        Log.d("log", "IP address: " + ip);*/

        getPublicIP();

        int port = 12345;
        /*File wwwroot = new File("/storage/1B07-2C1C/DCIM/").getAbsoluteFile();
        try {
            new NanoHTTPD( port, wwwroot );
            Log.d("log", "Server is running: " + wwwroot);
        }
        catch( IOException ioe ) {
            System.err.println( "Couldn't start server:\n" + ioe );
            System.exit( -1 );
        }*/

        port = 12345;
        //File wwwroot = new File(".").getAbsoluteFile();
        //File wwwroot = new File("/storage/emulated/0/DCIM").getAbsoluteFile();
        File wwwroot = new File("/storage/emulated/0/").getAbsoluteFile();

        try {
            new com.fst.myapplication.server.NanoHTTPD( port, wwwroot );
            Log.d("log", "Server is running: " + wwwroot);
        } catch( IOException ioe ) {
            System.err.println( "Couldn't start server:\n" + ioe );
            System.exit( -1 );
        }

        //SimpleWebServer.startServer(new String[]{});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public class MyRunnable implements Runnable {
        public void run() {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 1) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(MainActivity.this, "permission granted", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(MainActivity.this, "permission denied", Toast.LENGTH_SHORT).show();
        }
    }


    private void getPublicIP() {
        ArrayList<String> urls=new ArrayList<String>(); //to read each line

        new Thread(new Runnable(){
            public void run(){
                //TextView t; //to show the result, please declare and find it inside onCreate()

                try {
                    // Create a URL for the desired page
                    URL url = new URL("https://api.ipify.org/"); //My text file location
                    //First open the connection
                    HttpURLConnection conn=(HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(60000); // timing out in a minute

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    //t=(TextView)findViewById(R.id.TextView1); // ideally do this in onCreate()
                    String str;
                    while ((str = in.readLine()) != null) {
                        urls.add(str);
                    }
                    in.close();
                } catch (Exception e) {
                    Log.d("MyTag",e.toString());
                }

                //since we are in background thread, to post results we have to go back to ui thread. do the following for that

                MainActivity.this.runOnUiThread(new Runnable(){
                    public void run(){
                        try {
                            Toast.makeText(MainActivity.this, "Public IP:"+urls.get(0), Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(MainActivity.this, "TurnOn wiffi to get public ip", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        }).start();

    }

    public void starServer(){
        int port = 80;
        File wwwroot = new File(".").getAbsoluteFile();

        try {
            new com.fst.myapplication.server.NanoHTTPD( port, wwwroot );
        } catch( IOException ioe ) {
            System.err.println( "Couldn't start server:\n" + ioe );
            System.exit( -1 );
        }
    }

}