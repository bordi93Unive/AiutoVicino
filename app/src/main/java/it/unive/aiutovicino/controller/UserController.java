package it.unive.aiutovicino.controller;

import it.unive.aiutovicino.model.UserModel;

public class UserController {

    public static UserModel getUserByEmail(String $email){
        if($email.equals("ugo@ugami.it")){
            return new UserModel(1, "ugo@ugami.it", "ughino");
        }
        return null;
    }


}
