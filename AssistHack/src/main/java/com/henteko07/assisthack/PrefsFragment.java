package com.henteko07.assisthack;

import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;

import java.util.ArrayList;
import java.util.List;

public class PrefsFragment extends PreferenceFragment {

    public static final String ENABLE_KEY = "checkbox_preference";
    public static final String LIST_KEY = "list_preference";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref);

        ArrayList<String> appLabelList = new ArrayList<String>();
        ArrayList<String> appPackageList = new ArrayList<String>();

        PackageManager packageManager = this.getActivity().getPackageManager();
        List<ApplicationInfo> applicationInfo = packageManager.getInstalledApplications(PackageManager.GET_META_DATA);
        for (ApplicationInfo info : applicationInfo) {
            if ((info.flags & ApplicationInfo.FLAG_SYSTEM) == ApplicationInfo.FLAG_SYSTEM) continue;
            if (info.packageName.equals(this.getActivity().getPackageName())) continue;

            appLabelList.add((String) packageManager.getApplicationLabel(info));
            appPackageList.add(info.packageName);
        }

        String[] entries = appLabelList.toArray(new String[0]);
        String[] entrieValues = appPackageList.toArray(new String[0]);
        ListPreference lp = (ListPreference)findPreference(LIST_KEY);
        CheckBoxPreference cp = (CheckBoxPreference)findPreference(ENABLE_KEY);

        lp.setEntries(entries);
        lp.setEntryValues(entrieValues);
        lp.setSummary(lp.getEntry());
        lp.setEnabled(cp.isChecked());
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        sharedPreferences.registerOnSharedPreferenceChangeListener(onPreferenceChangeListenter);
    }

    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(onPreferenceChangeListenter);
    }

    private SharedPreferences.OnSharedPreferenceChangeListener onPreferenceChangeListenter = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            CheckBoxPreference cp = (CheckBoxPreference)findPreference(ENABLE_KEY);
            ListPreference lp = (ListPreference)findPreference(LIST_KEY);
            if (key.equals(ENABLE_KEY)) {
                lp.setEnabled(cp.isChecked());
            } else if (key.equals(LIST_KEY)) {
                lp.setSummary(lp.getEntry());
            }
        }
    };

}
