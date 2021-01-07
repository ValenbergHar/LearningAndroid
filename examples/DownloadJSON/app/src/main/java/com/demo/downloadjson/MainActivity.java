package com.demo.downloadjson;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        URL url = null;
        HttpURLConnection urlConnection = null;
        StringBuilder result = new StringBuilder();
        try {
            url = new URL("http://api.openweathermap.org/data/2.5/weather?q=hrodna&APPID=3b1bf02c1283bdf30dfbfc685c6092be");
            urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    result.append(line);
                    line = reader.readLine();
                }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("MyResult", String.valueOf(result));


//            HttpURLConnection urlConnection = null;
//            StringBuilder result = new StringBuilder();


//        DownloadJSONTask task = new DownloadJSONTask();
//        task.execute("http://api.openweathermap.org/data/2.5/weather?q=hrodna&APPID=3b1bf02c1283bdf30dfbfc685c6092be");


    }

//    private static class DownloadJSONTask extends AsyncTask<String, Void, String> {
//        @Override
//        protected String doInBackground(String... strings) {
//            URL url = null;
//            HttpURLConnection urlConnection = null;
//            StringBuilder result = new StringBuilder();
//            try {
//                url = new URL(strings[0]);
//                urlConnection = (HttpURLConnection) url.openConnection();
//                InputStream inputStream = urlConnection.getInputStream();
//                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
//                BufferedReader reader = new BufferedReader(inputStreamReader);
//                String line = reader.readLine();
//                while (line != null) {
//                    result.append(line);
//                    line = reader.readLine();
//                }
//                return result.toString();
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            try {
//                JSONObject jsonObject = new JSONObject(s);
//                JSONArray jsonArray = jsonObject.getJSONArray("weather");
//                JSONObject weather = jsonArray.getJSONObject(0);
//                String main = weather.getString("main");
//                String description = weather.getString("description");
//                Log.i("MyResult", main + " " + description);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//}
}
