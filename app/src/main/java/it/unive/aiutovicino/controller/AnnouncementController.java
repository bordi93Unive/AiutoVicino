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
import it.unive.aiutovicino.model.AnnouncementModel;
import it.unive.aiutovicino.model.UserModel;

public class AnnouncementController {
    public static Boolean insert(AnnouncementModel announcement){
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("userId", General.user.getId());
        queryParameters.put("idCategory", announcement.getIdCategory());
        queryParameters.put("title", announcement.getTitle());
        queryParameters.put("description", announcement.getDescription());
        queryParameters.put("place", announcement.getPlace());
        queryParameters.put("date", announcement.getDate());
        queryParameters.put("hours", announcement.getHours());
        queryParameters.put("partecipantsNumber", String.valueOf(announcement.getParticipantsNumber()));
        queryParameters.put("coins", String.valueOf(announcement.getCoins()));

        String response = General.connect("https://europe-west1-ing-sw-c6b56.cloudfunctions.net/announcements-insertAnnouncement", "POST", queryParameters);

        return !response.equals("");
    }

    public static Boolean apply(String id){
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("id", id);
        queryParameters.put("userId", General.user.getId());
        String response = General.connect("https://europe-west1-ing-sw-c6b56.cloudfunctions.net/announcements-applyToAnnouncement", "POST", queryParameters);

        return !response.equals("");
    }

    public static Boolean approveCourse(AnnouncementModel announcement){
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("id", announcement.getId());
        queryParameters.put("userId", General.user.getId());
        queryParameters.put("coins", String.valueOf(announcement.getCoins()));
        String response = General.connect("https://europe-west1-ing-sw-c6b56.cloudfunctions.net/announcements-approveAnnouncement", "POST", queryParameters);

        return !response.equals("");
    }
    //far sistemare chiamata a daniele sennò non funziona
    public static Boolean delete(String announcementId){
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("userId", General.user.getId());
        queryParameters.put("announcementId", announcementId);
        String response = General.connect("https://europe-west1-ing-sw-c6b56.cloudfunctions.net/announcements-deleteAnnouncement", "POST", queryParameters);

        return !response.equals("");
    }

    public static Boolean confirm(String idAnnouncement){
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("userId", General.user.getId());
        queryParameters.put("idAnnouncement", idAnnouncement);
        String response = General.connect("https://europe-west1-ing-sw-c6b56.cloudfunctions.net/applications-applicationConfirmation", "POST", queryParameters);

        return !response.equals("");
    }

    private static List<AnnouncementModel> getAnnouncements(String urlAddress, String requestedMethod, Map<String, String> queryParameters){
        List<AnnouncementModel> announcements = new ArrayList<>();

        String response = General.connect(urlAddress, requestedMethod, queryParameters);
        if(!response.equals("")) {
            try {
                JSONArray jArray = new JSONArray(response);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject jsonData = jArray.getJSONObject(i);

                    AnnouncementModel announcement = new AnnouncementModel();

                    announcement.setId(jsonData.getString("id"));
                    announcement.setUserId(jsonData.getString("userId"));
                    UserModel creatore = UserController.getUserById(announcement.getUserId());
                    announcement.setCreator(creatore.getNickname());
                    announcement.setIdCategory(jsonData.getString("idCategory"));
                    announcement.setTitle(jsonData.getString("title"));
                    announcement.setDescription(jsonData.getString("description"));
                    announcement.setPlace(jsonData.getString("place"));
                    announcement.setDate(jsonData.getString("date"));
                    announcement.setHours(jsonData.getString("hours"));
                    announcement.setParticipantsNumber(jsonData.getInt("partecipantsNumber"));
                    announcement.setCoins(jsonData.getInt("coins"));
                    announcement.setApproved(jsonData.getBoolean("approved"));
                    if (jsonData.has("userApplied")) {
                        JSONArray userApplied = jsonData.getJSONArray("userApplied");
                        List<UserModel> listUserApplied = new ArrayList<UserModel>();
                        for(int y = 0; y < userApplied.length(); y++){
                            UserModel u = UserController.getUserById(userApplied.get(y).toString());
                            if(u != null){
                                listUserApplied.add(u);
                            }
                            //listUserApplied.add(userApplied.getJSONObject(y).toString());
                        }
                        announcement.setUserApplied(listUserApplied);
                    }
                    announcement.setStatus(jsonData.getString("status"));

                    announcements.add(announcement);
                }
            } catch (JSONException e) {
                Log.e("Error", "GetAllAnnouncements Json Decode");
            }
        }

        return announcements;
    }

    public static List<AnnouncementModel> getAllAnnouncements(){
        String urlAddress = "https://europe-west1-ing-sw-c6b56.cloudfunctions.net/announcements-getAllAnnouncements";
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("userId", General.user.getId());
        return getAnnouncements(urlAddress, "POST", queryParameters);
    }

    public static List<AnnouncementModel> getAllMyAnnouncements(){
        String urlAddress = "https://europe-west1-ing-sw-c6b56.cloudfunctions.net/announcements-getAnnouncementsByUserId";
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("userId", General.user.getId());
        return getAnnouncements(urlAddress, "POST", queryParameters);
    }

    public static List<AnnouncementModel> getCoursesToApprove(){
        String urlAddress = "https://europe-west1-ing-sw-c6b56.cloudfunctions.net/announcements-getCoursesToApprove";
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("userId", General.user.getId());
        return getAnnouncements(urlAddress, "POST", queryParameters);
    }

    public static List<AnnouncementModel> getAnnouncementsAppliedByUserId(){
        String urlAddress = "https://europe-west1-ing-sw-c6b56.cloudfunctions.net/announcements-getAnnouncementsAppliedByUserId";
        Map<String, String> queryParameters = new HashMap<>();
        queryParameters.put("userId", General.user.getId());
        return getAnnouncements(urlAddress, "POST", queryParameters);
    }
}
