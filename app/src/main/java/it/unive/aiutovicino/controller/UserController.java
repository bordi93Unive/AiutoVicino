package it.unive.aiutovicino.controller;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;
import it.unive.aiutovicino.model.UserModel;


public class UserController {
    public static UserModel getAuth(String email, String password)  {
        UserModel user = null;

        try {
            URL url = new URL("https://europe-west1-ing-sw-c6b56.cloudfunctions.net/login");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("email", email)
                    .appendQueryParameter("password", password);
            String query = builder.build().getEncodedQuery();

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            int responseCode=conn.getResponseCode();

            String response = "";
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response +=line;
                }
            }
            if(!response.equals(""))
            {
                JSONArray JArr = new JSONArray(response);
                JSONObject jObject = JArr.getJSONObject(0);

                user = new UserModel();
                user.setId(jObject.getString("id"));
                user.setToken(jObject.getString("token"));
                user.setEmail(jObject.getString("email"));
                user.setSurname(jObject.getString("surname"));
                user.setName(jObject.getString("name"));
                user.setNickname(jObject.getString("nickname"));
                user.setDescription(jObject.getString("description"));
                user.setAdmin(jObject.getBoolean("admin"));
                user.setApproved(jObject.getBoolean("approved"));
            }
        } catch (IOException e) {
            Log.e("Error", "Login");
        } catch (JSONException e) {
            Log.e("Error", "Login Json Decode");
        }

        return user;
    }

    public static UserModel getUserById(String idUser)  {
        UserModel user = null;

        try {
            URL url = new URL("https://europe-west1-ing-sw-c6b56.cloudfunctions.net/user-getUserById");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("userId", idUser);
            String query = builder.build().getEncodedQuery();

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            int responseCode=conn.getResponseCode();

            String response = "";
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response +=line;
                }
            }
            if(!response.equals(""))
            {
                JSONArray JArr = new JSONArray(response);
                JSONObject jObject = JArr.getJSONObject(0);

                user = new UserModel();
                user.setId(jObject.getString("id"));
                user.setEmail(jObject.getString("email"));
                user.setSurname(jObject.getString("surname"));
                user.setName(jObject.getString("name"));
                user.setNickname(jObject.getString("nickname"));
                user.setDescription(jObject.getString("description"));
                user.setAdmin(jObject.getBoolean("admin"));
                user.setApproved(jObject.getBoolean("approved"));
            }
        } catch (IOException e) {
            Log.e("Error", "Login");
        } catch (JSONException e) {
            Log.e("Error", "Login Json Decode");
        }

        return user;
    }

    public static Boolean register(UserModel user){
        boolean result = false;
        try {
            URL url = new URL("https://europe-west1-ing-sw-c6b56.cloudfunctions.net/registration");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("email", user.getEmail())
                    .appendQueryParameter("password", user.getPassword())
                    .appendQueryParameter("name", user.getName())
                    .appendQueryParameter("surname", user.getSurname())
                    .appendQueryParameter("nickname", user.getNickname())
                    .appendQueryParameter("description", "");
            String query = builder.build().getEncodedQuery();

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            int responseCode=conn.getResponseCode();

            String response = "";
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                result = true;
            }
        } catch (IOException e) {
            Log.e("Error", "Login");
        }

        return result;
    }

    public static Boolean updateData(UserModel user){
        boolean result = false;
        try {
            URL url = new URL("https://europe-west1-ing-sw-c6b56.cloudfunctions.net/user-updateUser");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("email", user.getEmail())
                    .appendQueryParameter("password", user.getPassword())
                    .appendQueryParameter("name", user.getName())
                    .appendQueryParameter("surname", user.getSurname())
                    .appendQueryParameter("nickname", user.getNickname())
                    .appendQueryParameter("description", user.getDescription());
            String query = builder.build().getEncodedQuery();

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();

            int responseCode=conn.getResponseCode();

            String response = "";
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                result = true;
            }
        } catch (IOException e) {
            Log.e("Error", "UpdateData");
        }

        return result;
    }


}
