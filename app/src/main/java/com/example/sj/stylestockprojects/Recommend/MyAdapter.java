package com.example.sj.stylestockprojects.Recommend;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.sj.stylestockprojects.R;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter {
    private ArrayList<MyItem> myItems = new ArrayList<>();

    @Override
    public int getCount(){
        return myItems.size();
    }


    @Override
    public MyItem getItem(int position){
        return myItems.get(position);
    }

    @Override
    public long getItemId(int position){
        return 0;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Context context = parent.getContext();

        if(convertView ==null){
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.recommend_ltem_list,parent,false);
        }

        ImageView iv_img = (ImageView)convertView.findViewById(R.id.item_image);
        TextView tv_name = (TextView)convertView.findViewById(R.id.item_name);
        TextView tv_price = (TextView)convertView.findViewById(R.id.item_price);

        MyItem myItem = getItem(position);

        iv_img.setImageDrawable(myItem.getIcon());
        tv_name.setText(myItem.getName());
        tv_price.setText(myItem.getContents());

        return  convertView;
    }

    public void addItem(Drawable img,String name,String contents)
    {
        MyItem myItem = new MyItem();
        myItem.setIcon(img);
        myItem.setName(name);
        myItem.setContents(contents);

        myItems.add(myItem);
    }
}
