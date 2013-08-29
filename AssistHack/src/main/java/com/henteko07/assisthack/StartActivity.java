package com.henteko07.assisthack;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

public class StartActivity extends Activity {

    public static final String DEFAULT_PACKAGE = "com.google.android.googlequicksearchbox";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean enable = preferences.getBoolean(PrefsFragment.ENABLE_KEY, false);
        if (enable) {
            String packageName = preferences.getString(PrefsFragment.LIST_KEY, DEFAULT_PACKAGE);
            PackageManager pm = getPackageManager();
            Intent intent = pm.getLaunchIntentForPackage(packageName);
            try {
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
        }
        finish();
    }
}
