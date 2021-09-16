package com.fst.myapplication.ui.Filetransfer;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.fst.myapplication.HttpServer;
import com.fst.myapplication.databinding.FragmentFiletransferBinding;
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

                //server.urlConnect(url);
                //url = "http://192.168.1.23:12345";
                Log.d("log","url:" + url);

                new HttpUrlOpener().urlConnect(url);



            }
        });

        binding.downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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