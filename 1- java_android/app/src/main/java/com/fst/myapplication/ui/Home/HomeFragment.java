package com.fst.myapplication.ui.Home;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.fst.myapplication.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    private List<String> itemList;
    private Integer selectedItemIndex = -1 ;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /*final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        return root;
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //lv = (ListView) findViewById(R.id.your_list_view_id);
        ListView listView = binding.downloadListView;

        // Instanciating an array list (you don't need to do this,
        // you already have yours).
        itemList = new ArrayList<String>();
        itemList.add("fookledha dzauehazu eh azuh azuh uoho.mp3");
        itemList.add("barqklqdh ehr ioehrioeh rio ehro ehzoihzroe.apk");
        itemList.add("list3eziehz zeijzei zienziez eziejzi.mp4");
        itemList.add("bdsddzejz ezieuzie uzeuzpie uzieuz.mp3");
        itemList.add("dslddzdizd zeijzie izejzoiej eziejzo.mp4");
        itemList.add("paledsf hdklfh kldfjkdjf jfhdkjfd.apk");
        itemList.add("fookledha dzauehazu eh azuh azuh uoho.mp3");
        itemList.add("barqklqdh ehr ioehrioeh rio ehro ehzoihzroe.apk");
        itemList.add("list3eziehz zeijzei zienziez eziejzi.mp4");
        itemList.add("bdsddzejz ezieuzie uzeuzpie uzieuz.mp3");
        itemList.add("dslddzdizd zeijzie izejzoiej eziejzo.mp4");
        itemList.add("paledsf hdklfh kldfjkdjf jfhdkjfd.apk");

        Hashtable ht = new Hashtable <String,String>();
        ht.put("fookledha dzauehazu eh azuh azuh uoho.mp3","http://localhost:12345/fookledha dzauehazu eh azuh azuh uoho.mp3");
        ht.put("barqklqdh ehr ioehrioeh rio ehro ehzoihzroe.apk","http://localhost:12345/barqklqdh ehr ioehrioeh rio ehro ehzoihzroe.apk");
        ht.put("list3eziehz zeijzei zienziez eziejzi.mp4","http://localhost:12345/list3eziehz zeijzei zienziez eziejzi.mp4");



        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                HomeFragment.super.getContext(),
                android.R.layout.simple_list_item_1,
                itemList );

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            // argument position gives the index of item which is clicked
            public void onItemClick(AdapterView<?> arg0, View v,int position, long arg3)
            {
                int itemPosition = position;
                // ListView Clicked item value    public void changeItemBackgroundColor(int itemPosition) {
                String itemValue = (String) listView.getItemAtPosition(position);

                Toast.makeText(HomeFragment.super.getContext(), "Item Selected : "+itemValue
                                + "File Url = " + ht.get(itemValue)
                        ,   Toast.LENGTH_LONG).show();
                Log.d("log","File Url : " + ht.get(itemValue));
                changeItemBackgroundColor( v,itemPosition);


            }
        });

    }

    public void changeItemBackgroundColor(View v , int itemPosition) {
        ListView listView = binding.downloadListView;


        for (int i=0; i<listView.getChildCount() ; i++){
            listView.getChildAt(i).setBackgroundColor(Color.WHITE);
        }
        v.setBackgroundColor(Color.BLUE);
        selectedItemIndex = itemPosition ;

        /*for (int i=0 ; i<itemList.size() ; i++){
            if (i == itemPosition) {
                listView.getChildAt(itemPosition).setBackgroundColor(Color.BLUE);
                selectedItemIndex = i ;
                Log.d("log", "BLUE = " + i + " | " + itemList.get(i));
            } else {
                //listView.getChildAt(itemPosition).setBackgroundColor(Color.GRAY);
                Log.d("log", "Gray = " + i + " | " + itemList.get(i));
            }

        }*/

        /*Log.d("log", "selectedItemIndex 1 = " + selectedItemIndex);

        if(selectedItemIndex == itemPosition) { // same selection
            Log.d("log", "selectedItemIndex 2 = " + selectedItemIndex);
            listView.getChildAt(itemPosition).setBackgroundColor(Color.WHITE);
            selectedItemIndex = -1 ;
        } else {
            if(selectedItemIndex == -1) { // no selection
                Log.d("log", "selectedItemIndex 3 = " + selectedItemIndex);
                listView.getChildAt(itemPosition).setBackgroundColor(Color.BLUE);
                selectedItemIndex = itemPosition ;
            } else if(selectedItemIndex != -1){ // already selected
                Log.d("log", "selectedItemIndex = 4 " + selectedItemIndex);
                Toast.makeText(HomeFragment.super.getContext(), "Multiselection forbidden",Toast.LENGTH_LONG).show();
            }
        }*/

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}