package com.example.sj.stylestockprojects.Firebase;

import com.example.sj.stylestockprojects.Users.UserDTO;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PersonUpload {
    private String path;
    private UserDTO userDTO;
    private String username;
    private ArrayList<String> userinfo;
    public FirebaseDatabase firebaseDatabase;
    public DatabaseReference databaseReference;

    public void PersoninfoUpload(){
        firebaseDatabase = FirebaseDatabase.getInstance();
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

    public ArrayList<String> getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(ArrayList<String> userinfo) {
        this.userinfo = userinfo;
    }
}

