package com.fst.myapplication;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.fst.myapplication.db.DatabaseHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.fst.myapplication.databinding.ActivityMainBinding;

import java.io.IOException;
import java.net.ServerSocket;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    public String ipAdress = null;

    /*protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String Fpath = data.getDataString();
        Log.d("log","Fpath=" + Fpath);

        //TODO handle your request here
        super.onActivityResult(requestCode, resultCode, data);
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //DatabaseHelper db = new DatabaseHelper(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        /*binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        })*/;
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

        int port = 12345;
        Thread bgThread = new Thread() {
            public void run() {
            }
        };
        //bgThread.start();

        Runnable runnable = new MyRunnable();
        Thread thread = new Thread(runnable);
        //thread.start();

       /* WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        //.setText("Your Device IP Address: " + ipAddress);
        Log.d("log","Ip Address = " +ipAddress);
        this.ipAdress = ipAddress;*/

        new HTTPReqTask().execute();


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
            int port = 12345;
                    try {
                        ServerSocket serverSocket = new ServerSocket(port);
                        //Socket socket = serverSocket.accept();
                        Log.d("log","Server is running on port:" + port);

                        serverSocket.close();


                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.d("log","Exception:" + e.getMessage());

                    }

        }
    }

    private class HTTPReqTask {

        public void execute() {
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

}