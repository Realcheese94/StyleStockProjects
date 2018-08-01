package com.example.sj.stylestockprojects;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Closet_framework extends Fragment {
    FloatingActionButton fab;
    List<Object> Array = new ArrayList<>();
    Spinner closet_spinner;
    GridView closet_grid;
    private ArrayAdapter<String> adapter;
    private String catagory="top";
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ChildEventListener mChild;
    private  String username;
    private ArrayAdapter spinnerAdapter;
    private String[] data;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            username = bundle.getString("username","");
            Log.e("username",username);

        }


    }





    //closet _framework가 실행되면 시작
    @Override
    public void onStart(){

        Log.e("closet_framework","start_Closet_framework");
        Log.e("옷장_이름시작",username);
        super.onStart();


    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.closet_layout, container, false);
        /*Bundle bundle = this.getArguments();
        if(bundle != null){
            userid = bundle.getString("userid","");
            Log.e("Closet_userid=",userid);
        }*/
        fab = (FloatingActionButton)view.findViewById(R.id.AdditemButton);
       closet_grid = (GridView)view.findViewById(R.id.closet_grid);
       closet_spinner = (Spinner)view.findViewById(R.id.closet_spinner);
       data = getResources().getStringArray(R.array.catagory);


        spinnerAdapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_expandable_list_item_1,data);
        closet_spinner.setAdapter(spinnerAdapter);
        closet_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               switch (i){
                   case 0:
                       catagory="top";
                       break;
                   case 1:
                       catagory="bottom";
                       break;
                   case 2:
                       catagory="acc";
                       break;
                   case 3:
                       catagory="shoes";
                       break;
                   case 4:
                       catagory="hat";
                       break;
               }
               Log.e("michal_catagory",catagory);
               mReference = mDatabase.getReference(username+"/"+catagory);
                mReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        adapter.clear();

                        for (DataSnapshot messageData : dataSnapshot.getChildren()){
                            String ms = messageData.getKey().toString();

                            Array.add(ms);
                            adapter.add(ms);
                            Log.e("michal",messageData.toString());
                        }
                        adapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        adapter = new ArrayAdapter<String>(this.getContext(),android.R.layout.simple_gallery_item,new ArrayList<String>());
       closet_grid.setAdapter(adapter);


        Log.e("michal","sc");
        mDatabase = FirebaseDatabase.getInstance();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),AddItem.class);
                intent.putExtra("username",username);
                startActivity(intent);


            }
        });



        return view;



    }


}



