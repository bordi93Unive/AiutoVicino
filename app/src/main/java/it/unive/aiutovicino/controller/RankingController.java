package it.unive.aiutovicino.controller;

import it.unive.aiutovicino.model.RankingModel;

public class RankingController {
    public static RankingModel[] getAllRankings(){
        RankingModel[] rankings = new RankingModel[3];
        rankings[0] = new RankingModel(1, "Ugo", "Ughino", 136);
        rankings[1] = new RankingModel(1, "Uga", "Ughina", 126);
        rankings[2] = new RankingModel(1, "Ugato", "Prugo", 16);
        return rankings;
    }
}
