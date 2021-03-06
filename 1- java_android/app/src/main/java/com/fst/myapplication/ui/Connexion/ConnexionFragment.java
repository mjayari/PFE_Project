package com.fst.myapplication.ui.Connexion;

import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fst.myapplication.databinding.FragmentConnexionBinding;
import com.fst.myapplication.db.Connexion;
import com.fst.myapplication.db.DatabaseHelper;
import com.fst.myapplication.db.User;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConnexionFragment extends Fragment {

    private ConnexionViewModel connexionViewModel;
    private FragmentConnexionBinding binding;

    DatabaseHelper db;
    EditText userid_input, password_input;
    Button login_button, signup_button;

    public static int connexionID;
    public static int numberUploads = 0;
    public static int numberDownloads = 0;


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

                boolean check= validateinfo(userid,password);
                if (check) {
                    // request Verification of exsitence of USerID and password in DB
                    Boolean checkuserpass = db.checkUsernamePassword(userid, password);
                    if (checkuserpass == true) {
                        Toast.makeText(ConnexionFragment.super.getContext(), "Sign in successfull", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ConnexionFragment.super.getContext(), "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                    // request Verification of exsitence of USerID and password in DB
                    int pk = db.getConnexionRowsNumber() + 1;

                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date currentDate = new Date();
                    Log.d("log","Date : " +formatter.format(currentDate));

                    db.addConnexion(new Connexion(pk,
                            formatter.format(currentDate), 0, 0, userid));
                    ConnexionFragment.connexionID = pk;
                }


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

                boolean check= validateinfo(userid,password);

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date currentDate = new Date();

                if(check ==true) {

                    db.addUser(new User(userid, password, formatter.format(currentDate)));
                    Toast.makeText(ConnexionFragment.super.getContext(), "Registration Successful!", Toast.LENGTH_SHORT).show();

                }


            }
        });
    }

    private boolean validateinfo(String userId,String password) {
        boolean result=true;
        Log.d("log","userId=" + userId);
        Log.d("log","password=" +  password);


        // Validation Regex for UserID
        if (userId.equals("")) {
            binding.useridInput.requestFocus();
            binding.useridInput.setError("Please enter UserID field");
            result=false;
            return false;
        }else
        if (!userId.matches("^.{8,15}$")) {
            binding.useridInput.requestFocus();
            binding.useridInput.setError("size between 8 and 15 characters ");
            result=false;
            return false;
        } else
        // At least one digit
        if (!userId.matches("^.*[0-9]+.*$")) {
            binding.useridInput.requestFocus();
            binding.useridInput.setError("At least one digit ");
            result=false;
            return false;
        } else
        // at least 1 lower case letter
        if (!userId.matches("^.*[a-z]+.*$")) {
             binding.useridInput.requestFocus();
             binding.useridInput.setError("at least 1 lower case letter ");
            result=false;
            return false;
        } else
        //no white spaces
        if (!userId.matches("^\\S+$")) {
             binding.useridInput.requestFocus();
             binding.useridInput.setError("no white spaces ");
            result=false;
            return false;
        } else
        // Only letters and numbers
        if (!userId.matches("^[a-z0-9]+$")) {
             binding.useridInput.requestFocus();
             binding.useridInput.setError("Only letters and numbers  ");
            result=false;
            return false;
        }



         //if (result=false) return result;

        // Validation Regex for password
        if (password.equals("")) {
            binding.passwordInput.requestFocus();
            binding.passwordInput.setError("Please enter Password field");
            result=false;
            return false;
        }else
        if (!password.matches("^.{8,15}$")) {
            binding.passwordInput.requestFocus();
            binding.passwordInput.setError("size between 8 and 15 characters");
            result=false;
            return false;
        } else

        if (!password.matches("^.*[0-9]+.*$")) {
             binding.passwordInput.requestFocus();
             binding.passwordInput.setError("At least one digit ");
            result=false;
            return false;
        } else
        if (!password.matches("^.*[a-z]+.*$")) {
            binding.passwordInput.requestFocus();
            binding.passwordInput.setError("At least 1 lower case letter");
            result=false;
            return false;
        } else
        if (!password.matches("^.*[A-Z]+.*$")) {
            binding.passwordInput.requestFocus();
            binding.passwordInput.setError("At least one upper case letter");
            result=false;
            return false;
        } else
        if (!password.matches("^.*[@#$%^&+=]+.*$")) {
            binding.passwordInput.requestFocus();
            binding.passwordInput.setError("At least one symbol");
            result=false;
            return false;
        } else
        if (!password.matches("^\\S+$")) {
            binding.passwordInput.requestFocus();
            binding.passwordInput.setError("no white spaces ");
            result=false;
            return false;
        }










       /* String userIdREGEX = "^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[a-z0-9])" +      //only  letters and numbers
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
        }*/


        /*At least one Symbol
        if (!userId.matches("^.*[@#$%^&+=]+.*$")) {
            binding.useridInput.requestFocus();
            binding.useridInput.setError("At least one symbol ");
            return false;*/





     /*  String  passwordREGEX = "^" +
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
        }*/







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

        return result;
    }

   /* private boolean validateinfo1(String password) {

        if (!password.matches("^.{8,15}.*$")) {
            binding.passwordInput.requestFocus();
            binding.passwordInput.setError("size between 8 and 15 characters ");
            return false;
        } else
            // At least one digit
            if (!password.matches("^.*[0-9]+.*$")) {
                binding.passwordInput.requestFocus();
                binding.passwordInput.setError("At least one digit ");
                return false;
            }

                return true;
            }*/


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}