/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import java.util.List;

public class EarthquakeActivity2 extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    //        ArrayList<Earthquake> earthquakes = new ArrayList<>();
    private static final String mURL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
//        ArrayList<Earthquake> earthquakes = QueryUtils.extractEarthquakes();
        new EarthquakeAsyncTask().execute(mURL);

        // Create a fake list of earthquake locations.
//        earthquakes.add(new Earthquake(7.2f, "San Francisco", 1454371200, "https://www.google.com/"));
//        earthquakes.add(new Earthquake(6.1f, "London", 1437350400, "https://www.google.com/"));
//        earthquakes.add(new Earthquake(3.9f, "Tokyo", 1415577600, "https://www.google.com/"));
//        earthquakes.add(new Earthquake(5.4f, "Mexico City", 1399075200, "https://www.google.com/"));
//        earthquakes.add(new Earthquake(2.8f, "Moscow", 1359590400, "https://www.google.com/"));
//        earthquakes.add(new Earthquake(4.9f, "Rio de Janeiro", 1345334400, "https://www.google.com/"));
//        earthquakes.add(new Earthquake(1.6f, "Paris1", 1319932800, "https://www.google.com/"));
    }

    public class EarthquakeAsyncTask extends AsyncTask<String, Void, List<Earthquake>> {

        @Override
        protected List<Earthquake> doInBackground(String... strings) {
            if (strings.length == 0 || strings[0] == null) {
                return null;
            }
//            return new Link().fetchEarthquakeData(mURL);    // another solution
            return new Link().fetchEarthquakeData(strings[0]);
        }

        @Override
        protected void onPostExecute(List<Earthquake> earthquakes) {
            Log.e("zzzzz", earthquakes + "");
            if (earthquakes == null || earthquakes.isEmpty()) {
                return;
            }

            // Find a reference to the {@link ListView} in the layout
            ListView earthquakeListView = (ListView) findViewById(R.id.list);

            // Create a new {@link ArrayAdapter} of earthquakes
            EarthquakeAdapter adapter = new EarthquakeAdapter(getApplicationContext(), earthquakes);

            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            earthquakeListView.setAdapter(adapter);
        }
    }
}
