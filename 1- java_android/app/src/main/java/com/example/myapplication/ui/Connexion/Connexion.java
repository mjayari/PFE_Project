package com.example.myapplication.ui.Connexion;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class Connexion extends AppCompatActivity {

    private EditText userid,password;
    private TextView textView2,textView3;
    private Button button,button2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_connexion);

        userid=findViewById(R.id.userid);
        password=findViewById(R.id.password);
        button=findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  if(TextUtils.isEmpty(userid.getText().toString()));
                  Toast.makeText(Connexion.this,"Please type your username",Toast.LENGTH_LONG).show();


                  }

        });



    }
}
