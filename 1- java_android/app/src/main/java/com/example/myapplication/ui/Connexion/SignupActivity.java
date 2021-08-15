package com.example.myapplication.ui.Connexion;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.db.DatabaseHelper;
import com.example.myapplication.db.User;

public class SignupActivity extends AppCompatActivity {

    EditText userid1, password, repassword;
    Button button2 ;
    DatabaseHelper DB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        userid1 = (EditText) findViewById(R.id.userid1);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.repassword);
        button2 = (Button) findViewById(R.id.button1);

        DatabaseHelper db = new DatabaseHelper(this) ;






        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String user = userid1.getText().toString();
                String pass = password.getText().toString();
                String repass = repassword.getText().toString();

                Toast.makeText(SignupActivity.this, "Button pressed", Toast.LENGTH_SHORT).show();


                db.addUser(new User("user_id_3", "password_3", "date_3"));
                db.addUser(new User("user_id_4", "password_4", "date_4"));

            }
        });


    }
}