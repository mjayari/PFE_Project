package com.fst.myapplication.ui.Filetransfer;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fst.myapplication.HttpServer;
import com.fst.myapplication.databinding.FragmentFiletransferBinding;
import com.fst.myapplication.db.Configuration;
import com.fst.myapplication.db.DatabaseHelper;
import com.fst.myapplication.http.HttpFileDownloader;
import com.fst.myapplication.http.HttpUrlOpener;
import com.fst.myapplication.ui.Configuration.ConfigurationFragment;
import com.fst.myapplication.ui.Home.HomeFragment;

import java.io.File;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class FiletransferFragment extends Fragment {

    private FiletransferViewModel filetransferViewModel;
    public static FragmentFiletransferBinding binding;

    private List<String> downloadFileList = new ArrayList<String>();
    private Hashtable <String,String> downloadFileUrlTable;
    private Integer selectedDownloadFileIndex = -1 ;

    public List<String> uploadFileList = new ArrayList<String>();
    public Hashtable <String,String> uploadFileUrlTable;
    public Integer selectedUploadFileIndex = -1 ;

    public static DatabaseHelper db;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        filetransferViewModel =
                new ViewModelProvider(this).get(FiletransferViewModel.class);

       binding = FragmentFiletransferBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

       /* final TextView textView = binding.textHome;
        filetransferViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        return root;
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        HttpServer server = new HttpServer(this);
        db = new DatabaseHelper(this);

        ListView downloadListView = binding.downloadListView;
        ListView uploadListView = binding.uploadListView;

        downloadFileList = new ArrayList<String>();
        downloadFileUrlTable = new Hashtable<String,String>();

        binding.ConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = binding.editServerAddress.getText().toString() + "?request=upload";

                //server.urlConnect(url);
                //url = "http://192.168.1.23:12345";
                Log.d("log","url:" + url);

                new Thread(){
                    public void run(){

                        HttpUrlOpener huo = new HttpUrlOpener(binding);
                        huo.setFileTransferFragment(FiletransferFragment.this);
                        huo.urlConnect(url);

                    }
                }.start();



            }
        });

        binding.downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String fileURL = "http://192.168.1.2:12345/Camera/20210915_160855.mp4";
                //String fileURL = "http://10.0.2.16:12345/InterfacePrincipale.mp4";

                String selectedFileName = uploadListView.getItemAtPosition(selectedUploadFileIndex).toString();
                String selectedFileUrl = uploadFileUrlTable.get(selectedFileName);

                //String saveDir = "/storage/emulated/0/DCIM";
                String saveDir = ConfigurationFragment.config.getDownloadPath();

                new Thread() {
                    public void run() {
                        try {
                            //sleep(5000);
                            new HttpFileDownloader(binding).downloadFile(selectedFileUrl, saveDir);

                        } catch (Exception e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                            Log.d("log","Exception" + e1.getMessage());
                        }

                    }
                }.start();


            }
        });

        binding.uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedFileName = downloadListView.getItemAtPosition(selectedDownloadFileIndex).toString();
                String selectedFileUrl = downloadFileUrlTable.get(selectedFileName);

                //String weburl = "http://10.0.2.16:8080/?request=upload&fileUrl=http://10.0.2.16:8080/Download/validatepassword.mp4";
                //String weburl = "http://10.0.2.16:8080/?request=upload&fileUrl=" + selectedFileUrl;
                String serverAddress = binding.editServerAddress.getText().toString();
                String weburl = serverAddress + "/?request=upload&fileUrl=" + selectedFileUrl;

                Log.d("log", "RequestWeburl = " + weburl);

                new Thread(){
                    public void run(){
                        try {
                            URL url = new URL(weburl);
                            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                            urlConnection.getResponseCode();
                        } catch (Exception e) {

                        }
                    }
                }.start();

            }
        });

        binding.refrechDownloadLocalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Configuration configuration = db.getConfiguration(1);
                String downloadPath = configuration.getDownloadPath();
                //String downloadPath = "/storage/emulated/0/DCIM";

                downloadFileList = new ArrayList<String>();
                downloadFileUrlTable = new Hashtable<String, String>();

                File folder = new File(downloadPath);
                File[] listOfFiles = folder.listFiles();

                String fileList = "";
                for (int i = 0; i < listOfFiles.length; i++) {
                    if (listOfFiles[i].isFile()) {
                        System.out.println("File " + listOfFiles[i].getName());
                        Log.d("log","File" + listOfFiles[i].getName());
                        fileList += listOfFiles[i].getName() + "\n";
                        downloadFileList.add(listOfFiles[i].getName());
                        downloadFileUrlTable.put(listOfFiles[i].getName(),
                                "http://" + ConfigurationFragment.ipAdress + ":8080/Download/" + listOfFiles[i].getName());
                    } else if (listOfFiles[i].isDirectory()) {
                        System.out.println("Directory " + listOfFiles[i].getName());
                        Log.d("log","Directory" + listOfFiles[i].getName());

                    }
                }
                Log.d("log","List :" + downloadFileList);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        FiletransferFragment.super.getContext(),
                        android.R.layout.simple_list_item_1,
                        downloadFileList );

                //binding.downloadPathLocalText.setText(fileList);

                downloadListView.setAdapter(arrayAdapter);

                downloadListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    // argument position gives the index of item which is clicked
                    public void onItemClick(AdapterView<?> arg0, View v,int position, long arg3)
                    {
                        int itemPosition = position;
                        selectedDownloadFileIndex = itemPosition;

                        // ListView Clicked item value    public void changeItemBackgroundColor(int itemPosition) {
                        String itemValue = (String) downloadListView.getItemAtPosition(position);

                        Toast.makeText(FiletransferFragment.super.getContext(),"File Selected : " + itemValue
                                        + "File Url = " + downloadFileUrlTable.get(itemValue)
                                , Toast.LENGTH_LONG).show();
                        Log.d("log","File Url : " + downloadFileUrlTable.get(itemValue));

                        changeDownloadBackgroundColor( v,itemPosition);


                    }
                });

            }
        });


        binding.refrechUploadRemoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*uploadFileList = new ArrayList<String>();
                uploadFileList.add("file 1");
                uploadFileList.add("file 2");*/

                Log.d("log","uploadFileList :" + uploadFileList);
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                        FiletransferFragment.super.getContext(),
                        android.R.layout.simple_list_item_1,
                        uploadFileList );

                uploadListView.setAdapter(arrayAdapter);

                uploadListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    // argument position gives the index of item which is clicked
                    public void onItemClick(AdapterView<?> arg0, View v,int position, long arg3)
                    {
                        int itemPosition = position;
                        selectedUploadFileIndex = itemPosition;

                        // ListView Clicked item value    public void changeItemBackgroundColor(int itemPosition) {
                        String itemValue = (String) uploadListView.getItemAtPosition(position);

                        Toast.makeText(FiletransferFragment.super.getContext(),
                                    "File Selected : " + itemValue
                                        + "File Url = " + uploadFileUrlTable.get(itemValue)
                                                , Toast.LENGTH_LONG).show();

                        Log.d("log","File Url : " + uploadFileUrlTable.get(itemValue));

                        changeUploadBackgroundColor( v,itemPosition);


                    }
                });

            }
        });


    }

    public void changeDownloadBackgroundColor(View v , int itemPosition) {
        ListView listView = binding.downloadListView;

        for (int i = 0; i < listView.getChildCount(); i++) {
            listView.getChildAt(i).setBackgroundColor(Color.WHITE);
        }
        v.setBackgroundColor(Color.BLUE);

    }

    public void changeUploadBackgroundColor(View v , int itemPosition) {
        ListView listView = binding.uploadListView;

        for (int i = 0; i < listView.getChildCount(); i++) {
            listView.getChildAt(i).setBackgroundColor(Color.WHITE);
        }
        v.setBackgroundColor(Color.BLUE);

    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}