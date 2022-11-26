package it.unive.aiutovicino.model;

public class RankingModel {
    public int position;
    public String name;
    public String surname;
    public int coins;

    public RankingModel(int position, String name, String surname, int coins){
        this.position = position;
        this.name = name;
        this.surname = surname;
        this.coins = coins;
    }
}
