package com.example.sj.stylestockprojects;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sj.stylestockprojects.Firebase.PersonUpload;
import com.example.sj.stylestockprojects.Users.UserDTO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Mypage_information extends AppCompatActivity{
    Context mContext;

    private String id;
    private String user_name;
    private String user_gender;
    private String user_age;
    private EditText name;
    private EditText age;
    private UserDTO userDTO = new UserDTO();

    RadioGroup gender;
    final PersonUpload personUpload = new PersonUpload();
    private ArrayList<UserDTO> userDTOS = new ArrayList<>();

    //Mypage_framework mypage_framework = new Mypage_framework();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_info);

        name = (EditText) findViewById(R.id.user_name);
        age = (EditText) findViewById(R.id.user_age);
        gender = (RadioGroup) findViewById(R.id.user_gender);
        final RadioButton malerb = (RadioButton) findViewById(R.id.male_gender);
        final RadioButton femalerb = (RadioButton) findViewById(R.id.femal_gender);
        Button saveButton = (Button) findViewById(R.id.infosave_button);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            id = bundle.getString("id");
            user_name = bundle.getString("username", "");
            user_age = bundle.getString("age");
            user_gender = bundle.getString("gender");
            Log.e("username1234", user_name+" / "+user_age+" / "+user_gender);

        }

        name.setText(user_name);
        age.setText(user_age);
        if (gender.equals("남자")) {
            gender.check(R.id.male_gender);
        } else {
            gender.check(R.id.femal_gender);
        }


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (malerb.isChecked()) {
                    user_gender = malerb.getText().toString();
                }
                if (femalerb.isChecked()) {
                    user_gender = femalerb.getText().toString();
                }
                user_name = name.getText().toString();
                user_age = age.getText().toString();

                Log.e("saveUserinfo", user_name+" / "+user_age+" / "+user_gender);

                userDTO.setName(user_name);
                userDTO.setAge(user_age);
                userDTO.setGender(user_gender);
                Log.e("userinfo", userDTO.toString());

                personUpload.setPath("info");
                personUpload.setUsername(id);
                personUpload.setUserDTO(userDTO);
                personUpload.PersoninfoUpload();

                Toast.makeText(getApplication(), "성공적으로 저장되었습니다.", Toast.LENGTH_SHORT).show();


            }
        });


    }
    @Override
    public void onBackPressed() {
        Fragment fragment = new Mypage_framework();
        //replaceFragment(fragment);
        super.onBackPressed();

    }

    public void replaceFragment(Fragment fragment){
       /* Bundle bundle = new Bundle();
        bundle.putString("username",id);
        //Log.e("bundle_name",username);
        fragment.setArguments(bundle);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.navigation_mypage2,fragment);
        fragmentTransaction.commit();*/


        Bundle bundle = new Bundle();
        bundle.putString("username",id);
        //Log.e("bundle_name",username);
        fragment.setArguments(bundle);
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commitAllowingStateLoss();
        }
    }



}
