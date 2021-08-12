package com.example.myapplication.ui.Connexion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.db.DatabaseHelper;

public class SignupActivity extends AppCompatActivity {

    EditText userid1, password, repassword;
    Button register ;
    DatabaseHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        userid1 = (EditText) findViewById(R.id.userid1);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);
        register = (Button) findViewById(R.id.button1);

        DB = new DatabaseHelper(this);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = userid1.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

            }
        });


    }
}