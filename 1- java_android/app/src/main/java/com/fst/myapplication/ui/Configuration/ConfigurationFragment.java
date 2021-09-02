package com.fst.myapplication.ui.Configuration;

import android.os.Bundle;
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
    Button btnnsave;

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




        binding.btnnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /*NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);*/
                Toast.makeText(ConfigurationFragment.super.getContext(), "Button pressed", Toast.LENGTH_SHORT).show();

                db.addConfiguration(new Configuration("user1","8080","FIle1","File2"));

            }
        });


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}