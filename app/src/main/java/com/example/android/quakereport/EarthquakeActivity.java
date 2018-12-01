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


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {

    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query";
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
//        ArrayList<Earthquake> earthquakes = new ArrayList<>();

    // Find a reference to the {@link ListView} in the layout
    ListView earthquakeListView;

    // Create a new {@link ArrayAdapter} of earthquakes
    EarthquakeAdapter adapter;

    TextView empty_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("zzzzz", "onCreate1");
        setContentView(R.layout.earthquake_activity);

        // Find a reference to the {@link ListView} in the layout
        earthquakeListView = (ListView) findViewById(R.id.list);
        empty_view = findViewById(R.id.empty_view);
        earthquakeListView.setEmptyView(empty_view);

        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (!isConnected) {
            findViewById(R.id.progressBar).setVisibility(View.GONE);
            empty_view.setText(R.string.no_internet_connection);
            return;
        }

//        ArrayList<Earthquake> earthquakes = QueryUtils.extractEarthquakes();
        getSupportLoaderManager().initLoader(0, null, this).forceLoad();
        Log.e("zzzzz", "zzzzzzzzzzzzz0");

        // Create a fake list of earthquake locations.
//        earthquakes.add(new Earthquake(7.2f, "San Francisco", 1454371200, "https://www.google.com/"));
//        earthquakes.add(new Earthquake(6.1f, "London", 1437350400, "https://www.google.com/"));
//        earthquakes.add(new Earthquake(3.9f, "Tokyo", 1415577600, "https://www.google.com/"));
//        earthquakes.add(new Earthquake(5.4f, "Mexico City", 1399075200, "https://www.google.com/"));
//        earthquakes.add(new Earthquake(2.8f, "Moscow", 1359590400, "https://www.google.com/"));
//        earthquakes.add(new Earthquake(4.9f, "Rio de Janeiro", 1345334400, "https://www.google.com/"));
//        earthquakes.add(new Earthquake(1.6f, "Paris1", 1319932800, "https://www.google.com/"));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("zzzzz", "onStart1");
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.e("zzzzz", "onPostCreate1");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("zzzzz", "onResume1");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.e("zzzzz", "onPostResume1");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("zzzzz", "onPause1");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("zzzzz", "onStop1");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("zzzzz", "onRestart1");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        getSupportLoaderManager().destroyLoader(0);
        Log.e("zzzzz", "onDestroy1");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.e("zzzzz", "onBackPressed1");
    }

    @NonNull
    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, @Nullable Bundle args) {
        Log.e("zzzzz", "onCreateLoader");
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPrefs.getString(getString(R.string.settings_min_magnitude_key), getString(R.string.settings_min_magnitude_default));
        String orderBy = sharedPrefs.getString(getString(R.string.settings_order_by_key), getString(R.string.settings_order_by_default));
        String limit = sharedPrefs.getString(getString(R.string.settings_limit_key), getString(R.string.settings_limit_default));
        String startDate = sharedPrefs.getString(getString(R.string.settings_start_date_key), getString(R.string.settings_start_date_default));
        Uri baseUri = Uri.parse(USGS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", limit);
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", orderBy);
        uriBuilder.appendQueryParameter("starttime", startDate);
        Log.e("zzzzz", "" + uriBuilder.toString());
        return new EarthquakeLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Earthquake>> loader, List<Earthquake> data) {
        Log.e("zzzzz", data + "");
        findViewById(R.id.progressBar).setVisibility(View.GONE);
        if (data == null || data.isEmpty()) {
            TextView txt = findViewById(R.id.empty_view);
            txt.setText(R.string.no_earthquakes_found);
            return;
        }

        // Create a new {@link ArrayAdapter} of earthquakes
        adapter = new EarthquakeAdapter(getApplicationContext(), data);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);
        Log.e("zzzzz", "onLoadFinished");
    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {
//        earthquakeListView.setAdapter(new EarthquakeAdapter(getApplicationContext(), new ArrayList<Earthquake>()));
        Log.e("zzzzz", "onLoaderReset");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // display an options menu when the activity is created
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // This is called whenever an item in options menu is selected
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void restart(View view) {
        this.recreate();
    }

    public void fininsh(View view) {
        this.finish();
    }

    public void exit(View view) {
        System.exit(0);
    }
}
