package com.fst.myapplication.ui.Connexion;

import android.content.Intent;
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

import com.fst.myapplication.MainActivity;
import com.fst.myapplication.R;
import com.fst.myapplication.databinding.FragmentConnexionBinding;
import com.fst.myapplication.db.Connexion;
import com.fst.myapplication.db.DatabaseHelper;
import com.fst.myapplication.db.User;
import com.fst.myapplication.ui.Filetransfer.FiletransferFragment;
import com.fst.myapplication.ui.home.HomeFragment;

public class ConnexionFragment extends Fragment {

    private ConnexionViewModel connexionViewModel;
    private FragmentConnexionBinding binding;

    DatabaseHelper DB;
    EditText userid_input, password_input;
    Button login_button, signup_button;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        connexionViewModel =
                new ViewModelProvider(this).get(ConnexionViewModel.class);

        binding = FragmentConnexionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /*final TextView textView = binding.textGallery;
        connexionViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }



    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DatabaseHelper db = new DatabaseHelper(this);


        // Login Button onclicklistener
        binding.loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String userid = binding.useridInput.getText().toString();
                String password = binding.passwordInput.getText().toString();

                Toast.makeText(ConnexionFragment.super.getContext(), "Button pressed", Toast.LENGTH_SHORT).show();

                // request Verification of exsitence of USerID and password in DB
                db.addConnexion(new Connexion(1,"current_date",0,0));


            }
        });

        // SignUp Button onclicklistener
        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userid = binding.useridInput.getText().toString();
                String password = binding.passwordInput.getText().toString();

                // UserID input verification
                if(userid.equals(""))
                    Toast.makeText(ConnexionFragment.super.getContext(), "Please enter UserID field", Toast.LENGTH_SHORT).show();

                // Password input verification
                if(password.equals(""))
                    Toast.makeText(ConnexionFragment.super.getContext(), "Please enter Password field", Toast.LENGTH_SHORT).show();


                //Toast.makeText(ConnexionFragment.super.getContext(), "UserID = " + userid + " | Password = " + password, Toast.LENGTH_SHORT).show();

                db.addUser(new User(userid, password, "current date"));

            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}