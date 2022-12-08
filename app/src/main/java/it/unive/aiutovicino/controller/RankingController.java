package it.unive.aiutovicino.controller;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import it.unive.aiutovicino.General;
import it.unive.aiutovicino.model.RankingModel;
import it.unive.aiutovicino.model.UserModel;

public class RankingController {
    public static void getUserScore() {
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("userId", General.user.getId());
        String response = General.connect("https://europe-west1-ing-sw-c6b56.cloudfunctions.net/ranking-getUserScore", "POST", queryParameters);

        if (!response.equals("")) {
            try {
                JSONArray JArr = new JSONArray(response);
                JSONObject jObject = JArr.getJSONObject(0);

                //General.user.setScore(100);
                if(jObject.has("score") && !jObject.getString("score").equals("null") ) {
                    General.user.setScore(Integer.valueOf(jObject.getString("score")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public static RankingModel[] getAllRankings(){
        RankingModel[] rankings = new RankingModel[3];
        rankings[0] = new RankingModel(1, "Ugo", 136);
        rankings[1] = new RankingModel(2, "Matte",  126);
        rankings[2] = new RankingModel(3, "Daniele",  16);
        return rankings;
    }
}
