package com.example.sj.stylestockprojects;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class ViewItem extends AppCompatActivity {
    ImageButton itemImage;
    EditText itemname,itemprice,itembrand,itemsize;
    String brand,price,name,size,url;
    Bundle bundle;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.7f;
        setContentView(R.layout.activity_add_item);

        bundle=getIntent().getExtras();
        brand = bundle.getString("brand");
        price = bundle.getString("price");
        name = bundle.getString("name");
        size = bundle.getString("size");
        url = bundle.getString("url");

        itemImage = (ImageButton) findViewById(R.id.itemImage);
        itemname = (EditText)findViewById(R.id.itemname);
        itemprice = (EditText)findViewById(R.id.itemprice);
        itembrand = (EditText)findViewById(R.id.itemseller);
        itemsize = (EditText)findViewById(R.id.itemsize);

        Glide.with(this).load(url).apply(new RequestOptions().override(180,280)).into(itemImage);
        itembrand.setText(brand);
        itemprice.setText(price);
        itemname.setText(name);
        itemsize.setText(size);

    }
}
