package com.example.sj.stylestockprojects;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Diary_framework extends Fragment {

    Spinner spinner;
    String[] data;
    String username;
    //String catagory = "top";
    RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diary_layout, container, false);
        data = getResources().getStringArray(R.array.catagory);
        spinner= (Spinner)view.findViewById(R.id.diary_spinner);
        recyclerView = (RecyclerView)view.findViewById(R.id.diary_recycle);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(),3));
        final Closet_framework.ClosetRecycleViewAdapter diary_recyler = new Closet_framework.ClosetRecycleViewAdapter();
        recyclerView.setAdapter(diary_recyler);
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("hi","test");
            }
        });



        spinner.setAdapter(Closet_framework.spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    switch (i){
                        case 0:
                            Closet_framework.catagory="top";
                            break;
                        case 1:
                            Closet_framework.catagory="bottom";
                            break;
                        case 2:
                            Closet_framework.catagory="acc";
                            break;
                        case 3:
                            Closet_framework.catagory="shoes";
                            break;
                        case 4:
                            Closet_framework.catagory="hat";
                            break;
                    }
                    Log.e("diary_catagory",Closet_framework.catagory);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        return view;

    }
    @Override
    public void onStart(){

        super.onStart();


    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if(bundle != null){
            username = bundle.getString("username","");
            Log.e("diary_username",username);

        }


    }


    }

