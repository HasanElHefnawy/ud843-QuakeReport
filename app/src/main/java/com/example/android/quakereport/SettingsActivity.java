package com.example.android.quakereport;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("zzzzz", "onCreate2");
        setContentView(R.layout.settings_activity); // fragment
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("zzzzz", "onStart2");
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.e("zzzzz", "onPostCreate2");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("zzzzz", "onResume2");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.e("zzzzz", "onPostResume2");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("zzzzz", "onPause2");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("zzzzz", "onStop2");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e("zzzzz", "onRestart2");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        getSupportLoaderManager().destroyLoader(0);
        Log.e("zzzzz", "onDestroy2");
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
//        Intent intent = new Intent(this,EarthquakeActivity.class);
//        startActivity(intent);
        NavUtils.navigateUpFromSameTask(this);
//        System.exit(0);
        finish();
//        recreate();
        Log.e("zzzzz", "onBackPressed2");
    }

    public static class EarthquakePreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
        // This class is called when R.layout.settings_activity is displayed
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);    // PreferenceScreen

            Preference minMagnitude = findPreference(getString(R.string.settings_min_magnitude_key));
            bindPreferenceSummaryToValue(minMagnitude);

            Preference orderBy = findPreference(getString(R.string.settings_order_by_key));
            bindPreferenceSummaryToValue(orderBy);

            Preference limit = findPreference(getString(R.string.settings_limit_key));
            bindPreferenceSummaryToValue(limit);

            Preference startDate = findPreference(getString(R.string.settings_start_date_key));
            bindPreferenceSummaryToValue(startDate);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString();
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);   // display value of preference right below the preference name
//                    Log.e("xxxxxx", "" + stringValue);
//                    Log.e("xxxxxx", "" + labels[prefIndex]);
                }
            } else {
                preference.setSummary(stringValue); // display value of preference right below the preference name
            }
            return true;
        }

        private void bindPreferenceSummaryToValue(Preference preference) {
            SharedPreferences sharedPreferencesInstance = PreferenceManager.getDefaultSharedPreferences(preference.getContext()); // The value of Preference is stored in sharedPreferencesInstance
            String preferenceString = sharedPreferencesInstance.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);   // calls onPreferenceChange method to display value of Preference before changing it
            preference.setOnPreferenceChangeListener(this); // calls onPreferenceChange method when value of Preference is changed by the user
        }
    }
}