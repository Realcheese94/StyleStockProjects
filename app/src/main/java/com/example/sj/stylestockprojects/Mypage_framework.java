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

public class Mypage_framework extends Fragment {

    private String id;
    private String user_name;
    private String user_gender;
    private int user_age;
    private EditText name;
    private EditText age;
    private UserDTO userDTO = new UserDTO();
    final PersonUpload personUpload = new PersonUpload();



    @Override
    public void onStart(){

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

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.mypage_layout,container,false);
      name= (EditText)view.findViewById(R.id.user_name);
       RadioGroup gender = (RadioGroup) view.findViewById(R.id.user_gender);
       final EditText age = (EditText)view.findViewById(R.id.user_age);
       Button saveButton = (Button)view.findViewById(R.id.infosave_button);
       final RadioButton malerb = (RadioButton)view.findViewById(R.id.male_gender);
       final RadioButton femalerb = (RadioButton)view.findViewById(R.id.femal_gender);






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
               user_age = Integer.parseInt(age.getText().toString());

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