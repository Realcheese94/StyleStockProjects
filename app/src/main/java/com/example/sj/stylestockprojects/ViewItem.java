package com.example.sj.stylestockprojects;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

public class ViewItem extends AppCompatActivity {
    ImageButton itemImage;
    Button updateBtn,deleteBtn;
    EditText itemprice,itembrand,itemsize;
    TextView itemname;
    String catagory,brand,price,name,size,url;
    Bundle bundle;
    Spinner itemcatagory;
    ArrayAdapter spinnerAdapter;
    String username;

    String update_itemname,update_itemprice,update_itembrand,update_itemsize;
    String delete_itemname;

    private FirebaseDatabase firebaseDatabase ;
    private DatabaseReference databaseReference ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.7f;
        setContentView(R.layout.activity_view_item);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        final String[] data = getResources().getStringArray(R.array.catagory);
        spinnerAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,data);

        bundle=getIntent().getExtras();
        username = bundle.getString("username");
        catagory = bundle.getString("catagory");
        brand = bundle.getString("brand");
        price = bundle.getString("price");
        name = bundle.getString("name");
        size = bundle.getString("size");
        url = bundle.getString("url");

        itemImage = (ImageButton) findViewById(R.id.itemImage);
        itemname = (TextView)findViewById(R.id.itemname);
        itemprice = (EditText)findViewById(R.id.itemprice);
        itembrand = (EditText)findViewById(R.id.itemseller);
        itemsize = (EditText)findViewById(R.id.itemsize);
        updateBtn = (Button)findViewById(R.id.updateButton);
        deleteBtn = (Button)findViewById(R.id.deleteButton);
        itemcatagory = (Spinner)findViewById(R.id.catagoryspinner);

        itemcatagory.setAdapter(spinnerAdapter);

        Glide.with(this).load(url).apply(new RequestOptions().override(180,280)).into(itemImage);
        itembrand.setText(brand);
        itemprice.setText(price);
        itemname.setText(name);
        itemsize.setText(size);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_itemname = itemname.getText().toString();
                update_itemprice = itemprice.getText().toString();
                update_itembrand = itembrand.getText().toString();
                update_itemsize = itemsize.getText().toString();

                Update update = new Update(update_itemname,update_itemprice,update_itembrand,update_itemsize,url);
                Map<String,Object> postvalue = update.toMap();
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put(username+"/"+catagory+"/"+update_itemname,postvalue);
                databaseReference.updateChildren(childUpdates);

                Toast.makeText(getApplicationContext(),"수정되었습니다",Toast.LENGTH_SHORT);

                finish();
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete_itemname = itemname.getText().toString();

                Update update = new Update(null,null,null,null,null);
                Map<String,Object> postvalue = update.toMap();
                Map<String, Object> childUpdates = new HashMap<>();
                childUpdates.put(username+"/"+catagory+"/"+delete_itemname,postvalue);
                databaseReference.updateChildren(childUpdates);

                Toast.makeText(getApplicationContext(),"삭제되었습니다",Toast.LENGTH_SHORT);

                finish();
            }
        });

    }

    @IgnoreExtraProperties
    public class Update {

        public String name;
        public String price;
        public String brand;
        public String size;
        public String url;

        public Update() {
            // Default constructor required for calls to DataSnapshot.getValue(Post.class)
        }

        public Update(String name, String price, String brand, String size,String url) {
            this.name = name;
            this.price = price;
            this.brand = brand;
            this.size = size;
            this.url=url;
        }

        @Exclude
        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("product_name", name);
            result.put("product_price", price);
            result.put("product_brand", brand);
            result.put("product_size", size);
            result.put("product_url",url);
            return result;
        }

    }
}
