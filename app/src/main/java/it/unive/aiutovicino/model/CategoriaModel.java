package it.unive.aiutovicino.model;

public class CategoriaModel {
    public int id;
    public String description;
    public int nCoin;


    public CategoriaModel(){

    }

    public CategoriaModel(int id, String description, int nCoin){
        this.id = id;
        this.description = description;
        this.nCoin = nCoin;
    }
}
