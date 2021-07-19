package com.example.android.newsnavigation;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        System.out.println("onCreate of SettingsActivity");

    }
    public static class NewsFragmentActivity extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);
            System.out.println("onCreate of NewsFragmentActivity");
            Preference country = findPreference("country");
            bindPreferenceSummaryToValue(country);
        }
        private void bindPreferenceSummaryToValue(Preference preference)
        {
            System.out.println("bindPreferenceSummary");
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences shrd = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = shrd.getString(preference.getKey(),"");
            onPreferenceChange(preference,preferenceString);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            System.out.println("onPreferenceChange");
            String stringValue = newValue.toString();
            if(preference instanceof ListPreference)
            {
                ListPreference listPreference = (ListPreference) preference;
                int preferenceIndex = listPreference.findIndexOfValue(stringValue);
                if(preferenceIndex > 0)
                {
                    CharSequence[] lables  = listPreference.getEntries();
                    preference.setSummary(lables[preferenceIndex]);
                }
            }
            return true;
        }
    }
}