package it.unive.aiutovicino.model;

public class RankingModel {
    public int position;
    public String nickname;
    public int coins;

    public RankingModel(int position, String nickname, int coins){
        this.position = position;
        this.nickname = nickname;
        this.coins = coins;
    }
}
