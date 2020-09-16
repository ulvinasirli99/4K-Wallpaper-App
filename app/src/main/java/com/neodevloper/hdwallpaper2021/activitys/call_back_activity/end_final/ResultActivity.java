package com.neodevloper.hdwallpaper2021.activitys.call_back_activity.end_final;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.neodevloper.hdwallpaper2021.activitys.assistant.reason.FeedBackActivity;
import com.neodevloper.hdwallpaper2021.R;
import com.neodevloper.hdwallpaper2021.activitys.call_back_activity.ultimate.InfoActivity;
import com.neodevloper.hdwallpaper2021.activitys.call_back_activity.ultimate.SearchActivity;
import com.neodevloper.hdwallpaper2021.activitys.call_back_activity.ultimate.SettingActivity;
import com.neodevloper.hdwallpaper2021.fragments.basic_fragment.CategoryFragment;
import com.neodevloper.hdwallpaper2021.fragments.basic_fragment.HomeFragment;
import com.neodevloper.hdwallpaper2021.fragments.basic_fragment.LatestFragment;
import com.onesignal.OneSignal;

public class ResultActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Context context;
    private View view;
    private Window window;
    private static final String MyPREFERENCES = "nightModePrefes";
    private static final String KEY_ISNIGHTMODE = "isNightMode";
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;
    private SharedPreferences sharedPreferences;
    private Switch aSwitch, dark_result;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        context = this;

        OneSignal.startInit(context)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();

        window = this.getWindow();
        toolbar = findViewById(R.id.toolbars);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.Drawer);
        navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,
                drawerLayout, toolbar, 0, 0);
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        view = this.getWindow().getDecorView();

        aSwitch = findViewById(R.id.dark_back_btn);
//        dark_result = findViewById(R.id.dark_result);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_dress,
                        new HomeFragment()).commit();
        navigationView.setCheckedItem(R.id.homes);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED) {

                String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.VIBRATE};

                requestPermissions(permission, WRITE_EXTERNAL_STORAGE_CODE);

            }
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        switch (id) {

            case R.id.homes:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_dress,
                                new HomeFragment()).commit();
                toolbar.setTitle("Home");
                break;

            case R.id.category:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_dress,
                                new CategoryFragment()).commit();
                toolbar.setTitle("Category");
                break;

            case R.id.latest:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_dress,
                                new LatestFragment()).commit();
                toolbar.setTitle("Latest");
                break;

            case R.id.send:
                Intent feed = new Intent(this, FeedBackActivity.class);
                startActivity(feed);
                break;

            case R.id.sharing:
                shareAppResult();
                break;

            case R.id.about:
                Intent about = new Intent(this, SettingActivity.class);
                startActivity(about);
                break;

            case R.id.search_wall_item:
                Intent intentSe = new Intent(this, SearchActivity.class);
                startActivity(intentSe);
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();

        }
    }

    private void shareAppResult() {

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareBody = "https://play.google.com/store/apps/details?id=com.neodevloper.hdwallpaper2021";
        String shareSub = "Your Subject Here";
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(shareIntent, "4K Wallpaper"));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case R.id.share_images:
                shareApp();
                break;

            case R.id.restart:
                restartApp();
                break;

            case R.id.about_apps:
                Intent aboutHome = new Intent(this, InfoActivity.class);
                startActivity(aboutHome);
                break;

            case R.id.deyis:
                darkModeSelect();
                break;

        }

        return super.onOptionsItemSelected(item);

    }

    private void shareApp() {

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareBody = "https://play.google.com/store/apps/details?id=com.neodevloper.hdwallpaper2021";
        String shareSub = "Your Subject Here";
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(shareIntent, "4K Wallpaper"));

    }

    private void restartApp() {

        PendingIntent p = PendingIntent.getActivity(context, 1000, getIntent(), PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, p);

        System.exit(0);

    }

    public void darkModeSelect() {


        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                ResultActivity.this, R.style.BottomSheetDialogTheme
        );

        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(
                        R.layout.dark_backround,
                        (RelativeLayout) findViewById(R.id.bootomSheetContanier)
                );

        bottomSheetView.findViewById(R.id.dark_back_btn).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                view.setBackgroundResource(R.color.tre);

                if (Build.VERSION.SDK_INT >= 21) {
                    window = getWindow();
                    window.setStatusBarColor(R.color.black);
                    window.setNavigationBarColor(R.color.black);

                }
                toolbar.setBackgroundResource(R.color.tre);

            }
        });

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();


    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case WRITE_EXTERNAL_STORAGE_CODE: {
                if (grantResults.length > 0 &&
                        grantResults[0] ==
                                PackageManager.PERMISSION_GRANTED) {

                } else {
                    Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
