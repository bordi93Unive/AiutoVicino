package it.unive.aiutovicino.controller;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import it.unive.aiutovicino.General;
import it.unive.aiutovicino.model.UserModel;


public class UserController {
    public static Boolean register(UserModel user){
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("email", user.getEmail());
        queryParameters.put("password", user.getPassword());
        queryParameters.put("surname", user.getSurname());
        queryParameters.put("name", user.getName());
        queryParameters.put("nickname", user.getNickname());
        queryParameters.put("description", "");

        String response = General.connect("https://europe-west1-ing-sw-c6b56.cloudfunctions.net/registration", "POST", queryParameters);

        return !response.equals("");
    }

    public static Boolean update(UserModel user){
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("email", user.getEmail());
        queryParameters.put("password", user.getPassword());
        queryParameters.put("surname", user.getSurname());
        queryParameters.put("name", user.getName());
        queryParameters.put("nickname", user.getNickname());
        queryParameters.put("description", user.getDescription());

        String response = General.connect("https://europe-west1-ing-sw-c6b56.cloudfunctions.net/user-updateUser", "POST", queryParameters);

        return !response.equals("");
    }

    public static UserModel getAuth(String email, String password) {
        UserModel user = null;

        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("email", email);
        queryParameters.put("password", password);
        String response = General.connect("https://europe-west1-ing-sw-c6b56.cloudfunctions.net/login", "POST", queryParameters);

        if (!response.equals("")) {
            try {
                JSONArray JArr = new JSONArray(response);
                JSONObject jObject = JArr.getJSONObject(0);

                if(jObject.has("id") && jObject.has("token") && jObject.has("email") && jObject.has("surname") && jObject.has("name")) {
                    user = new UserModel();
                    user.setId(jObject.getString("id"));
                    user.setToken(jObject.getString("token"));
                    user.setEmail(jObject.getString("email"));
                    user.setSurname(jObject.getString("surname"));
                    user.setName(jObject.getString("name"));

                    if(jObject.has("nickname")) {
                        user.setNickname(jObject.getString("nickname"));
                    }

                    if(jObject.has("description")) {
                        user.setDescription(jObject.getString("description"));
                    }

                    if(jObject.has("admin")) {
                        user.setDescription(jObject.getString("admin"));
                    }
                    else{
                        user.setAdmin(false);
                    }

                    if(jObject.has("admin")) {
                        user.setApproved(jObject.getBoolean("approved"));
                    }
                    else{
                        user.setApproved(false);
                    }
                }
            } catch(JSONException e) {
                Log.e("Error", "Login Json Decode");
            }
        }

        return user;
    }

    public static UserModel getUserById(String idUser)  {
        UserModel user = null;

        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("userId", idUser);
        String response = General.connect("https://europe-west1-ing-sw-c6b56.cloudfunctions.net/user-getUserById", "POST", queryParameters);

        if (!response.equals("")) {
            try {
                JSONArray JArr = new JSONArray(response);
                JSONObject jObject = JArr.getJSONObject(0);

                if(jObject.has("id") && jObject.has("email") && jObject.has("surname") && jObject.has("name")) {
                    user = new UserModel();
                    user.setId(jObject.getString("id"));
                    user.setEmail(jObject.getString("email"));
                    user.setSurname(jObject.getString("surname"));
                    user.setName(jObject.getString("name"));

                    if(jObject.has("nickname")) {
                        user.setNickname(jObject.getString("nickname"));
                    }

                    if(jObject.has("description")) {
                        user.setDescription(jObject.getString("description"));
                    }

                    if(jObject.has("admin")) {
                        user.setDescription(jObject.getString("admin"));
                    }
                    else{
                        user.setAdmin(false);
                    }

                    if(jObject.has("admin")) {
                        user.setApproved(jObject.getBoolean("approved"));
                    }
                    else{
                        user.setApproved(false);
                    }
                }
            } catch(JSONException e) {
                Log.e("Error", "GetUserById Json Decode");
            }
        }

        return user;
    }
}
