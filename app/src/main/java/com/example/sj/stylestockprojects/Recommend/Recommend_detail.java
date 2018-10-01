package com.example.sj.stylestockprojects.Recommend;

import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sj.stylestockprojects.R;
import com.example.sj.stylestockprojects.Recommend.Crawlers20.TopJsoupAsyncTask;
import com.example.sj.stylestockprojects.Users.UserDTO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Recommend_detail extends AppCompatActivity {
    private String username;
    private String catagory;
    private String url;
    private String brand;
    private String price;
    private String name;
    private String [] url_list;
    private String gender;
    private int age;
    private String size;
    private Bundle bundle;
    private TextView name_text;
    private ImageView item_imageView;
    public static String item_url="";

    private UserDTO userDTO;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ListView listView;


    @Override
    public void onStart() {

        super.onStart();

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
        listView = (ListView)findViewById(R.id.recommend_list);

        name_text.setText(name + "와 관련된 추천 목록 입니다.");


    TopJsoupAsyncTask topJsoupAsyncTask = new TopJsoupAsyncTask();
    topJsoupAsyncTask.execute();
    Log.e("url_result",item_url);








        dataSetting();


    }


private void dataSetting(){
        MyAdapter myAdapter = new MyAdapter();
        for(int i =0;i<10;i++){
            myAdapter.addItem(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_home_black_24dp),"name","contents");
        }
        listView.setAdapter(myAdapter);
}




}



