package com.zenbarrier.wearfull;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.google.android.wearable.intent.RemoteIntent;


public class MainActivity extends Activity {

    public final static String PLAY_STORE_APP_URI = "market://details?id=com.zenbarrier.wearfull";

    @SuppressLint("StringFormatInvalid")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textMain = (TextView) findViewById(R.id.textView_main);
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = packageInfo.versionName;
            textMain.setText(getString(R.string.hello_wear, version));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            textMain.setText(getString(R.string.hello_wear, "2.x"));
        }

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isConnected = preferences.getBoolean(getString(R.string.key_pref_connected), true);
        if(!isConnected) {
            Intent intentAndroid =
                    new Intent(Intent.ACTION_VIEW)
                            .addCategory(Intent.CATEGORY_BROWSABLE)
                            .setData(Uri.parse(PLAY_STORE_APP_URI));

            RemoteIntent.startRemoteActivity(
                    getApplicationContext(),
                    intentAndroid,
                    mResultReceiver);
        }

    }

    private final ResultReceiver mResultReceiver = new ResultReceiver(new Handler()){

    };
}
