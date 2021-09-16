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
                //String url = "http://localhost:12345/";
                //String url = "http://192.168.1.20:12345/";

                //String fileURL = "http://192.168.1.20/Detective.Chinatown.3.2021.1080p.BluRay.x264.AAC5.1-%5bYTS.MX%5d.mp4";
                String fileURL = "http://192.168.1.20:12345/video.mp4";

                String saveDir = "/storage/emulated/0/DCIM/";

                new Thread() {
                    public void run() {
                        try {
                            new HttpUrlOpener().urlConnect(url);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }.start();

                /*
                new Thread() {
                    public void run() {
                        try {
                            Log.d("log","Message");
                            //sleep(5000);
                            new HttpFileDownloader().downloadFile(fileURL, saveDir);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            Log.d("log","Exception: " + e.getMessage());
                        }
                    }
                }.start();*/

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