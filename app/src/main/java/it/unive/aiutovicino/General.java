package it.unive.aiutovicino;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.SearchView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import it.unive.aiutovicino.model.CategoryModel;
import it.unive.aiutovicino.model.UserModel;

public class General {
    public static String SHARED_PREFS = "aiuto_vicino";
    public static UserModel user;
    public static List<CategoryModel> categories;
    public static MenuItem searchView;

    public static void setSearchViewVisible(){
        if(searchView != null){
            searchView.setVisible(true);
        }
    }
    public static void setSearchViewInvisible(){
        if(searchView != null){
            searchView.setVisible(false);
        }
    }

    public static void setNotVisibleTabs(Activity activity){
        TabLayout tabs = activity.findViewById(R.id.tabs);
        tabs.setVisibility(View.GONE);
    }

    public static void setVisibleTabs(Activity activity, ViewPager viewPager){
        TabLayout tabs = activity.findViewById(R.id.tabs);
        tabs.setVisibility(View.VISIBLE);
        tabs.setupWithViewPager(viewPager);
    }

    public static String connect(String urlAddress, String requestedMethod, Map<String, String> queryParameters) {
        String response = "";
        try {
            URL url = new URL(urlAddress);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            if(General.user != null) {
                conn.addRequestProperty("Token", General.user.getToken());
            }
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod(requestedMethod);
            conn.setDoOutput(true);
            if(queryParameters != null && queryParameters.size() > 0) {
                conn.setDoInput(true);
                Uri.Builder builder = new Uri.Builder();
                for(Map.Entry<String, String> entry : queryParameters.entrySet())  {
                    builder.appendQueryParameter(entry.getKey(), entry.getValue());
                }

                String query = builder.build().getEncodedQuery();
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
            }

            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response +=line;
                }
                if(General.user != null){
                    General.user.setTokenExpiration(System.currentTimeMillis() + 86400000);
                }
            }

        } catch (
                IOException e) {
            Log.e("Error", urlAddress);
        }

        return response;
    }

    public static UserModel setUserBySharedPreferences(UserModel user, Activity activity, int mode){
        if(user != null) {
            SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREFS, mode);
            if (sharedPreferences != null) {
                SharedPreferences.Editor editor = sharedPreferences.edit();

                Gson gson = new Gson();
                String json = gson.toJson(user);
                editor.putString("user", json);
                editor.apply();
            }
        }
        return user;
    }

    public static UserModel getUserBySharedPreferences(Activity activity, int mode){
        if(user == null) {
            SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREFS, mode);
            if (sharedPreferences != null) {
                Gson gson = new Gson();
                String json = sharedPreferences.getString("user", "");

                if (json != null && !json.equals("")) {
                    user = gson.fromJson(json, UserModel.class);
                }
            }
        }
        return user;
    }

    public static boolean checkTokenExpiration(){
        boolean expired = true;
        if(General.user != null && General.user.getTokenExpiration() > System.currentTimeMillis()){
            expired = false;
        }
        return expired;
    }

    public static void resetSharedPreferences(Activity activity, int mode){
        SharedPreferences sharedPreferences = activity.getSharedPreferences(SHARED_PREFS, mode);
        if (sharedPreferences != null) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear().commit();
            user = null;
        }
    }

    public static boolean isValid(final String password) {
        /*Password must contain at least one digit [0-9].
        Password must contain at least one lowercase Latin character [a-z].
        Password must contain at least one uppercase Latin character [A-Z].
        Password must contain at least one special character like ! @ # & ( ).
        Password must contain a length of at least 8 characters and a maximum of 20 characters.*/
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
        final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}
