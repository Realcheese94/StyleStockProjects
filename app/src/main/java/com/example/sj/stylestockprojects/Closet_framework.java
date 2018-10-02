package com.example.sj.stylestockprojects;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import com.bumptech.glide.Glide;
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

    private ArrayList<Product> Products = new ArrayList<>();

    Spinner closet_spinner;
    RecyclerView recyclerView;

    private String catagory="top";
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ChildEventListener mChild;
    private  String username;
    private ArrayAdapter spinnerAdapter;
    private String[] data;

    private Bundle bundle=new Bundle();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            username = bundle.getString("username","");
            Log.e("username",username);

        }

    }

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

        fab = (FloatingActionButton)view.findViewById(R.id.AdditemButton);
        //closet_grid = (GridView)view.findViewById(R.id.closet_grid);
        closet_spinner = (Spinner)view.findViewById(R.id.closet_spinner);
        data = getResources().getStringArray(R.array.catagory);



        recyclerView = (RecyclerView)view.findViewById(R.id.closet_recycle);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(),3));
        final ClosetRecycleViewAdapter closetRecycleViewAdapter = new ClosetRecycleViewAdapter();
        recyclerView.setAdapter(closetRecycleViewAdapter);
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("michal","setonclick");
            }
        });


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

                        Products.clear();

                        for (DataSnapshot messageData : dataSnapshot.getChildren()){
                            Product product = messageData.getValue(Product.class);
                            Products.add(product);
                            Log.e("michal_pro",Products.toString());
                        }
                        closetRecycleViewAdapter.notifyDataSetChanged();

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


    class ClosetRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_image,parent,false);

            return new CustomViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Glide.with(holder.itemView.getContext()).load(Products.get(position).product_url).into(((CustomViewHolder)holder).imageView);
        }

        @Override
        public int getItemCount() {
            return Products.size();
        }

        private class CustomViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public CustomViewHolder(View view) {
                super(view);
                imageView = (ImageView)view.findViewById(R.id.item_image);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos=getLayoutPosition();
                        bundle.clear();

                        Intent intent = new Intent(getActivity(),ViewItem.class);
                        bundle.putString("brand",Products.get(pos).product_brand);
                        bundle.putString("price",Products.get(pos).product_price);
                        bundle.putString("name",Products.get(pos).product_name);
                        bundle.putString("size",Products.get(pos).product_size);
                        bundle.putString("url",Products.get(pos).product_url);

                        intent.putExtras(bundle);
                        startActivity(intent);

                    }
                });
            }
        }

    }



}


