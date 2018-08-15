package com.example.sj.stylestockprojects;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class Diary_framework extends Fragment {

    private List<Product> Products = new ArrayList<>();

    Spinner Diary_spinner;
    RecyclerView recyclerView;
    private ArrayAdapter spinnerAdapter;
    private  String username;
    private String[] data;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diary_layout, container, false);
        Diary_spinner = (Spinner)view.findViewById(R.id.diary_spinner);
        spinnerAdapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_expandable_list_item_1,data);

        return view;

    }
    @Override
    public void onStart(){


        Log.e("옷장_이름시작",username);
        super.onStart();


    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            username = bundle.getString("username","");
            Log.e("Diary_username",username);

        }

    }
}