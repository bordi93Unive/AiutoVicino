package it.unive.aiutovicino.controller;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
                JSONObject jObject = new JSONObject(response);
                if(jObject.has("score") && !jObject.getString("score").equals("null") ) {
                    General.user.setScore(Integer.valueOf(jObject.getString("score")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public static List<RankingModel> getAllRankings(){
        List<RankingModel> rankings = new ArrayList<>();

        String response = General.connect("https://europe-west1-ing-sw-c6b56.cloudfunctions.net/ranking-getRanking", "GET", null);

        if(!response.equals("")) {
            try{
                JSONArray jArray = new JSONArray(response);
                for(int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);

                    if(json_data.has("id") && json_data.has("userNickname") && json_data.has("userId") && json_data.has("nCoin")) {
                        RankingModel ranking = new RankingModel();

                        ranking.setId(json_data.getString("id"));
                        ranking.setUserNickname(json_data.getString("userNickname"));
                        ranking.setUserId(json_data.getString("userId"));
                        ranking.setCoins(Integer.valueOf(json_data.getString("nCoin")));
                        ranking.setPosition(i + 1);

                        rankings.add(ranking);
                    }
                }

            }
            catch (JSONException e) {
                Log.e("Error", "GetNotApprovedUsers Json Decode");
            }
        }

        return rankings;
    }
}
