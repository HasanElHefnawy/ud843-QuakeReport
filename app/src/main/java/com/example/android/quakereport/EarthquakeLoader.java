package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {
    List<Earthquake> earthquakes = new ArrayList<>();
    String mURL;

    public EarthquakeLoader(@NonNull Context context, String url) {
        super(context);
        mURL = url;
    }

    @Nullable
    @Override
    public List<Earthquake> loadInBackground() {
        Log.e("zzzzz","loadInBackground");
        return fetchEarthquakeData();
    }

    public List<Earthquake> fetchEarthquakeData() {
        try {
//            Thread.sleep(1000);
            InputStream is = new URL("https://api.myjson.com/bins/kp9wz").openStream();
            URLConnection uc = new URL(mURL).openConnection(); // URL to Parse
            HttpURLConnection huc = (HttpURLConnection) new URL(mURL).openConnection();
            int x = huc.getResponseCode();
            if (x == 200) {
                BufferedReader rd1 = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                BufferedReader rd2 = new BufferedReader(new InputStreamReader(uc.getInputStream(), Charset.forName("UTF-8")));
                BufferedReader rd3 = new BufferedReader(new InputStreamReader(huc.getInputStream(), Charset.forName("UTF-8")));
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = rd3.readLine()) != null) {
                    sb.append(line);
                }
                String jsonText = sb.toString();
                JSONObject json = new JSONObject(jsonText);
                JSONArray features = json.getJSONArray("features");
                for (int index = 0; index < features.length(); index++) {
                    JSONObject arrayindex = features.getJSONObject(index);
                    JSONObject properties = arrayindex.getJSONObject("properties");
                    float mag = (float) properties.getDouble("mag");
                    String place = properties.getString("place");
                    long time = properties.getLong("time");
                    String url = properties.getString("url");
                    earthquakes.add(new Earthquake(mag, place, time, url));
                    Log.e("zzzzzz", mag + "\n" + place + "\n" + new SimpleDateFormat("MMM d, yyyy h:mm a").format(new Date(time)));
                }
                is.close();
            } else Log.e("zzzzzzzzzzzz Exception", "There is an error, the error code is: " + x);
        } catch (Exception e) {
            Log.e("zzzzzzzzzzzz Exception", "khara", e);
//                e.printStackTrace();
        }
        return earthquakes;
    }
}
