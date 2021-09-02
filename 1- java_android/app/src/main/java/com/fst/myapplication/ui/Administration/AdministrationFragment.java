package com.fst.myapplication.ui.Administration;

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

import com.fst.myapplication.databinding.FragmentAdministrationBinding;
import com.fst.myapplication.ui.Administration.AdministrationViewModel;

public class AdministrationFragment extends Fragment {

    private AdministrationViewModel administrationViewModel;
    private FragmentAdministrationBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        administrationViewModel =
                new ViewModelProvider(this).get(AdministrationViewModel.class);

        binding = FragmentAdministrationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        administrationViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // binding.button2.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //  public void onClick(View view) {
               /* NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment);*/
        //    Toast.makeText(ConfigurationFragment.super.getContext(), "Button pressed", Toast.LENGTH_SHORT).show();

        //}
        //});


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}