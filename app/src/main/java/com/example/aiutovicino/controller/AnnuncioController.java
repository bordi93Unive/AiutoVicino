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
        AnnuncioModel[] annunci = new AnnuncioModel[15];
        annunci[0] = new AnnuncioModel(1, "Sfalcio prato", 1,"01/01/2023","15:15","Bassano",3,true);
        annunci[1] = new AnnuncioModel(2, "Baby Sitting", 2,"23/12/2022","10:15","Trieste",2,false);
        annunci[2] = new AnnuncioModel(3, "Dog sitting", 3,"02/08/1993","19:20","Rosà",1,true);
        annunci[3] = new AnnuncioModel(2, "Baby Sitting", 2,"23/12/2022","10:15","Trieste",2,false);
        annunci[4] = new AnnuncioModel(3, "Dog sitting", 3,"02/08/1993","19:20","Rosà",1,true);
        annunci[5] = new AnnuncioModel(1, "Sfalcio prato", 1,"01/01/2023","15:15","Bassano",3,true);
        annunci[6] = new AnnuncioModel(2, "Baby Sitting", 2,"23/12/2022","10:15","Trieste",2,false);
        annunci[7] = new AnnuncioModel(3, "Dog sitting", 3,"02/08/1993","19:20","Rosà",1,true);
        annunci[8] = new AnnuncioModel(1, "Sfalcio prato", 1,"01/01/2023","15:15","Bassano",3,true);
        annunci[9] = new AnnuncioModel(2, "Baby Sitting", 2,"23/12/2022","10:15","Trieste",2,false);
        annunci[10] = new AnnuncioModel(3, "Dog sitting", 3,"02/08/1993","19:20","Rosà",1,true);
        annunci[11] = new AnnuncioModel(1, "Sfalcio prato", 1,"01/01/2023","15:15","Bassano",3,true);
        annunci[12] = new AnnuncioModel(2, "Baby Sitting", 2,"23/12/2022","10:15","Trieste",2,false);
        annunci[13] = new AnnuncioModel(3, "Dog sitting", 3,"02/08/1993","19:20","Rosà",1,true);
        annunci[14] = new AnnuncioModel(1, "Sfalcio prato", 1,"01/01/2023","15:15","Bassano",3,true);
        return annunci;
    }

    public static AnnuncioModel[] getAllMyAnnouncments(){
        AnnuncioModel[] annunci = new AnnuncioModel[12];

        annunci[0] = new AnnuncioModel(2, "Baby Sitting", 2,"23/12/2022","10:15","Trieste",2,false);
        annunci[1] = new AnnuncioModel(3, "Dog sitting", 3,"02/08/1993","19:20","Rosà",1,true);
        annunci[2] = new AnnuncioModel(1, "Sfalcio prato", 1,"01/01/2023","15:15","Bassano",3,true);
        annunci[3] = new AnnuncioModel(2, "Baby Sitting", 2,"23/12/2022","10:15","Trieste",2,false);
        annunci[4] = new AnnuncioModel(3, "Dog sitting", 3,"02/08/1993","19:20","Rosà",1,true);
        annunci[5] = new AnnuncioModel(1, "Sfalcio prato", 1,"01/01/2023","15:15","Bassano",3,true);
        annunci[6] = new AnnuncioModel(2, "Baby Sitting", 2,"23/12/2022","10:15","Trieste",2,false);
        annunci[7] = new AnnuncioModel(3, "Dog sitting", 3,"02/08/1993","19:20","Rosà",1,true);
        annunci[8] = new AnnuncioModel(1, "Sfalcio prato", 1,"01/01/2023","15:15","Bassano",3,true);
        annunci[9] = new AnnuncioModel(2, "Baby Sitting", 2,"23/12/2022","10:15","Trieste",2,false);
        annunci[10] = new AnnuncioModel(3, "Dog sitting", 3,"02/08/1993","19:20","Rosà",1,true);
        annunci[11] = new AnnuncioModel(1, "Sfalcio prato", 1,"01/01/2023","15:15","Bassano",3,true);

        return annunci;
    }
}
