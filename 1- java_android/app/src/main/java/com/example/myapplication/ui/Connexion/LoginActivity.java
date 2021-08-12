package com.example.myapplication.ui.Connexion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.R;
import com.example.myapplication.db.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    EditText userid, password;
    Button login , register ;
    DatabaseHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        userid = (EditText) findViewById(R.id.userid1);
        password = (EditText) findViewById(R.id.password);
        login= (Button) findViewById(R.id.button1);
        DB = new DatabaseHelper(this);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = userid.getText().toString();
                String pass = password.getText().toString();

            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}