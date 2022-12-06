package it.unive.aiutovicino.controller;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpGet;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.util.EntityUtils;

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
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import it.unive.aiutovicino.General;
import it.unive.aiutovicino.model.AnnuncioModel;

public class AnnuncioController {
    public static Boolean insertAnnuncio(AnnuncioModel annuncio){
        boolean result = false;
        try {
            URL url = new URL("https://europe-west1-ing-sw-c6b56.cloudfunctions.net/insertAnnouncement");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("description", annuncio.description)
                    .appendQueryParameter("idCategory", annuncio.id_category)
                    .appendQueryParameter("idUser", General.user.id)
                    .appendQueryParameter("place", annuncio.place)
                    .appendQueryParameter("partecipantsNumber", String.valueOf(annuncio.partecipantsNumber));
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

    public static AnnuncioModel getAnnouncment(int id){
        switch(id){
            case 1:
                return new AnnuncioModel("1", "Sfalcio prato", "1","01/01/2023","15:15","Bassano",3,true);
            case 2:
                return new AnnuncioModel("2", "Baby Sitting", "2","23/12/2022","10:15","Trieste",2,false);
            case 3:
                return new AnnuncioModel("3", "Dog sitting", "3","02/08/1993","19:20","Rosà",1,true);
            default:
                return null;
        }
    }

    public static List<AnnuncioModel> getAllAnnouncements(){
        List<AnnuncioModel> annunci = new ArrayList<>();

        try {
            URL url = new URL("https://europe-west1-ing-sw-c6b56.cloudfunctions.net/announcements-getAllAnnouncements");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            //conn.setDoInput(true);
            conn.setDoOutput(true);

            /*Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("token", General.user.token);
            String query = builder.build().getEncodedQuery();

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(query);
            writer.flush();
            writer.close();
            os.close();*/

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
                    AnnuncioModel annuncio = new AnnuncioModel();
                    JSONObject json_data = jArray.getJSONObject(i);
                    annuncio.id = json_data.getString("id");
                    annuncio.title = json_data.getString("description");
                    annuncio.description = json_data.getString("description");
                    annuncio.id_category = json_data.getString("idCategory");
                    annuncio.place = json_data.getString("place");
                    annuncio.partecipantsNumber = json_data.getInt("partecipantsNumber");

                    annunci.add(annuncio);
                }
            }
        } catch (IOException e) {
            Log.e("Error", "Login");
        } catch (JSONException e) {
            Log.e("Error", "Login Json Decode");
        }

        return annunci;
    }

    public static List<AnnuncioModel> getAllMyAnnouncements(){
        List<AnnuncioModel> annunci = new ArrayList<>();


        return annunci;
    }
}
