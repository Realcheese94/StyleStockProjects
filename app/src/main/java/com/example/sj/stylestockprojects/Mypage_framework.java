package com.example.sj.stylestockprojects;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.sj.stylestockprojects.Firebase.PersonUpload;
import com.example.sj.stylestockprojects.Users.UserDTO;
import com.facebook.login.LoginManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.nhn.android.naverlogin.OAuthLogin;

import java.util.ArrayList;

public class Mypage_framework extends Fragment {
    Context mContext;

    private String id;
    private String user_name;
    private String user_gender;
    private String user_age;
    private EditText name;
    private EditText age;
    private UserDTO userDTO = new UserDTO();
    final PersonUpload personUpload = new PersonUpload();
    private ArrayList<UserDTO> userDTOS = new ArrayList<>();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Button total_logout,mypage_info;

    public static OAuthLogin mOAuthLoginModule;

    @Override
    public void onStart() {
        //personUpload.getInfoPerson();
        super.onStart();


    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            id = bundle.getString("username", "");
            Log.e("username", id);

        }
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(id + "/info/");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mypage_layout, container, false);
        name = (EditText) view.findViewById(R.id.user_name);
        final RadioGroup gender = (RadioGroup) view.findViewById(R.id.user_gender);
        final EditText age = (EditText) view.findViewById(R.id.user_age);
        Button saveButton = (Button) view.findViewById(R.id.infosave_button);
        final RadioButton malerb = (RadioButton) view.findViewById(R.id.male_gender);
        final RadioButton femalerb = (RadioButton) view.findViewById(R.id.femal_gender);
        total_logout = (Button)view.findViewById(R.id.total_logout);
        mypage_info = (Button)view.findViewById(R.id.mypage_info);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userDTOS.clear();

                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    Log.e("michal", dataSnapshot.getValue().toString());
                    UserDTO userDTO = dataSnapshot.getValue(UserDTO.class);
                    Log.e("michal_info", userDTO.getName());
                    name.setText(userDTO.getName().toString());

                    Log.e("michal_age", userDTO.getAge());
                    age.setText(userDTO.getAge());

                    if (userDTO.getGender().equals("남자")) {
                        gender.check(R.id.male_gender);
                    } else {
                        gender.check(R.id.femal_gender);
                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


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

                userDTO.setName(user_name);
                userDTO.setAge(user_age);
                userDTO.setGender(user_gender);
                Log.e("userinfo", userDTO.toString());

                personUpload.setPath("info");
                personUpload.setUsername(id);
                personUpload.setUserDTO(userDTO);
                personUpload.PersoninfoUpload();

                Toast.makeText(getActivity(), "성공적으로 저장되었습니다.", Toast.LENGTH_SHORT).show();


            }
        });
        //통합 로그아웃
        total_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TotalLogout();
            }
        });
        mypage_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),Mypage_information.class);
                Bundle bundle = new Bundle();
                bundle.clear();

                bundle.putString("username",user_name);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        return view;
    }




    //소셜로그인 통합 로그아웃
    private void TotalLogout(){
        SharedPreferences auto = getActivity().getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = auto.edit();
        editor.clear();
        editor.commit();

        LoginManager.getInstance().logOut();
        UserManagement.requestLogout(new LogoutResponseCallback() {
            @Override
            public void onCompleteLogout() {
            }
        });
        mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.logout(mContext);

        Intent intent = new Intent(getActivity(),LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

}