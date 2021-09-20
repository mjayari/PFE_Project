package com.fst.myapplication.ui.Configuration;

import static android.content.Context.WIFI_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.fst.myapplication.HttpServer;
import com.fst.myapplication.MainActivity;
import com.fst.myapplication.databinding.FragmentConfigurationBinding;
import com.fst.myapplication.databinding.FragmentConfigurationBinding;
import com.fst.myapplication.db.Configuration;
import com.fst.myapplication.db.Connexion;
import com.fst.myapplication.db.DatabaseHelper;
import com.fst.myapplication.db.User;
import com.fst.myapplication.http.NanoHTTPD;

import java.io.File;
import java.io.IOException;

public class ConfigurationFragment extends Fragment {

    private ConfigurationViewModel configurationViewModel;
    private FragmentConfigurationBinding binding;

    DatabaseHelper db;
    NanoHTTPD server2;
    Button SaveSettingButton,find_upload_button ,find_download_button;
    EditText TextEditUploadPath,TextEditDownloadPath;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        configurationViewModel =
                new ViewModelProvider(this).get(ConfigurationViewModel.class);

        binding = FragmentConfigurationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        //final TextView textView = binding.textSlideshow;
        configurationViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //textView.setText(s);
            }
        });
        return root;
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DatabaseHelper db = new DatabaseHelper(this);
        HttpServer server = new HttpServer(this);

        int portNumber = 8080;
        String downloadPath = "/storage/emulated/0/Upload";
        String uploadPath = "/storage/emulated/0/Download";

        binding.editTextPortNumber.setText(String.valueOf(portNumber));
        binding.TextEditUploadPath.setText(downloadPath);
        binding.TextEditDownloadPath.setText(uploadPath);

        Configuration configuration = db.getConfiguration(1);
        if (configuration != null) {
            portNumber = configuration.getPortNumber();
            downloadPath = configuration.getDownloadPath();
            uploadPath = configuration.getUploadsPath();

            binding.editTextPortNumber.setText(String.valueOf(portNumber));
            binding.TextEditUploadPath.setText(uploadPath);
            binding.TextEditDownloadPath.setText(downloadPath);
        }


        Context context = ConfigurationFragment.super.getContext();
        /*WifiManager wifiManager = (WifiManager) context.getSystemService(WIFI_SERVICE);
        String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
        .setText("Your Device IP Address: " + ipAddress);
        Log.d("log","Ip Address = " +ipAddress);*/

        String ipAdress = server.wifiIpAddress(context);
        Log.d("log","Ip Address: " +ipAdress);
        binding.LocalIpAddressText.setText("http://" + ipAdress +":"+ portNumber);
        Log.d("log","http:// " + ipAdress + ":" + portNumber);



        binding.SaveSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int portNumber;
                String uploadPath;
                String downloadPath;
                Log.d("log", "portNumber =" + binding.editTextPortNumber.getText());
                if (binding.editTextPortNumber.getText() == null) {
                    Toast.makeText(ConfigurationFragment.super.getContext(), "port Number must be specified", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    portNumber = Integer.parseInt(binding.editTextPortNumber.getText().toString());
                    //portNumber =binding.editTextPortNumber.getText().toString();
                    //portNumber = binding.editTextPortNumber.getText().length();
                    uploadPath = binding.TextEditUploadPath.getText().toString();
                    downloadPath = binding.TextEditDownloadPath.getText().toString();
                    //Log.d("log", "portNumber =" + portNumber);

                    binding.LocalIpAddressText.setText("http://" + ipAdress +":"+ portNumber);

                     /*NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);*/
                    Toast.makeText(ConfigurationFragment.super.getContext(), "Port Number : " + binding.editTextPortNumber.getText() , Toast.LENGTH_SHORT).show();
                    if (db.getConfigurationRowsNumber() > 0) {
                        db.updateConfiguration(portNumber, uploadPath, downloadPath);
                    } else
                        db.addConfiguration(new Configuration(1, portNumber, uploadPath, downloadPath));
                    Log.d("log","RowsNumber:" + db.getConfigurationRowsNumber());
                }
            }
        });


        binding.findUploadButton.setOnClickListener(new View.OnClickListener() {
            private static final int SELECT_FILE = 0;

            @Override
            public void onClick(View v) {




                /*String path = Environment.getExternalStorageDirectory() + "/" + "Directory" + "/";
                Uri uri = Uri.parse(path);
                Intent intent = new Intent(Intent.ACTION_PICK);*/
                //intent.setDataAndType(uri, "*/*");
                //startActivity(intent);
                /*int PICKFILE_REQUEST_CODE = 100;
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("file/*");
                startActivityForResult(intent, PICKFILE_REQUEST_CODE);
                //String FilePath = intent.getData().getPath();
                startActivity(intent);*/
                //Log.d("log","FilePath=" + FilePath);
                Intent chooser = new Intent(Intent.ACTION_GET_CONTENT);
                Uri uri = Uri.parse(Environment.getDownloadCacheDirectory().getPath().toString());
                chooser.addCategory(Intent.CATEGORY_OPENABLE);
                chooser.setDataAndType(uri, "*/*");
// startActivity(chooser);
                try {
                    startActivityForResult(chooser, SELECT_FILE);
                }
                catch (android.content.ActivityNotFoundException ex)
                {
                    Toast.makeText(ConfigurationFragment.super.getContext(), "Please install a File Manager", Toast.LENGTH_SHORT).show();
                    
                }

            }

        });

        binding.StartServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*server.startServer();
                if(server.status){
                    binding.ServerStatusText.setText("Server Status: Running!");

                } else
                    binding.ServerStatusText.setText("Server Status: Closed!");*/


                if ( ConfigurationFragment.super.getActivity().checkCallingOrSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED ) {
                    requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1);

                }

                if ( ConfigurationFragment.super.getActivity().checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED ) {
                    requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                }

                //NanoHTTPD.startServer(new String[]{});
                //int port = 12345;
                Configuration configuration = db.getConfiguration(1);

                int port = configuration.getPortNumber();
                //int port = Integer.parseInt(binding.editTextPortNumber.getText().toString());

                //File wwwroot = new File("C:\\+Backup\\Journal").getAbsoluteFile();
                File wwwroot = new File("/storage/emulated/0/").getAbsoluteFile();
                //File wwwroot = new File("/storage/101F-3807/").getAbsoluteFile();
                Log.d("log","port:" + port);
                Log.d("log","root:" + wwwroot.getAbsolutePath());

                try
                {
                    server2 = new NanoHTTPD( port, wwwroot );
                    server2.status=true;
                    binding.ServerStatusText.setText("Server Status: Running!");

                }
                catch( IOException ioe )
                {
                    System.err.println( "Couldn't start server:\n" + ioe );
                    System.exit( -1 );
                    server2.status=false;
                    binding.ServerStatusText.setText("Server Status: Stopped!");
                }

                System.out.println( "Now serving files in port " + port + " from \"" + wwwroot + "\"" );
                Log.d("log", "Now serving files in port " + port + " from \"" + wwwroot + "\""  );


                /*textView = (TextView) findViewById(R.id.android_device_ip_address);
                WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
                String ipAddress = Formatter.formatIpAddress(wifiManager.getConnectionInfo().getIpAddress());
                textView.setText("Your Device IP Address: " + ipAddress);*/


                /*Thread bgThread = new Thread() {
                    public void run() {
                        server.startServer();
                    }
                };
                bgThread.start();*/
            }
        });
                /*protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                if (requestCode == 100) {
                String folderPath = intent.getDataString();
                //TODO
                return;
                }
                super.onActivityResult(requestCode, resultCode, data);}*/

        binding.StopServerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*server.serverSocket.isClosed();
                if(! server.serverSocket.isClosed()){
                    try {
                        server.serverSocket.close();
                        binding.ServerStatusText.setText("Server Status: Stoped!");

                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                        Log.d("log","Exception: " +ioException.getMessage());
                    }
                }*/
                server2.stop();
                if(server2.status)
                    binding.ServerStatusText.setText("Server Status: Running!");
                else
                    binding.ServerStatusText.setText("Server Status: Stopped!");




            }
        });

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}