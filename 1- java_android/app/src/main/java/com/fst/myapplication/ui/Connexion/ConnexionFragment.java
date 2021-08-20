package com.fst.myapplication.ui.Connexion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.fst.myapplication.databinding.FragmentConnexionBinding;
import com.fst.myapplication.db.DatabaseHelper;
import com.fst.myapplication.db.User;
import com.fst.myapplication.ui.home.HomeFragment;

public class ConnexionFragment extends Fragment {

    private ConnexionViewModel connexionViewModel;
    private FragmentConnexionBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        connexionViewModel =
                new ViewModelProvider(this).get(ConnexionViewModel.class);

        binding = FragmentConnexionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textGallery;
        connexionViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DatabaseHelper db = new DatabaseHelper(this) ;



        binding.btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               /* NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);*/
                Toast.makeText(ConnexionFragment.super.getContext(), "Button pressed", Toast.LENGTH_SHORT).show();

                db.addUser(new User("user_id_3", "password_3", "date_3"));
                db.addUser(new User("user_id_4", "password_4", "date_4"));

            }
        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}