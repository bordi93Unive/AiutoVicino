package com.example.aiutovicino.model;

public class UserModel {
    public int id;
    public String email;
    public String password;
    public String surname;
    public String nickname;
    public String name;
    public boolean state;

    public UserModel(int id, String name, String surname, String email, String password,String nickname,boolean state) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.state = state;
    }

    public void setUserModel(String name, String surname, String email, String nickname){
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.nickname = nickname;
    }

    public void getUserModel(String name, String surname, String email, String nickname){
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.nickname = nickname;
    }



    public String getName(){
        return this.name;
    }

    public String getSurname(){
        return this.name;
    }
    public String getEmail(){
        return this.email;
    }
}
