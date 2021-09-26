package com.fst.myapplication.ui.Administration;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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
        displayFiletransferTableData();
    }

    public String giveDate() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, yyyy");
        return sdf.format(cal.getTime());

        // binding.connexionIDText.setText(giveDate());
    }


    public void displayConnexionTableData() {
        TableLayout stk = binding.connexionTableLayout;
        stk.setPadding(150,5,5,5);

        TableRow tr0 = new TableRow(AdministrationFragment.super.getContext());
        tr0.setBackgroundColor(Color.GRAY);
        tr0.setPadding(10, 10, 10, 10);

        TextView tv1 = new TextView(AdministrationFragment.super.getContext());
        tv1.setText("UserId");
        tv1.setTextColor(Color.WHITE);
        tv1.setPadding(20, 5, 5, 5);
        tv1.setGravity(Gravity.CENTER_HORIZONTAL);
        tr0.addView(tv1);

        TextView tv2 = new TextView(AdministrationFragment.super.getContext());
        tv2.setText("Downloads");
        tv2.setTextColor(Color.WHITE);
        tv2.setPadding(5, 5, 5, 5);
        tv2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tr0.addView(tv2);

        TextView tv3 = new TextView(AdministrationFragment.super.getContext());
        tv3.setText("Uploads");
        tv3.setPadding(5, 5, 5, 5);
        tv3.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tv3.setTextColor(Color.WHITE);
        tr0.addView(tv3);

        TextView tv4 = new TextView(AdministrationFragment.super.getContext());
        tv4.setText("ConnexionTime");
        tv4.setTextColor(Color.WHITE);
        tv4.setPadding(5, 5, 5, 5);
        tv4.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tr0.addView(tv4);

        stk.addView(tr0);

        Hashtable<Integer, String[]> dataTable = db.readConnexionData();
        Log.d("log", "Table = " + dataTable);

        for (int i = 0; i < dataTable.size(); i++) {
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
            tr1.setPadding(5,5,5,5);

            TextView t1v = new TextView(AdministrationFragment.super.getContext());
            t1v.setText(userId);
            t1v.setPadding(5,5,5,5);
            t1v.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tr1.addView(t1v);

            TextView t2v = new TextView(AdministrationFragment.super.getContext());
            t2v.setText(downloadNumber);
            t2v.setPadding(5,5,5,5);
            t2v.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tr1.addView(t2v);

            TextView t3v = new TextView(AdministrationFragment.super.getContext());
            t3v.setText(uploadNumber);
            t3v.setPadding(5,5,5,5);
            t3v.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tr1.addView(t3v);

            TextView t4v = new TextView(AdministrationFragment.super.getContext());
            t4v.setText(connexionTime);
            t4v.setPadding(5,5,5,5);
            t4v.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tr1.addView(t4v);

            stk.addView(tr1);


        }
    }

        public void displayFiletransferTableData () {
            TableLayout stk1 = binding.fileTransferTableLayout;
            stk1.setPadding(306,10,10,10);

            TableRow tr01 = new TableRow(AdministrationFragment.super.getContext());
            tr01.setBackgroundColor(Color.GRAY);
            tr01.setPadding(10, 10, 10, 10);

            TextView tv11 = new TextView(AdministrationFragment.super.getContext());
            tv11.setText("transferId");
            tv11.setTextColor(Color.WHITE);
            tv11.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv11.setPadding(20, 5, 5, 5);
            //tr01.addView(tv11);

            TextView tv22 = new TextView(AdministrationFragment.super.getContext());
            tv22.setText("fileName");
            tv22.setTextColor(Color.WHITE);
            tv22.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv22.setPadding(5, 5, 5, 5);
            tr01.addView(tv22);

            TextView tv55 = new TextView(AdministrationFragment.super.getContext());
            tv55.setText("UserId");
            tv55.setTextColor(Color.WHITE);
            tv55.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv55.setPadding(5, 5, 5, 5);
            tr01.addView(tv55);

            TextView tv33 = new TextView(AdministrationFragment.super.getContext());
            tv33.setText("transferType");
            tv33.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv33.setPadding(5, 5, 5, 5);
            tv33.setTextColor(Color.WHITE);
            tr01.addView(tv33);

            TextView tv44 = new TextView(AdministrationFragment.super.getContext());
            tv44.setText("transferTime");
            tv44.setTextColor(Color.WHITE);
            tv44.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            tv44.setPadding(5, 5, 5, 5);
            tr01.addView(tv44);



            stk1.addView(tr01);


            Hashtable<Integer, String[]> dataTable = db.readFileTransferData();
            Log.d("log", "Table = " + dataTable);

            for (int i = 0; i < dataTable.size(); i++) {
                String[] array = dataTable.get(i);
                Log.d("log", "array = " + array);

                String transferId = array[0];
                Log.d("log", "transferId = " + transferId);
                String fileName = array[1];
                Log.d("log", "fileName = " + fileName);
                String transferType = array[2];
                Log.d("log", "transferType = " + transferType);
                String transferTime = array[3];
                Log.d("log", "transferTime = " + transferTime);
                String connexionId = array[4];
                Log.d("log", "connexionId = " + connexionId);


                TableRow tr1 = new TableRow(AdministrationFragment.super.getContext());

                TextView t1v = new TextView(AdministrationFragment.super.getContext());
                t1v.setText(transferId);
                t1v.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                //tr1.addView(t1v);

                TextView t2v = new TextView(AdministrationFragment.super.getContext());
                t2v.setText(fileName);
                t2v.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tr1.addView(t2v);

                TextView t5v = new TextView(AdministrationFragment.super.getContext());
                t5v.setText(db.getUserIdByConnexionId(Integer.valueOf(connexionId)));
                t5v.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tr1.addView(t5v);

                TextView t3v = new TextView(AdministrationFragment.super.getContext());
                t3v.setText(transferType);
                t3v.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tr1.addView(t3v);

                TextView t4v = new TextView(AdministrationFragment.super.getContext());
                t4v.setText(transferTime);
                t4v.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tr1.addView(t4v);


                stk1.addView(tr1);
            }
        }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}