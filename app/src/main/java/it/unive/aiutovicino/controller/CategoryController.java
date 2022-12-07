package it.unive.aiutovicino.controller;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import it.unive.aiutovicino.General;
import it.unive.aiutovicino.model.CategoryModel;

public class CategoryController {

    public static List<CategoryModel> getAllCategories(){
        List<CategoryModel> categories = new ArrayList<>();
        String response = General.connect("https://europe-west1-ing-sw-c6b56.cloudfunctions.net/categories-getAllCategories", "GET", null);
        if(!response.equals("")) {
            try{
                JSONArray jArray = new JSONArray(response);
                for(int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);

                    if(json_data.has("id") && json_data.has("description") && json_data.has("coins")) {
                        CategoryModel category = new CategoryModel();
                        category.setId(String.valueOf(json_data.getInt("id")));
                        category.setDescription(json_data.getString("description"));
                        category.setCoins(json_data.getInt("coins"));
                        categories.add(category);
                    }
                }

            }
            catch (JSONException e) {
                Log.e("Error", "GetAllCategories Json Decode");
            }
        }
        return categories;
    }
}
