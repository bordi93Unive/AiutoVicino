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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import it.unive.aiutovicino.General;
import it.unive.aiutovicino.model.CategoryModel;
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
        queryParameters.put("userId", user.getId());
        queryParameters.put("email", user.getEmail());
        if(user.getPassword() != null && !user.getPassword().equals("")) {
            queryParameters.put("password", user.getPassword());
        }
        queryParameters.put("surname", user.getSurname());
        queryParameters.put("name", user.getName());
        queryParameters.put("nickname", user.getNickname());
        queryParameters.put("description", user.getDescription());

        String response = General.connect("https://europe-west1-ing-sw-c6b56.cloudfunctions.net/user-updateUser", "POST", queryParameters);

        return !response.equals("");
    }

    public static Boolean approveUser(String userId){
        if(General.user.isAdmin()) {
            Map<String, String> queryParameters = new HashMap<>();
            queryParameters.put("adminUserId", General.user.getId());
            queryParameters.put("userId", userId);

            String response = General.connect("https://europe-west1-ing-sw-c6b56.cloudfunctions.net/user-approveUser", "POST", queryParameters);

            return !response.equals("");
        }
        return false;
    }

    public static Boolean deleteUser(String userId){
        if(General.user.isAdmin()) {
            Map<String, String> queryParameters = new HashMap<>();
            queryParameters.put("adminUserId", General.user.getId());
            queryParameters.put("userId", userId);

            String response = General.connect("https://europe-west1-ing-sw-c6b56.cloudfunctions.net/user-deleteUser", "POST", queryParameters);

            return !response.equals("");
        }
        return false;
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
                        user.setAdmin(jObject.getBoolean("admin"));
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

                    if(jObject.has("expiration")) {
                        JSONObject expiration = jObject.getJSONObject("expiration");
                        if(expiration.has("_seconds") && expiration.has("_nanoseconds")){
                            String t = expiration.getInt("_seconds") + "" + expiration.getInt("_nanoseconds");
                            long timestamp = Long.valueOf(t);
                            timestamp /= 1000000;
                            user.setTokenExpiration(timestamp);
                        }
                    }
                }
            } catch(JSONException e) {
                Log.e("Error", "Login Json Decode");
            }
        }

        return user;
    }

    public static UserModel getUserById(String userId)  {
        UserModel user = null;

        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("userId", userId);
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
                        user.setAdmin(jObject.getBoolean("admin"));
                    }
                    else{
                        user.setAdmin(false);
                    }

                    if(jObject.has("approved")) {
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

    public static List<UserModel> getNotApprovedUsers(String userId) {
        List<UserModel> users = new ArrayList<>();

        if(General.user.isAdmin()){
            Map<String, String> queryParameters = new HashMap<>();
            queryParameters.put("userId", userId);
            String response = General.connect("https://europe-west1-ing-sw-c6b56.cloudfunctions.net/user-getNotApprovedUsers", "POST", queryParameters);

            if(!response.equals("")) {
                try{
                    JSONArray jArray = new JSONArray(response);
                    for(int i = 0; i < jArray.length(); i++) {
                        JSONObject json_data = jArray.getJSONObject(i);

                        if(json_data.has("id") && json_data.has("email") && json_data.has("surname") && json_data.has("name")) {
                            UserModel user = new UserModel();
                            user.setId(json_data.getString("id"));
                            user.setEmail(json_data.getString("email"));
                            user.setSurname(json_data.getString("surname"));
                            user.setName(json_data.getString("name"));
                            if(json_data.has("nickname")) {
                                user.setNickname(json_data.getString("nickname"));
                            }

                            if(json_data.has("description")) {
                                user.setDescription(json_data.getString("description"));
                            }
                            user.setApproved(false);
                            users.add(user);
                        }
                    }

                }
                catch (JSONException e) {
                    Log.e("Error", "GetNotApprovedUsers Json Decode");
                }
            }
        }

        return users;
    }
}
