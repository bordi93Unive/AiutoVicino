package com.example.aiutovicino.model;

public class UserModel {
    public int id;
    public String email;
    public String password;
    public String surname;
    public String nickname;
    public String name;
    public boolean state;

    public UserModel(int id, String email, String password,String nickname) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}
