package com.fst.myapplication.ui.Configuration;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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

import com.fst.myapplication.databinding.FragmentConfigurationBinding;
import com.fst.myapplication.databinding.FragmentConfigurationBinding;
import com.fst.myapplication.db.Configuration;
import com.fst.myapplication.db.DatabaseHelper;
import com.fst.myapplication.db.User;

public class ConfigurationFragment extends Fragment {

    private ConfigurationViewModel configurationViewModel;
    private FragmentConfigurationBinding binding;

    DatabaseHelper db;
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

        binding.TextEditUploadPath.setText("/storage/upload");
        binding.TextEditDownloadPath.setText("/storage/download");



        binding.SaveSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /*NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);*/
                Toast.makeText(ConfigurationFragment.super.getContext(), "Button pressed", Toast.LENGTH_SHORT).show();

                db.addConfiguration(new Configuration("user1","8080","FIle1","File2"));

            }
        });


        binding.findUploadButton.setOnClickListener(new View.OnClickListener() {
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
            }
        });
                /*protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                if (requestCode == 100) {
                String folderPath = intent.getDataString();
                //TODO
                return;
                }
                super.onActivityResult(requestCode, resultCode, data);}*/
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}