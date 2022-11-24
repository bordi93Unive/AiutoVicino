package com.example.aiutovicino.controller;

import com.example.aiutovicino.model.AnnuncioModel;
import com.example.aiutovicino.model.UserModel;

public class AnnuncioController {
    public static AnnuncioModel getAnnouncment(int id){
        switch(id){
            case 1:
                return new AnnuncioModel(1, "Sfalcio prato", 1,"01/01/2023","15:15","Bassano",3,true);
            case 2:
                return new AnnuncioModel(2, "Baby Sitting", 2,"23/12/2022","10:15","Trieste",2,false);
            case 3:
                return new AnnuncioModel(3, "Dog sitting", 3,"02/08/1993","19:20","Rosà",1,true);
            default:
                return null;
        }
    }

    public static AnnuncioModel[] getAllAnnouncments(){
        AnnuncioModel[] annunci = new AnnuncioModel[3];
        annunci[0] = new AnnuncioModel(1, "Sfalcio prato", 1,"01/01/2023","15:15","Bassano",3,true);
        annunci[1] = new AnnuncioModel(2, "Baby Sitting", 2,"23/12/2022","10:15","Trieste",2,false);
        annunci[2] = new AnnuncioModel(3, "Dog sitting", 3,"02/08/1993","19:20","Rosà",1,true);
        return annunci;
    }
}
