package com.example.aiutovicino.controller;

import com.example.aiutovicino.model.UserModel;

public class UserController {

    public static UserModel getUserByEmail(String $email){
        if($email.equals("ugo@ugami.it")){
            return new UserModel(1, "ugo@ugami.it", "ughino");
        }
        return null;
    }


}
