package com.example.myapplication.ui.Connexion;

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

import com.example.myapplication.R;
import com.example.myapplication.db.Connexion;
import com.example.myapplication.db.DatabaseHelper;
import com.example.myapplication.db.User;

public class ConnexionFragment extends Fragment {

    EditText userid1, password, repassword;
    Button button2 ;
    DatabaseHelper DB;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_connexion,container,false);



        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = userid1.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                Toast.makeText(ConnexionFragment.this, "Button pressed", Toast.LENGTH_SHORT).show();




            }
        });


    }


}
