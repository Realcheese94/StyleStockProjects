package com.example.sj.stylestockprojects;

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
import com.example.sj.stylestockprojects.Userinfo.UserDTO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Mypage_framework extends Fragment {

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


    @Override
    public void onStart(){
        //personUpload.getInfoPerson();
        super.onStart();



    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        if(bundle != null){
            id = bundle.getString("username","");
            Log.e("username",id);

        }
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(id+"/info/");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.mypage_layout,container,false);
      name= (EditText)view.findViewById(R.id.user_name);
       final RadioGroup gender = (RadioGroup) view.findViewById(R.id.user_gender);
       final EditText age = (EditText)view.findViewById(R.id.user_age);
       Button saveButton = (Button)view.findViewById(R.id.infosave_button);
       final RadioButton malerb = (RadioButton)view.findViewById(R.id.male_gender);
       final RadioButton femalerb = (RadioButton)view.findViewById(R.id.femal_gender);

       databaseReference.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(DataSnapshot dataSnapshot) {
            userDTOS.clear();

               for (DataSnapshot messageData : dataSnapshot.getChildren()){
                   Log.e("michal",dataSnapshot.getValue().toString());
                   UserDTO userDTO = dataSnapshot.getValue(UserDTO.class);
                   Log.e("michal_info",userDTO.getName());
                   name.setText(userDTO.getName().toString());

                   Log.e("michal_age",userDTO.getAge());
                   age.setText(userDTO.getAge());

                    if(userDTO.getGender().equals("남자")){
                        gender.check(R.id.male_gender);
                    }
                    else {
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

               if(malerb.isChecked()){
                   user_gender = malerb.getText().toString();
               }
               if(femalerb.isChecked()){
                   user_gender = femalerb.getText().toString();
               }
               user_name = name.getText().toString();
               user_age = age.getText().toString();

               userDTO.setName(user_name);
               userDTO.setAge(user_age);
               userDTO.setGender(user_gender);
               Log.e("userinfo",userDTO.toString());

               personUpload.setPath("info");
               personUpload.setUsername(id);
               personUpload.setUserDTO(userDTO);
               personUpload.PersoninfoUpload();

               Toast.makeText(getActivity(),"성공적으로 저장되었습니다.",Toast.LENGTH_SHORT).show();



           }
       });




        return view;
    }


}