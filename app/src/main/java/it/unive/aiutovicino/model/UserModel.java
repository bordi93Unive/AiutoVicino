package it.unive.aiutovicino.model;

public class UserModel {
    public String id;
    public String token;
    public String email;
    public String password;
    public String name;
    public String surname;
    public String nickname;
    public String description;
    public boolean state;
    public boolean admin;

    public UserModel(){
    }

    public UserModel(String id, String token, String email,String password, String name, String surname,String nickname,Boolean admin){
        this.id = id;
        this.token = token;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
        this.admin = admin;
        this.password = password;
    }

    public UserModel(String id, String name, String surname, String email, String password, String nickname, boolean state) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.state = state;
    }

}
