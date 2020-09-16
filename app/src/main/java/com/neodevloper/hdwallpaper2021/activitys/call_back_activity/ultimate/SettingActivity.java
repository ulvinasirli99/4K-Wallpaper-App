package com.neodevloper.hdwallpaper2021.activitys.call_back_activity.ultimate;

import android.Manifest;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.neodevloper.hdwallpaper2021.R;
import com.neodevloper.hdwallpaper2021.activitys.assistant.primary.PreviewActivity;
import com.neodevloper.hdwallpaper2021.web.follow.facebook;
import com.neodevloper.hdwallpaper2021.web.follow.help;
import com.neodevloper.hdwallpaper2021.web.follow.instagram;
import com.neodevloper.hdwallpaper2021.web.follow.twitter;

import java.util.Random;

import es.dmoral.toasty.Toasty;
import okhttp3.Cache;

public class SettingActivity extends AppCompatActivity {

    private static final String TAG = "SettingActivity";
    private SeekBar _seekBar;
    private Context context;
    private TextView textView2;
    private Random random;
    private int chace;
    private Switch wifiSwitch;
    private CheckBox chcsound;
    private WifiManager wifiManager;
    protected ImageView twitterF, facebookF, instagramF, helpW, imgChaceRefresh;
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;
    private boolean success = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        context = this;

        chcsound = findViewById(R.id.chcsound);

        random = new Random();
        textView2 = findViewById(R.id.textView2);
        imgChaceRefresh = findViewById(R.id.imgChaceRefresh);

        setChaceCleaner();

        twitterF = findViewById(R.id.twitterF);
        facebookF = findViewById(R.id.facebookF);
        instagramF = findViewById(R.id.instagramF);
        helpW = findViewById(R.id.imageView3);

        followSocialNetwork();

        _seekBar = findViewById(R.id.bright_seek);
        _seekBar.setMax(200);
        _seekBar.setProgress(getBrightness());

        getPermission();

        wifiSwitch = findViewById(R.id.wifi_switch);
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.d(TAG, "onCheckedChanged: ON");
                    wifiManager.setWifiEnabled(false);
                    wifiSwitch.setText("ON");
                    Toasty.warning(context, R.string.wifi_disable,Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "onCheckedChanged: Off");
                    wifiManager.setWifiEnabled(true);
                    wifiSwitch.setText("OFF");
                    Toasty.success(context, R.string.wifi_enable,Toast.LENGTH_SHORT).show();
                }
            }
        });

        /**
         * Notification Sound with SharedPreferences preferences is Save ......
         */
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        final SharedPreferences.Editor editor = preferences.edit();
        if (preferences.contains("checked") && preferences.getBoolean("checked",false)==true){
            chcsound.setChecked(true);
        }else {
            chcsound.setChecked(false);
        }

        chcsound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (chcsound.isChecked()){
                    editor.putBoolean("checked",true);
                    editor.apply();
                }else {
                    editor.putBoolean("checked",false);
                    editor.apply();
                }

            }
        });

        _seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (fromUser && success) {
                    setBrightness(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if (!success) {
                    Toasty.info(context, R.string.bright_permission,Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void followSocialNetwork() {

        twitterF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, twitter.class);
                startActivity(intent);

            }
        });

        facebookF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, facebook.class);
                startActivity(intent);
            }
        });

        instagramF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, instagram.class);
                startActivity(intent);
            }
        });

        helpW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, help.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wifiStateReceiver, intentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(wifiStateReceiver);
    }

    private BroadcastReceiver wifiStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                    WifiManager.WIFI_STATE_UNKNOWN);
            switch (wifiStateExtra) {
                case WifiManager.WIFI_STATE_ENABLED:
                    wifiSwitch.setChecked(false);
                    wifiSwitch.setText("ON");
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    wifiSwitch.setChecked(true);
                    wifiSwitch.setText("OFF");
                    break;
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {

            case WRITE_EXTERNAL_STORAGE_CODE: {

                if (grantResults.length > 0 &&

                        grantResults[0] ==

                                PackageManager.PERMISSION_GRANTED) {

                } else {

                    Toast.makeText(context, R.string.permisson_granted, Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

    private void setBrightness(int brightness) {

        if (brightness < 0) {
            brightness = 0;
        } else if (brightness > 200) {
            brightness = 200;
        }

        ContentResolver _resolver = getApplicationContext().getContentResolver();
        Settings.System.putInt(_resolver, Settings.System.SCREEN_BRIGHTNESS, brightness);

    }

    private int getBrightness() {

        int brightness = 100;

        try {
            ContentResolver _contentReslover = getApplicationContext().getContentResolver();
            brightness = Settings.System.getInt(_contentReslover, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        return brightness;

    }


    private void getPermission() {

        boolean value;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            value = Settings.System.canWrite(getApplicationContext());
            if (value) {
                success = true;
            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getApplicationContext().getPackageName()));
                startActivityForResult(intent, 1000);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == 1000) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                boolean value = Settings.System.canWrite(getApplicationContext());
                if (value) {
                    success = true;
                } else {
                    Toast.makeText(context, R.string.permisson_not_granted, Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setChaceCleaner() {

        chace = random.nextInt(340);

        int vas = random.nextInt(50);

        double count = (chace / 100) + (vas);

        if (count<1){
            textView2.setText(count+" B");
        } else if (count > 10 && count <20 ) {
            textView2.setText(count + " MB");
        } else if (count >20 && count<100){
            textView2.setText(count + " KB");
        }

        imgChaceRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int misc = 0;
                textView2.setText(misc + " KB");

            }
        });

    }


}