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
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Closet_framework extends Fragment {
    FloatingActionButton fab;
    private DatabaseReference mDatabase;

    private  int img[] = {
            R.drawable.common_full_open_on_phone,
            R.drawable.common_google_signin_btn_icon_disabled,
            R.drawable.common_google_signin_btn_icon_dark };


    private FirebaseDatabase firebaseDatabase ;
    private DatabaseReference ImagePathRef;
    private GridView gridView;
    private  String username;



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
        firebaseDatabase = FirebaseDatabase.getInstance();
        ImagePathRef = firebaseDatabase.getReference("imagepath");
        gridView = (GridView)view.findViewById(R.id.mygridimageview);
        //gridView.setAdapter(new imageAdapter(getActivity()));
        imageAdapter imageAdapter = new imageAdapter(
                getActivity(),
                R.layout.row,
                img
        );
        gridView.setAdapter(imageAdapter);




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
    public class imageAdapter extends BaseAdapter{
        Context context;
        int layout;
        int img[];
        LayoutInflater inf;

        public imageAdapter(Context context, int layout, int[] img) {
            this.context = context;
            this.layout = layout;
            this.img = img;
            inf = (LayoutInflater) context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return img.length;
        }

        @Override
        public Object getItem(int position) {
            return img[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = inf.inflate(layout, null);
            ImageView iv = (ImageView) convertView.findViewById(R.id.imageView1);
            iv.setImageResource(img[position]);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView imageView = (ImageView) v;
                    imageView.setImageResource(R.drawable.common_google_signin_btn_icon_dark_focused);
                }
            });

            return convertView;
        }

    } // end of class


}


