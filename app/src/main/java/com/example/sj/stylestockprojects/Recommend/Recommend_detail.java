package com.example.sj.stylestockprojects.Recommend;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sj.stylestockprojects.MainActivity;
import com.example.sj.stylestockprojects.R;
import com.example.sj.stylestockprojects.Recommend.Crawlers20.TopJsoupAsyncTask;
import com.example.sj.stylestockprojects.Users.UserDTO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class Recommend_detail extends AppCompatActivity {
    private String username;
    private String catagory;
    private String url;
    private String brand;
    private String price;
    private String name;
    private String[] url_list;
    private String gender;
    private int age;
    private String size;
    private Bundle bundle;
    private TextView name_text;
    private ImageView item_imageView;
    public static String item_url = "";
    private int random_num;

    MyAdapter myAdapter;
    private UserDTO userDTO;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ListView listView;
    Button close_recommend;


    /*
           bundle.putString("username",username);
           bundle.putString("catagory",catagory);
           bundle.putString("brand",Products.get(pos).product_brand);
           bundle.putString("price",Products.get(pos).product_price);
           bundle.putString("name",Products.get(pos).product_name);
           bundle.putString("size",Products.get(pos).product_size);
           bundle.putString("url",Products.get(pos).product_url);

     */
    @Override
    public void onStart() {

        super.onStart();

        Log.e("url_result1234", item_url);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend_detail);

        bundle = getIntent().getExtras();
        username = bundle.getString("username");
        catagory = bundle.getString("catagory");
        brand = bundle.getString("brand");
        price = bundle.getString("price");
        name = bundle.getString("name");
        size = bundle.getString("size");
        url = bundle.getString("url");


        TopJsoupAsyncTask topJsoupAsyncTask = new TopJsoupAsyncTask();
        topJsoupAsyncTask.execute(name);

        Log.e("recommend_list", username + "/" + catagory + "/" + name);


        //파이어베이스 로 UserDTO저장
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(username + "/info/");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    userDTO = dataSnapshot.getValue(UserDTO.class);
                    Log.e("recommend_name", userDTO.getAge());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        name_text = (TextView) findViewById(R.id.product_name);
        listView = (ListView) findViewById(R.id.recommend_list);

        name_text.setText(name + "와 관련된 추천 목록 입니다.");



        dataSetting();


    }


    private void dataSetting() {
        Random random = new Random();
        myAdapter = new MyAdapter();
        listView = (ListView) findViewById(R.id.recommend_list);
        if(item_url != ""){
            url_list = item_url.split("-");
            for(int i=0;i<url_list.length;i++){
                Log.d("urllist_result1", "onCreate: "+url_list[i]);
            }
            for (int i = 0; i < 20; i++) {
                random_num = random.nextInt(30)+1;
                myAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_home_black_24dp), url_list[random_num].split("m/")[0].toString(), url_list[random_num].toString(),url_list[random_num]);
            }
        }else{
            for (int i = 0; i < 10; i++) {
                myAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_home_black_24dp), "name", "contents", String.valueOf(R.drawable.logotransparent));
            }
        }

        listView.setAdapter(myAdapter);
    }


    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }

}



