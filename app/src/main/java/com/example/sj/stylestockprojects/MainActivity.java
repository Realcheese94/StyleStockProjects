package com.example.sj.stylestockprojects;


import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.nio.file.Paths;
import java.util.ArrayList;

import static android.app.PendingIntent.getActivity;


public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Fragment fragment = null;
    private final int CAMERA_CODE = 1111;
    private String username;
    private Bundle frag_args;
    public Userinformation userinformation;
    public String [] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.M){
            for (String permission : permissions){
                int result = PermissionChecker.checkSelfPermission(this,permission);
                if(result == PermissionChecker.PERMISSION_GRANTED){

                }
                else{

                }
            }
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment(new Closet_framework());
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");


        Log.d("main으로가는 username",username);


    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new Closet_framework();
                    break;
                case R.id.navigation_dashboard:
                    fragment = new Diary_framework();
                    break;
                case R.id.navigation_notifications:
                    fragment = new Transaction_framework();
                    break;
                case R.id.navigation_mypage:
                    fragment = new Recommend_framework();
                    break;
                case R.id.navigation_mypage2:
                    fragment = new Mypage_framework();
                    break;
            }
            return loadFragment(fragment);
        }
    };


    @Override
    public void onStart(){
        loadFragment(new Closet_framework());

        super.onStart();


    }


    private boolean loadFragment(Fragment fragment) {
        Bundle bundle = new Bundle();
        bundle.putString("username",username);
        //Log.e("bundle_name",username);
       fragment.setArguments(bundle);
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commitAllowingStateLoss();
            return true;
        }
        return false;
    }

    private void doRequestPermission(){
        String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        ArrayList<String> notGrantedPermissions = new ArrayList<>();
        for(String perm : permissions){

        }
    }

}
