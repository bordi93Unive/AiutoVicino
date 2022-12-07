package it.unive.aiutovicino;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import it.unive.aiutovicino.model.CategoryModel;
import it.unive.aiutovicino.model.UserModel;

public class General {
    public static String SHARED_PREFS = "aiuto_vicino";
    public static UserModel user;
    public static List<CategoryModel> categories;

    public static String connect(String urlAddress, String requestedMethod, Map<String, String> queryParameters) {
        String response = "";
        try {
            URL url = new URL(urlAddress);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
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
            }

        } catch (
                IOException e) {
            Log.e("Error", urlAddress);
        }

        return response;
    }
}
