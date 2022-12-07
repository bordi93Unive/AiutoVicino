package it.unive.aiutovicino.controller;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

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

   /* public static CategoriaModel[] getAllCategory(){
        CategoriaModel[] categorie = new CategoriaModel[3];
        categorie[0] = new CategoriaModel(1, "Aiuto Lavori", 30);
        categorie[1] =  new CategoriaModel(2, "Baby Sitting", 20);
        categorie[2] = new CategoriaModel(3, "Dog sitting", 10);
        return categorie;
    }*/

    public static List<CategoriaModel> getAllCategories(){

        List<CategoriaModel> categorie = new ArrayList<>();

            try {
            URL url = new URL("https://europe-west1-ing-sw-c6b56.cloudfunctions.net/categories-getAllCategories");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            //conn.setDoInput(true);
            conn.setDoOutput(true);


            int responseCode=conn.getResponseCode();

            String response = "";
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response +=line;
                }
            }
            if(!response.equals("")) {
                JSONArray jArray = new JSONArray(response);
                for(int i = 0; i < jArray.length(); i++) {
                    CategoriaModel categoria = new CategoriaModel();
                    JSONObject json_data = jArray.getJSONObject(i);
                    categoria.id = json_data.getInt("id");
                    categoria.description = json_data.getString("description");
                    categoria.nCoin = json_data.getInt("coins");

                    categorie.add(categoria);
                }
            }
        } catch (
        IOException e) {
            Log.e("Error", "GetAllAnnuncements");
        } catch (
        JSONException e) {
            Log.e("Error", "Login Json Decode");
        }

         return categorie;
    }
}
