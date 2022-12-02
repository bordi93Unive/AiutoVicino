package it.unive.aiutovicino.task;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpEntity;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpResponse;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.HttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.client.methods.HttpGet;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.DefaultHttpClient;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.util.EntityUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class AnnuncioTask extends AsyncTask<String, Void, Boolean> {
    String result = "";
    @Override
    protected void onPreExecute() {
        Log.d("Pre", "123");
    }

    protected void onPostExecute() {
        Log.d("Post", "321");
    }

    @Override
    protected Boolean doInBackground(String... args) {
        try {
            URL url;
            HttpURLConnection urlConnection = null;
            url = new URL("https://europe-west1-ing-sw-c6b56.cloudfunctions.net/getAllAnnouncement");
//open a URL coonnection

urlConnection = (HttpURLConnection) url.openConnection();

InputStream in = urlConnection.getInputStream();

InputStreamReader isw = new InputStreamReader(in);

int data = isw.read();

while (data != -1) {
result += (char) data;
data = isw.read();

}
Log.d("Ugo", result);
return true;


        } catch (IOException e) {
            e.printStackTrace();
        }catch(RuntimeException e){
                Log.d("ugo",e.getMessage());
        }
        return false;
    }
}
