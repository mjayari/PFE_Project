package com.fst.myapplication.ui.Administration;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.fst.myapplication.databinding.FragmentAdministrationBinding;
import com.fst.myapplication.db.Configuration;
import com.fst.myapplication.db.Connexion;
import com.fst.myapplication.db.DatabaseHelper;
import com.fst.myapplication.db.FileTransfer;
import com.fst.myapplication.ui.Administration.AdministrationViewModel;
import com.fst.myapplication.ui.Configuration.ConfigurationFragment;
import com.fst.myapplication.ui.Filetransfer.FiletransferFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;

public class AdministrationFragment extends Fragment {

    private AdministrationViewModel administrationViewModel;
    private FragmentAdministrationBinding binding;

    public static DatabaseHelper db;
    public static Configuration config;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        administrationViewModel =
                new ViewModelProvider(this).get(AdministrationViewModel.class);

        binding = FragmentAdministrationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /*final TextView textView = binding.textSlideshow;
        administrationViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = new DatabaseHelper(this);

        Configuration configuration = db.getConfiguration(1);

        String downloadPath = configuration.getDownloadPath();

        //showTableData();
        displayConnexionTableData();

    }

    public String giveDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy");
        return sdf.format(cal.getTime());

       // binding.connexionIDText.setText(giveDate());
    }

    public void showTableData() {
        TableLayout stk = binding.tableLayout;
        TableRow tr0 = new TableRow(AdministrationFragment.super.getContext());
        tr0.setBackgroundColor(Color.GRAY);

        TextView tv1 = new TextView(AdministrationFragment.super.getContext());
        tv1.setText("Name");
        tr0.addView(tv1);

        TextView tv2 = new TextView(AdministrationFragment.super.getContext());
        tv2.setText("Contact");
        tr0.addView(tv2);

        TextView tv3 = new TextView(AdministrationFragment.super.getContext());
        tv3.setText("Address");
        tr0.addView(tv3);

        stk.addView(tr0);

        for (int i = 0; i<5; i++){
            TableRow tr1 = new TableRow(AdministrationFragment.super.getContext());

            TextView t1v = new TextView(AdministrationFragment.super.getContext());
            t1v.setText("Name " + (i+1));
            tr1.addView(t1v);

            TextView t2v = new TextView(AdministrationFragment.super.getContext());
            t2v.setText("connexion " + (i+1));
            tr1.addView(t2v);

            TextView t3v = new TextView(AdministrationFragment.super.getContext());
            t3v.setText("id " + (i+1));
            tr1.addView(t3v);


            stk.addView(tr1);
        }
    }

    public void displayConnexionTableData() {
        TableLayout stk = binding.tableLayout;
        TableRow tr0 = new TableRow(AdministrationFragment.super.getContext());
        tr0.setBackgroundColor(Color.GRAY);
        tr0.setPadding(10,10,10,10);

        TextView tv1 = new TextView(AdministrationFragment.super.getContext());
        tv1.setText("UserId");
        tv1.setTextColor(Color.WHITE);
        tv1.setPadding(20,5,5,5);
        tr0.addView(tv1);

        TextView tv2 = new TextView(AdministrationFragment.super.getContext());
        tv2.setText("DownloadsNumber");
        tv2.setTextColor(Color.WHITE);
        tv2.setPadding(5,5,5,5);
        tr0.addView(tv2);

        TextView tv3 = new TextView(AdministrationFragment.super.getContext());
        tv3.setText("UploadsNumber");
        tv3.setPadding(5,5,5,5);
        tv3.setTextColor(Color.WHITE);
        tr0.addView(tv3);

        TextView tv4 = new TextView(AdministrationFragment.super.getContext());
        tv4.setText("ConnexionTime");
        tv4.setTextColor(Color.WHITE);
        tv4.setPadding(5,5,5,5);
        tr0.addView(tv4);

        stk.addView(tr0);

        Hashtable<Integer, String[]> dataTable = db.readConnexionData();
        Log.d("log", "Table = " + dataTable);

        for(int i=0; i < dataTable.size(); i++) {
            String[] array = dataTable.get(i);
            Log.d("log", "array = " + array);

            String userId = array[0];
            Log.d("log", "userId = " + userId);
            String downloadNumber = array[1];
            Log.d("log", "downloadNumber = " + downloadNumber);
            String uploadNumber = array[2];
            Log.d("log", "uploadNumber = " + uploadNumber);
            String connexionTime = array[3];
            Log.d("log", "connexionTime = " + connexionTime);


            TableRow tr1 = new TableRow(AdministrationFragment.super.getContext());

            TextView t1v = new TextView(AdministrationFragment.super.getContext());
            t1v.setText(userId);
            tr1.addView(t1v);

            TextView t2v = new TextView(AdministrationFragment.super.getContext());
            t2v.setText(downloadNumber);
            tr1.addView(t2v);

            TextView t3v = new TextView(AdministrationFragment.super.getContext());
            t3v.setText(uploadNumber);
            tr1.addView(t3v);

            TextView t4v = new TextView(AdministrationFragment.super.getContext());
            t4v.setText(connexionTime);
            tr1.addView(t4v);

            stk.addView(tr1);
        }


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}