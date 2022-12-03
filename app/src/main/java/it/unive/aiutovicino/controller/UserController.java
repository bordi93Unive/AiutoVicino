package it.unive.aiutovicino.controller;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.NameValuePair;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.entity.UrlEncodedFormEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpPost;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.message.BasicNameValuePair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

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
                JSONObject jObject = new JSONObject(response);
                user = new UserModel((String)jObject.get("userId"), (String)jObject.get("token"), email, "Ugo", "Ughino");
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
