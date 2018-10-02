package com.example.sj.stylestockprojects;

import android.content.Intent;
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
import com.example.sj.stylestockprojects.Recommend.Crawlers20.TopJsoupAsyncTask;
import com.example.sj.stylestockprojects.Recommend.Recommend_detail;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Recommend_framework extends Fragment {

    Spinner recommend_spinner;
    RecyclerView recyclerView;
    private ArrayList<Product> Products = new ArrayList<>();
    private String catagory = "top";
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private String username;
    private ArrayAdapter spinnerAdapter;
    private String[] data;
    private Bundle bundle = new Bundle();


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if(bundle != null){
            username = bundle.getString("username","");

        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recommend_layout, container, false);


        //closet_grid = (GridView)view.findViewById(R.id.closet_grid);
        recommend_spinner = (Spinner)view.findViewById(R.id.recommend_spinner);
        data = getResources().getStringArray(R.array.catagory);



        recyclerView = (RecyclerView)view.findViewById(R.id.recommend_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(),3));
        final RecommendRecycleViewAdapter recommendRecycleViewAdapter = new RecommendRecycleViewAdapter();
        recyclerView.setAdapter(recommendRecycleViewAdapter);
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("michal","setonclick");
            }
        });


        spinnerAdapter = new ArrayAdapter<String>(view.getContext(),android.R.layout.simple_expandable_list_item_1,data);
        recommend_spinner.setAdapter(spinnerAdapter);
        recommend_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


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
                        recommendRecycleViewAdapter.notifyDataSetChanged();

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


    class RecommendRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

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

                        Intent intent = new Intent(getActivity(),Recommend_detail.class);
                        bundle.putString("username",username);
                        bundle.putString("catagory",catagory);
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

