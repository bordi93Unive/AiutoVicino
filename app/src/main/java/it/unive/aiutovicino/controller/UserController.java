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
                String id= jObject.getString("id");
                String token = jObject.getString("token");
                String name = jObject.getString("name");
                String surname = jObject.getString("surname");
                user = new UserModel(id,token,email,name,surname);
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
                    .appendQueryParameter("email", user.email)
                    .appendQueryParameter("password", user.password)
                    .appendQueryParameter("name", user.name)
                    .appendQueryParameter("surname", user.surname)
                    .appendQueryParameter("nickname", user.nickname)
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
                    .appendQueryParameter("email", user.email)
                    .appendQueryParameter("password", user.password)
                    .appendQueryParameter("name", user.name)
                    .appendQueryParameter("surname", user.surname)
                    .appendQueryParameter("nickname", user.nickname)
                    .appendQueryParameter("description", user.descrizione);
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

    public static UserModel getUserByEmail(String email){
        /*attenzione che questo if viene eseguito appena parte l'app. Quindi prende la stringa hardcoded in fragment_login*/
        if(email.equals("ugo@ugami.it")){
            return creaUgo();
        }
        return null;
    }

    public static UserModel getUserByID(int id){

            return creaUgo();
    }

    public static UserModel creaUgo(){
        return new UserModel("1","Ugo","Ughino", "ugo@ugami.it", "ughino", "Ughino",true);
    }


}
