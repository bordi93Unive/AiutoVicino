package it.unive.aiutovicino.controller;

import it.unive.aiutovicino.model.AnnuncioModel;
import it.unive.aiutovicino.model.CategoriaModel;

public class CategoriaController {
    public static CategoriaModel getCategoria(int id){
        switch(id){
            case 1:
                return new CategoriaModel(1, "Aiuto Lavori", 30);
            case 2:
                return new CategoriaModel(2, "Baby Sitting", 20);
            case 3:
                return new CategoriaModel(3, "Dog sitting", 10);
            default:
                return null;
        }
    }

    public static CategoriaModel[] getAllCategory(){
        CategoriaModel[] categorie = new CategoriaModel[3];
        categorie[0] = new CategoriaModel(1, "Aiuto Lavori", 30);
        categorie[1] =  new CategoriaModel(2, "Baby Sitting", 20);
        categorie[2] = new CategoriaModel(3, "Dog sitting", 10);
        return categorie;
    }
}
