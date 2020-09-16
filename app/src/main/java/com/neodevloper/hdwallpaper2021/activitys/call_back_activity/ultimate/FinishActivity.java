package com.neodevloper.hdwallpaper2021.activitys.call_back_activity.ultimate;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;


import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.neodevloper.hdwallpaper2021.activitys.assistant.reason.AboutActivity;
import com.neodevloper.hdwallpaper2021.activitys.assistant.reason.FeedBackActivity;
import com.neodevloper.hdwallpaper2021.fragments.basic_fragment.FinishCategoryList;
import com.neodevloper.hdwallpaper2021.R;
import com.neodevloper.hdwallpaper2021.adapter.page_adapter.viewadapter.screen_item.ui_page.ViewPagerAdapter;
import com.neodevloper.hdwallpaper2021.fragments.basic_fragment.HomeFragment;
import com.neodevloper.hdwallpaper2021.fragments.basic_fragment.SearchFragment;
import com.shrikanthravi.library.NightModeButton;

import es.dmoral.toasty.Toasty;
import me.kungfucat.viewpagertransformers.CubeOutTransformer;
import me.kungfucat.viewpagertransformers.RaiseFromCenterTransformer;
import me.kungfucat.viewpagertransformers.TranslationYTransformer;

public class FinishActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "FinishActivity";
    private DrawerLayout drawerLayout;
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;
    private NavigationView navigationView;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private Context context;
    private NightModeButton darkModeButton;
    private ViewPagerAdapter adapter;
    private View view;
    int uiOptions;
    private Window window;
    private WifiManager wifiManager;
    private SharedPreferences sharedPreferences;
    private static final String MY_PREFERENCES = "nightModePrefs";
    private static final String KEY_ISNIGHTMODE = "isNightMode";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        context = this;

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        window = this.getWindow();

        view = this.getWindow().getDecorView();

        uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        view.setSystemUiVisibility(uiOptions);

        drawerLayout = findViewById(R.id.drawerLayout_ff);
        navigationView = findViewById(R.id.navigationView_ff);
        navigationView.setNavigationItemSelectedListener(this);

        darkModeButton = findViewById(R.id.nightModeButton);

        viewPager = findViewById(R.id.viewpager_f);

        toolbar = findViewById(R.id.toolbar_f);

        setSupportActionBar(toolbar);

        tabLayout = findViewById(R.id.tab_layout_f);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new FinishCategoryList(), getString(R.string.cate));

        adapter.addFragment(new HomeFragment(), getString(R.string.home));

        adapter.addFragment(new SearchFragment(), getString(R.string.searchh));

        sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);

        viewPager.setAdapter(adapter);

        viewPager.setPageTransformer(true, new TranslationYTransformer());

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.category_white);

        tabLayout.getTabAt(1).setIcon(R.drawable.home_white);

        tabLayout.getTabAt(2).setIcon(R.drawable.search);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, 0, 0);

        actionBarDrawerToggle.syncState();

        nightModeButtonSetChecked();

        checkNightModeActivated();

        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        /**
         * Burda sef ola biler cunki Kitikat yerine Marshall omalidir
         * todo onu nezere alaraq sef olsa burda ola bilerr...
         */

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED) {

                String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.VIBRATE, Manifest.permission.WRITE_SETTINGS};

                requestPermissions(permission, WRITE_EXTERNAL_STORAGE_CODE);

            }
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {

                    toolbar.setTitle(R.string.category_toolbar);

                } else if (tab.getPosition() == 1) {

                    toolbar.setTitle(R.string.home_tool);

                } else if (tab.getPosition() == 2) {

                    toolbar.setTitle(R.string.search_tool);

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();
        viewPager.setCurrentItem(1);
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

            drawerLayout.closeDrawer(GravityCompat.START);

        } else {

            super.onBackPressed();

        }
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
                //Todo yarimciq qalibbbbb
                if (restorePrefData()) {
                    Intent intent = new Intent(context, InfoActivity.class);
                    startActivity(intent);
                } else {
                    privacyAccept();
                }
                break;

            case R.id.deyis:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    darkModeSelect();

                } else {

                    _darkModeDiminutiveVersion();

                }

                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id) {

            case R.id.send:
                Intent feed = new Intent(this, FeedBackActivity.class);
                startActivity(feed);
                break;

            case R.id.sharing:
                shareAppResult();
                break;

            case R.id.about:
                Intent about = new Intent(this, AboutActivity.class);
                startActivity(about);
                break;

            case R.id.purchase:
                //TODO PURCHASE DIALOG////////.
                purchaseDialog();
                /*TODO burda google amount biilling in yeni versiyada gelmesyini
               todo gosteren bir dialog olacaq ve google play consle oturduqdan sonra versiya
                deysinekenlyi elan ederek bunu ozellesdirirk....
                */
                break;

            case R.id.setting:
                Intent setting = new Intent(context, SettingActivity.class);
                startActivity(setting);
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);

        return true;
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

    private void shareAppResult() {

        Intent shareIntent = new Intent(Intent.ACTION_SEND);

        shareIntent.setType("text/plain");

        String shareBody = "https://play.google.com/store/apps/details?id=com.neodevloper.hdwallpaper2021";

        String shareSub = "Your Subject Here";

        shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);

        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);

        startActivity(Intent.createChooser(shareIntent, "4K Wallpaper"));


    }

    private void darkModeSelect() {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(

                FinishActivity.this, R.style.BottomSheetDialogTheme

        );

        final View bottomSheetView = LayoutInflater.from(getApplicationContext())

                .inflate(

                        R.layout.dark_backround,

                        (RelativeLayout) findViewById(R.id.bootomSheetContanier)
                );


        bottomSheetView.findViewById(R.id.dark_back_btn).setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"ResourceAsColor", "NewApi"})
            @Override
            public void onClick(View v) {

                if (bottomSheetView.findViewById(R.id.dark_back_btn).getTooltipText().equals("salam")) {

                    bottomSheetView.findViewById(R.id.dark_back_btn).setTooltipText("sagol");

                    view.setBackgroundResource(R.color.tre);

                    tabLayout.setBackgroundResource(R.color.tre);

                    tabLayout.setTabTextColors(ColorStateList.valueOf(getColor(R.color.white)));

                    window.setStatusBarColor(R.color.tre);

                    window.setNavigationBarColor(R.color.tre);

                    toolbar.setBackgroundResource(R.color.tre);


                } else if (bottomSheetView.findViewById(R.id.dark_back_btn).getTooltipText().equals("sagol")) {

                    bottomSheetView.findViewById(R.id.dark_back_btn).setTooltipText("salam");

                    view.setBackgroundResource(R.color.red);

                    window = getWindow();

                    tabLayout.setBackgroundResource(R.color.red);

                    tabLayout.setTabTextColors(ColorStateList.valueOf(getColor(R.color.white)));

                    window.setStatusBarColor(R.color.white);

                    window.setNavigationBarColor(R.color.white);

                    toolbar.setBackgroundResource(R.color.red);


                }


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

                    Toast.makeText(context, R.string.permisson_granted, Toast.LENGTH_SHORT).show();

                }
            }
        }
    }

    private void purchaseDialog() {

        Button btnClose;

        final Dialog _dialog = new Dialog(context);

        _dialog.setContentView(R.layout.purchase_dialog);

        _dialog.setCancelable(false);

        _dialog.setCanceledOnTouchOutside(false);

        _dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,

                WindowManager.LayoutParams.WRAP_CONTENT);

        _dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        _dialog.getWindow().getAttributes().windowAnimations =

                android.R.style.Animation_Dialog;

        _dialog.show();

        btnClose = _dialog.findViewById(R.id.btnClose);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _dialog.dismiss();
            }
        });

    }

    private void _darkModeDiminutiveVersion() {

        Button btnClose;

        final Dialog _dialog = new Dialog(context);

        _dialog.setContentView(R.layout.dark_mode_un_selected);

        _dialog.setCancelable(false);

        _dialog.setCanceledOnTouchOutside(false);

        _dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,

                WindowManager.LayoutParams.WRAP_CONTENT);

        _dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        _dialog.getWindow().getAttributes().windowAnimations =

                android.R.style.Animation_Dialog;

        _dialog.show();

        btnClose = _dialog.findViewById(R.id.btnCloseDark);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _dialog.dismiss();
            }
        });


    }

    private void connectControl() {

        final BottomSheetDialog _bottom_dialog = new BottomSheetDialog(context,

                R.style.BottomSheetDialogTheme);

        final View bottom_sheet = LayoutInflater.from(

                getApplicationContext())

                .inflate(R.layout.wifi_control, (RelativeLayout) findViewById(R.id.wifiControl));

        bottom_sheet.findViewById(R.id.wi_fi_count).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                if (bottom_sheet.findViewById(R.id.wi_fi_count).getTooltipText().equals("salam")) {

                    _wifiManagerOff();

                    bottom_sheet.findViewById(R.id.wi_fi_count).setTooltipText("sagol");

                } else if (bottom_sheet.findViewById(R.id.wi_fi_count).getTooltipText().equals("sagol")) {

                    _wifiManagerOn();

                    bottom_sheet.findViewById(R.id.wi_fi_count).setTooltipText("salam");

                }

            }
        });

        _bottom_dialog.setContentView(bottom_sheet);

        _bottom_dialog.show();

    }

    @SuppressLint("MissingPermission")
    private void _wifiManagerOn() {

        wifiManager.setWifiEnabled(true);

        Toasty.error(context, R.string.wifi_disable, Toast.LENGTH_SHORT).show();

    }

    @SuppressLint("MissingPermission")
    private void _wifiManagerOff() {

        wifiManager.setWifiEnabled(false);

        Toasty.success(context, R.string.wifi_enable, Toast.LENGTH_SHORT).show();

    }

    private void privacyAccept() {

        Button accept;

        Animation btnAnim;

        TextView oo, lll, ppOO, pWer;

        View mmmB;

        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation);

        final Dialog dialog = new Dialog(context);

        dialog.setContentView(R.layout.policy_dialog);

        dialog.setCancelable(false);

        dialog.setCanceledOnTouchOutside(false);

        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,

                WindowManager.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.getWindow().getAttributes().windowAnimations =

                android.R.style.Animation_Dialog;

        dialog.show();

        /**
         * BUttn and textwiew && View fidByID set Layout;;
         * oo,lll,ppOO,pWer  View mmmB
         */

        mmmB = dialog.findViewById(R.id.mmmB);
        oo = dialog.findViewById(R.id.oo);
        lll = dialog.findViewById(R.id.lll);
        ppOO = dialog.findViewById(R.id.ppOO);
        pWer = dialog.findViewById(R.id.pWer);

        /**
         * Set Animation Viewss
         */
        mmmB.setAnimation(btnAnim);
        oo.setAnimation(btnAnim);
        lll.setAnimation(btnAnim);
        ppOO.setAnimation(btnAnim);
        pWer.setAnimation(btnAnim);

        accept = dialog.findViewById(R.id.accept);

        accept.setAnimation(btnAnim);

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

                savePrefsData();

            }
        });


    }

    private void savePrefsData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("new", MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean("old", true);

        editor.commit();


    }

    private boolean restorePrefData() {


        SharedPreferences pref = getApplicationContext().getSharedPreferences("new", MODE_PRIVATE);

        Boolean isIntroActivityOpnendBefore = pref.getBoolean("old", false);

        return isIntroActivityOpnendBefore;


    }

    private void nightModeButtonSetChecked() {



        darkModeButton.setOnSwitchListener(new NightModeButton.OnSwitchListener() {
            @SuppressLint({"ResourceAsColor", "NewApi"})
            @Override
            public void onSwitchListener(boolean isNight) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (isNight) {

                        Log.d(TAG, "onCheckedChanged: ON");

                        view.setBackgroundResource(R.color.tre);

                        view.setSystemUiVisibility(uiOptions);

                        tabLayout.setBackgroundResource(R.color.tre);

                        tabLayout.setSelectedTabIndicatorColor(getColor(R.color.white));

                        tabLayout.setTabTextColors(ColorStateList.valueOf(getColor(R.color.white)));

                        window.setStatusBarColor(R.color.tre);

                        window.setNavigationBarColor(R.color.tre);

                        toolbar.setBackgroundResource(R.color.tre);

                        saveNightModeState(true);

                    }
                    else {

                        view.setBackgroundResource(R.color.red);

                        view.setSystemUiVisibility(uiOptions);

                        window = getWindow();

                        tabLayout.setBackgroundResource(R.color.red);

                        tabLayout.setSelectedTabIndicatorColor(getColor(R.color.black));

                        tabLayout.setTabTextColors(ColorStateList.valueOf(getColor(R.color.white)));

                        window.setStatusBarColor(R.color.white);

                        window.setNavigationBarColor(R.color.white);

                        toolbar.setBackgroundResource(R.color.red);

                        saveNightModeState(false);

                    }

                } else{

                    _darkModeDiminutiveVersion();

                }


            }
        });


    }

    private void saveNightModeState(boolean nightMode) {

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(KEY_ISNIGHTMODE, nightMode);

        editor.apply();


    }


    @SuppressLint({"ResourceAsColor", "NewApi"})
    private void checkNightModeActivated() {

        if (sharedPreferences.getBoolean(KEY_ISNIGHTMODE, false)) {

            //Todo burda setChecked olunacaq ,ama bollean olanda

            //TODO Set Checked true ve altda else de false olacaq.....

            view.setBackgroundResource(R.color.tre);

            view.setSystemUiVisibility(uiOptions);

            tabLayout.setBackgroundResource(R.color.tre);

            tabLayout.setSelectedTabIndicatorColor(getColor(R.color.white));

            tabLayout.setTabTextColors(ColorStateList.valueOf(getColor(R.color.white)));

            window.setStatusBarColor(R.color.tre);

            window.setNavigationBarColor(R.color.tre);

            toolbar.setBackgroundResource(R.color.tre);


        } else {

            view.setBackgroundResource(R.color.red);

            view.setSystemUiVisibility(uiOptions);

            window = getWindow();

            tabLayout.setBackgroundResource(R.color.red);

            tabLayout.setSelectedTabIndicatorColor(getColor(R.color.black));

            tabLayout.setTabTextColors(ColorStateList.valueOf(getColor(R.color.white)));

            window.setStatusBarColor(R.color.white);

            window.setNavigationBarColor(R.color.white);

            toolbar.setBackgroundResource(R.color.red);

        }


    }



}