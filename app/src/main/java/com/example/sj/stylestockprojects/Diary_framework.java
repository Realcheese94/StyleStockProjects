package com.example.sj.stylestockprojects;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
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
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Diary_framework extends Fragment {


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

    private ImageView top_img,bottom_img,acc_img,shoes_img,hat_img;
    private Bundle bundle=new Bundle();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.diary_layout, container, false);

        //img findviewid
        top_img = (ImageView)view.findViewById(R.id.top_view);
        bottom_img = (ImageView)view.findViewById(R.id.bottom_view);
        acc_img = (ImageView)view.findViewById(R.id.acc_view);
        shoes_img = (ImageView)view.findViewById(R.id.shoes_view);
        hat_img = (ImageView)view.findViewById(R.id.hat_view);


        closet_spinner = (Spinner)view.findViewById(R.id.diary_spinner);
        data = getResources().getStringArray(R.array.catagory);



        recyclerView = (RecyclerView)view.findViewById(R.id.diary_recycle);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(),3));
        final Diary_framework.DiaryRecycleViewAdapter closetRecycleViewAdapter = new Diary_framework.DiaryRecycleViewAdapter();
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




        return view;

    }


    class DiaryRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.simple_image,parent,false);

            return new Diary_framework.DiaryRecycleViewAdapter.CustomViewHolder(view);
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
                        if(catagory.equals("top")){
                            Log.e("Diary_catagory","top");
                            int Product_num = getLayoutPosition();
                            Glide.with(getActivity()).load(Products.get(Product_num).product_url).apply(new RequestOptions().override(180,280)).into(top_img);


                        }
                        if(catagory.equals("bottom")){
                            Log.e("Diary_catagory","bottom");
                            int Product_num = getLayoutPosition();
                            Glide.with(getActivity()).load(Products.get(Product_num).product_url).apply(new RequestOptions().override(180,280)).into(bottom_img);

                        }
                        if(catagory.equals("acc")){
                            Log.e("Diary_catagory","acc");
                            int Product_num = getLayoutPosition();
                            Glide.with(getActivity()).load(Products.get(Product_num).product_url).apply(new RequestOptions().override(180,280)).into(acc_img);

                        }
                        if(catagory.equals("shoes")){
                            Log.e("Diary_catagory","shoes");
                            int Product_num = getLayoutPosition();
                            Glide.with(getActivity()).load(Products.get(Product_num).product_url).apply(new RequestOptions().override(180,280)).into(shoes_img);

                        }
                        if(catagory.equals("hat")){
                            Log.e("Diary_catagory","hat");
                            int Product_num = getLayoutPosition();
                            Glide.with(getActivity()).load(Products.get(Product_num).product_url).apply(new RequestOptions().override(180,280)).into(hat_img);

                        }


                    }
                });
            }
        }

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