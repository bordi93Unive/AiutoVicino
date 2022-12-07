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
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import it.unive.aiutovicino.General;
import it.unive.aiutovicino.model.AnnouncementModel;
import it.unive.aiutovicino.model.UserModel;

public class AnnouncementController {
    public static Boolean insertAnnuncio(AnnouncementModel announcement){
        boolean result = false;
        try {
            URL url = new URL("https://europe-west1-ing-sw-c6b56.cloudfunctions.net/announcements-insertAnnouncement");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("date", announcement.getDate())
                    .appendQueryParameter("hours", announcement.getHours())
                    .appendQueryParameter("description", announcement.getDescription())
                    .appendQueryParameter("idCategory", announcement.getIdCategory())
                    .appendQueryParameter("idUser", General.user.getId())
                    .appendQueryParameter("place", announcement.getPlace())
                    .appendQueryParameter("coins", String.valueOf(announcement.getCoins()))
                    .appendQueryParameter("partecipantsNumber", String.valueOf(announcement.getParticipantsNumber()));
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

    public static Boolean apply(String id){
        boolean result = false;
        try {
            URL url = new URL("https://europe-west1-ing-sw-c6b56.cloudfunctions.net/announcements-applyToAnnouncement");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("id", id)
                    .appendQueryParameter("userId", General.user.getId());;
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

    public static List<AnnouncementModel> getAllAnnouncements(){
        List<AnnouncementModel> announcements = new ArrayList<>();

        try {
            URL url = new URL("https://europe-west1-ing-sw-c6b56.cloudfunctions.net/announcements-getAllAnnouncements");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("idUser", General.user.getId());
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
            if(!response.equals("")) {
                JSONArray jArray = new JSONArray(response);
                for(int i = 0; i < jArray.length(); i++) {
                    JSONObject jsonData = jArray.getJSONObject(i);

                    AnnouncementModel announcement = new AnnouncementModel();

                    announcement.setId(jsonData.getString("id"));
                    announcement.setIdUser(jsonData.getString("idUser"));
                    announcement.setIdCategory(jsonData.getString("idCategory"));
                    announcement.setTitle(jsonData.getString("description"));
                    announcement.setDescription(jsonData.getString("description"));
                    announcement.setPlace(jsonData.getString("place"));
                    announcement.setDate(jsonData.getString("date"));
                    announcement.setHours(jsonData.getString("hours"));
                    announcement.setParticipantsNumber(jsonData.getInt("partecipantsNumber"));
                    announcement.setCoins(jsonData.getInt("coins"));
                    announcement.setApproved(jsonData.getBoolean("approved"));
                    if(jsonData.has("userApplyed")){
                        announcement.setUserApplyed(new ArrayList<>());
                        String[] userApplyed = new String[1];
                        userApplyed[0] = "zPVvd9Mzhk1tXAurtjnL";

                        for(int index = 0; index < userApplyed.length; index++){
                            UserModel user = UserController.getUserById(userApplyed[index]);
                            if(user != null){
                                announcement.getUserApplyed().add(user);
                            }
                        }
                        /*JSONArray userApplyed = jsonData.getJSONArray("userApplyed");
                        List<String> listUserApplyed = new ArrayList<String>();
                        for(int y = 0; y < userApplyed.length(); y++){
                            listUserApplyed.add(userApplyed.getJSONObject(y));
                        }
                        announcement.setUserApplyed((String[]) listUserApplyed.toArray());*/
                    }
                    announcement.setStatus(jsonData.getString("status"));

                    announcements.add(announcement);
                }
            }
        } catch (IOException e) {
            Log.e("Error", "GetAllAnnouncements");
        } catch (JSONException e) {
            Log.e("Error", "GetAllAnnouncements Json Decode");
        }

        return announcements;
    }

    public static List<AnnouncementModel> getAllMyAnnouncements(){
        List<AnnouncementModel> announcements = new ArrayList<>();

        try {
            URL url = new URL("https://europe-west1-ing-sw-c6b56.cloudfunctions.net/announcements-getAnnouncementsByUserId");
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("idUser", General.user.getId());
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
            if(!response.equals("")) {
                JSONArray jArray = new JSONArray(response);
                for(int i = 0; i < jArray.length(); i++) {
                    JSONObject jsonData = jArray.getJSONObject(i);

                    AnnouncementModel announcement = new AnnouncementModel();

                    announcement.setId(jsonData.getString("id"));
                    announcement.setIdUser(jsonData.getString("idUser"));
                    announcement.setIdCategory(jsonData.getString("idCategory"));
                    announcement.setTitle(jsonData.getString("description"));
                    announcement.setDescription(jsonData.getString("description"));
                    announcement.setPlace(jsonData.getString("place"));
                    announcement.setDate(jsonData.getString("date"));
                    announcement.setHours(jsonData.getString("hours"));
                    announcement.setParticipantsNumber(jsonData.getInt("partecipantsNumber"));
                    announcement.setCoins(jsonData.getInt("coins"));
                    announcement.setApproved(jsonData.getBoolean("approved"));
                    if(jsonData.has("userApplyed")){
                        String[] userApplyed = new String[1];
                        userApplyed[0] = "zPVvd9Mzhk1tXAurtjnL";

                        for(int index = 0; index < userApplyed.length; index++){
                            UserModel user = UserController.getUserById(userApplyed[index]);
                        }

                        /*JSONArray userApplyed = jsonData.getJSONArray("userApplyed");
                        List<String> listUserApplyed = new ArrayList<String>();
                        for(int y = 0; y < userApplyed.length(); y++){
                            listUserApplyed.add(userApplyed.getJSONObject(y).toString());
                        }
                        announcement.setUserApplyed((String[]) listUserApplyed.toArray());*/
                    }
                    announcement.setStatus(jsonData.getString("status"));

                    announcements.add(announcement);
                }
            }
        } catch (IOException e) {
            Log.e("Error", "GetAllAnnouncements");
        } catch (JSONException e) {
            Log.e("Error", "GetAllAnnouncements Json Decode");
        }

        return announcements;
    }
}
