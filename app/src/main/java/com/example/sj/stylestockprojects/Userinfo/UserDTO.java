package com.example.sj.stylestockprojects.Userinfo;

public class UserDTO {
    private String name;
    private String gender;
    private String age;

    public UserDTO(String name,String gender,String age){
        this.name= name;
        this.gender = gender;
        this.age = age;
    }
    public UserDTO(){

    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(String age) {
        this.age = age;
    }

}
