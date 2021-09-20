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
import com.fst.myapplication.ui.Home.HomeFragment;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class FiletransferFragment extends Fragment {

    private FiletransferViewModel filetransferViewModel;
    private FragmentFiletransferBinding binding;

    private List<String> downloadFileList;
    private Hashtable <String,String> downloadFileUrlTable;
    private Integer selectedDownloadFileIndex = -1 ;


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
        DatabaseHelper db = new DatabaseHelper(this);

        ListView listView = binding.listView;

        downloadFileList = new ArrayList<String>();
        downloadFileUrlTable = new Hashtable<String,String>();

        binding.ConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = binding.editServerAddress.getText().toString();

                //server.urlConnect(url);
                //url = "http://192.168.1.23:12345";
                Log.d("log","url:" + url);

                new Thread(){
                    public void run(){

                        new HttpUrlOpener(binding).urlConnect(url);


                    }
                }.start();



            }
        });

        binding.downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String fileURL = "http://192.168.1.2:12345/Camera/20210915_160855.mp4";
                //String fileURL = "http://10.0.2.16:12345/InterfacePrincipale.mp4";

                String selectedFileName = listView.getItemAtPosition(selectedDownloadFileIndex).toString();
                String selectedFileUrl = downloadFileUrlTable.get(selectedFileName);

                String saveDir = "/storage/emulated/0/DCIM";

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

        binding.refrechDownloadLocalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Configuration configuration = db.getConfiguration(1);
                String downloadPath = configuration.getDownloadPath();
                //String downloadPath = "/storage/emulated/0/DCIM";


                File folder = new File(downloadPath);
                File[] listOfFiles = folder.listFiles();

                String fileList = "";
                for (int i = 0; i < listOfFiles.length; i++) {
                    if (listOfFiles[i].isFile()) {
                        System.out.println("File " + listOfFiles[i].getName());
                        Log.d("log","File" + listOfFiles[i].getName());
                        fileList += listOfFiles[i].getName() + "\n";
                        downloadFileList.add(listOfFiles[i].getName());
                        downloadFileUrlTable.put(listOfFiles[i].getName(),"http://localhost:8080/Download/" + listOfFiles[i].getName());
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

                listView.setAdapter(arrayAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                {
                    // argument position gives the index of item which is clicked
                    public void onItemClick(AdapterView<?> arg0, View v,int position, long arg3)
                    {
                        int itemPosition = position;
                        // ListView Clicked item value    public void changeItemBackgroundColor(int itemPosition) {
                        String itemValue = (String) listView.getItemAtPosition(position);

                        Toast.makeText(FiletransferFragment.super.getContext(),"File Selected : " + itemValue
                                        + "File Url = " + downloadFileUrlTable.get(itemValue)
                                , Toast.LENGTH_LONG).show();
                        Log.d("log","File Url : " + downloadFileUrlTable.get(itemValue));
                        changeItemBackgroundColor( v,itemPosition);


                    }
                });

            }
        });



                /*  binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);
                Toast.makeText(FiletransferFragment.super.getContext(), "Button pressed", Toast.LENGTH_SHORT).show();

            }
        });*/


    }

    public void changeItemBackgroundColor(View v , int itemPosition) {
        ListView listView = binding.listView;


        for (int i = 0; i < listView.getChildCount(); i++) {
            listView.getChildAt(i).setBackgroundColor(Color.WHITE);
        }
        v.setBackgroundColor(Color.BLUE);
        selectedDownloadFileIndex = itemPosition;
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}