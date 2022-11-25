package it.unive.aiutovicino.controller;

import it.unive.aiutovicino.model.UserModel;

public class UserController {

    public static UserModel getUserByEmail(String email){
        /*attenzione che questo if viene eseguito appena parte l'app. Quindi prende la stringa hardcoded in fragment_login*/
        if(email.equals("ugo@ugami.it")){
            return creaUgo();
        }
        return null;
    }

    public static UserModel getUserByID(int id){

            return creaUgo();
    }

    public static UserModel creaUgo(){
        return new UserModel(1,"Ugo","Ughino", "ugo@ugami.it", "ughino", "Ughino",true);
    }


}
