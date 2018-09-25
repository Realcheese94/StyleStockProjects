package com.example.sj.stylestockprojects.Firebase;

import android.widget.ArrayAdapter;

import com.example.sj.stylestockprojects.Userinfo.UserDTO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PersonUpload {
    private String path;
    private com.google.firebase.database.FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private UserDTO userDTO;
    private  String username;
    private ArrayList<String> userinfo;


    public void PersoninfoUpload(){
        firebaseDatabase = com.google.firebase.database.FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        databaseReference.child(username+"/"+path+"/").setValue(userDTO);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public UserDTO getUserDTO() {

        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
