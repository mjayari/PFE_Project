package com.fst.myapplication.ui.Connexion;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
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

import com.fst.myapplication.MainActivity;
import com.fst.myapplication.R;
import com.fst.myapplication.databinding.FragmentConnexionBinding;
import com.fst.myapplication.db.Connexion;
import com.fst.myapplication.db.DatabaseHelper;
import com.fst.myapplication.db.User;
import com.fst.myapplication.ui.Filetransfer.FiletransferFragment;
import com.fst.myapplication.ui.home.HomeFragment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConnexionFragment extends Fragment {

    private ConnexionViewModel connexionViewModel;
    private FragmentConnexionBinding binding;

    DatabaseHelper db;
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



                // Toast.makeText(ConnexionFragment.super.getContext(), "Button pressed", Toast.LENGTH_SHORT).show();// UserID input verification
                // if (userid.equals(""))
                // Toast.makeText(ConnexionFragment.super.getContext(), "Please enter UserID field", Toast.LENGTH_SHORT).show();

                // Password input verification
                //if (password.equals(""))
                //    Toast.makeText(ConnexionFragment.super.getContext(), "Please enter Password field", Toast.LENGTH_SHORT).show();

                // Function for validate UserID
                //binding.passwordInput.setTransformationMethod(PasswordTransformationMethod.getInstance());
                boolean check= validateinfo(userid,password);
                Log.d("password", password);
                //boolean check=true;
                //if(check==true) {
                //  Toast.makeText(ConnexionFragment.super.getContext(), "Valid ", Toast.LENGTH_SHORT).show();
                //}
                //else {
                //  Toast.makeText(ConnexionFragment.super.getContext(), "Invalid ", Toast.LENGTH_SHORT).show();
                //}

                // request Verification of exsitence of USerID and password in DB
                Boolean checkuserpass = db.checkusernamepassword(userid, password);
                if (checkuserpass == true) {
                    Toast.makeText(ConnexionFragment.super.getContext(), "Sign in successfull", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ConnexionFragment.super.getContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                }

                // request Verification of exsitence of USerID and password in DB
                int pk = db.getConnexionRowsNumber() + 1;
                db.addConnexion(new Connexion(pk, "current_date", 0, 0, userid));
                //Toast.makeText(ConnexionFragment.super.getContext(), "UserID = " + userid , Toast.LENGTH_SHORT).show();
                //Toast.makeText(ConnexionFragment.super.getContext(), "ConnexionRowsNumber = " + db.getConnexionRowsNumber() , Toast.LENGTH_SHORT).show();


            }
        });


        // SignUp Button onclicklistener
        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userid = binding.useridInput.getText().toString();
                String password = binding.passwordInput.getText().toString();

                // UserID input verification
                if (userid.equals(""))
                    Toast.makeText(ConnexionFragment.super.getContext(), "Please enter UserID field", Toast.LENGTH_SHORT).show();

                // Password input verification
                if (password.equals(""))
                    Toast.makeText(ConnexionFragment.super.getContext(), "Please enter Password field", Toast.LENGTH_SHORT).show();

                // request of UserID and password input verification
                Toast.makeText(ConnexionFragment.super.getContext(), "UserID = " + userid + " | Password = " + password, Toast.LENGTH_SHORT).show();

                db.addUser(new User(userid, password, "current date"));

            }
        });
    }

    private boolean validateinfo(String userId,String password) {
        String userIdREGEX = "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[a-z0-9])" +      //only lower case letters and numbers
                "(?=\\S+$)" +           //no white spaces
                ".{8,15}" +               //size between 8 and 15 characters
                "$";
        Pattern p = Pattern.compile(userIdREGEX);
        Matcher m = p.matcher(userId);
        boolean b = m.matches();
        if (b)
            Toast.makeText(ConnexionFragment.super.getContext(), "UserId Valid", Toast.LENGTH_SHORT).show();
        else {
            binding.useridInput.requestFocus();
            binding.useridInput.setError("UserId not Valid");
            Toast.makeText(ConnexionFragment.super.getContext(), "UserId not  Valid", Toast.LENGTH_SHORT).show();
        }


        String  passwordREGEX = "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{8,15}" +               //size between 8 and 15 characters
                "$";
        Pattern p1 = Pattern.compile(passwordREGEX);
        Matcher m1 = p1.matcher(password);
        boolean b1 = m1.matches();
        if (b1)
            Toast.makeText(ConnexionFragment.super.getContext(), "Password Valid", Toast.LENGTH_SHORT).show();
        else{
            binding.passwordInput.requestFocus();
            binding.passwordInput.setError("Password not Valid");
            binding.passwordInput.setCursorVisible(true);
            Toast.makeText(ConnexionFragment.super.getContext(), "Password not  Valid", Toast.LENGTH_SHORT).show();
        }


        Pattern p11 = Pattern.compile("^(?=.*[0-9])$");
        Matcher m11 = p11.matcher(password);
        boolean b11 = m11.matches();
        if (!m11.matches()) {
            binding.useridInput.requestFocus();
            binding.useridInput.setError("At least one digit ");
            return false;
        }





      /* if (userid.length()==0){
           binding.useridInput.requestFocus();
           binding.useridInput.setError("Field can't be Empty");
           return false;
       }
       else if (!userid.matches("^.{8,15}$")){
           binding.useridInput.requestFocus();
           binding.useridInput.setError("length must be between 8 and 15");
            return false;
       }
       else if (! userid.matches("[0-9]+")){
           binding.useridInput.requestFocus();
           binding.useridInput.setError("At least one digit");
           return false;
       }
       else if (! userid.matches("[a-z]+")){
           binding.useridInput.requestFocus();
           binding.useridInput.setError("At least one lowercase letter");
           return false;
       }
       else if (userid.matches("[^a!c][@#$%^&+=]+")){
           binding.useridInput.requestFocus();
           binding.useridInput.setError("No symbols");
           return false;
       }

        if (password.length()==0){
            binding.passwordInput.requestFocus();
            binding.passwordInput.setError("Field can't be Empty");
            return false;
        }
        else if (password.length()<10){
            binding.passwordInput.requestFocus();
            binding.passwordInput.setError("At least 10 characters");
            return false;
        }
        else if (password.matches(".*[A-Z]+")){
            binding.passwordInput.requestFocus();
            binding.passwordInput.setError("Minimum one upperace");
            return false;
        }
        else if (password.matches(".*[0-9]+")){
            binding.passwordInput.requestFocus();
            binding.passwordInput.setError("Minimum one number");
            return false;
        }
        else if (password.matches(".*[@#$%^&+=]+")){
            binding.passwordInput.requestFocus();
            binding.passwordInput.setError("Minimum one Symbol");
            return false;
        }
        else*/
         return true;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}