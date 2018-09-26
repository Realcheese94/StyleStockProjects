package com.example.sj.stylestockprojects;

import com.example.sj.stylestockprojects.Userinfo.UserDTO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Userinformation {

    public String userid;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    ArrayList<UserDTO> users = new ArrayList<>();
    //mReference = mDatabase.getReference(username+"/"+catagory);


    public Userinformation(String username){
        this.userid = username;
    }
    public ArrayList<UserDTO> checkUserdata() {
        databaseReference = firebaseDatabase.getReference(userid + "/info/");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                users.clear();

                for (DataSnapshot messageData : dataSnapshot.getChildren()) {
                    UserDTO user = messageData.getValue(UserDTO.class);
                    users.add(user);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return users;

    }




    public void setUserid(String userid){
        this.userid = userid;
    }
    public String getUserid(){
        return userid;
    }
}
