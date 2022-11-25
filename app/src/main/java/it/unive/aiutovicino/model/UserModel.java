package it.unive.aiutovicino.model;

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

}
