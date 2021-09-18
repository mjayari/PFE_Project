package com.fst.myapplication.ui.Filetransfer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fst.myapplication.HttpServer;
import com.fst.myapplication.databinding.FragmentFiletransferBinding;
import com.fst.myapplication.http.HttpFileDownloader;
import com.fst.myapplication.http.HttpUrlOpener;

import java.io.OutputStream;

public class FiletransferFragment extends Fragment {

    private FiletransferViewModel filetransferViewModel;
    private FragmentFiletransferBinding binding;

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


        binding.ConnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = binding.editTextUrl.getText().toString();

                //server.urlConnect(url);
                //url = "http://192.168.1.23:12345";
                Log.d("log","url:" + url);

                new Thread(){
                    public void run(){
                        new HttpUrlOpener().urlConnect(url);
                    }
                }.start();



            }
        });

        binding.downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String fileURL = "http://192.168.1.2:12345/Camera/20210915_160855.mp4";
                String fileURL = "http://10.0.2.16:12345/InterfacePrincipale.mp4";


                String saveDir = "/storage/emulated/0/Download";

                new Thread() {
                    public void run() {
                        try {
                            //sleep(5000);
                            new HttpFileDownloader(binding).downloadFile(fileURL, saveDir);

                        } catch (Exception e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                            Log.d("log","Exception" + e1.getMessage());
                        }

                    }
                }.start();


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





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}