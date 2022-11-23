package com.example.aiutovicino.controller;

import com.example.aiutovicino.model.AnnuncioModel;
import com.example.aiutovicino.model.UserModel;

public class AnnuncioController {
    public static AnnuncioModel[] getAllAnnouncments(){
        AnnuncioModel[] annunci = new AnnuncioModel[3];
        annunci[0] = new AnnuncioModel(1, "Sfalcio prato", 1);
        annunci[1] = new AnnuncioModel(2, "Baby Sitting", 2);
        annunci[2] = new AnnuncioModel(3, "Dog sitting", 3);
        return annunci;
    }
}
