package com.neodevloper.hdwallpaper2021.activitys.assistant.primary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.neodevloper.hdwallpaper2021.R;
import com.neodevloper.hdwallpaper2021.adapter.page_adapter.viewadapter.intro.IntroViewPagerAdapter;
import com.neodevloper.hdwallpaper2021.adapter.page_adapter.viewadapter.screen_item._ui_item.ScreenItem;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends AppCompatActivity {

    private ViewPager screenPager;
    private  IntroViewPagerAdapter introViewPagerAdapter;
    private  TabLayout tabIndicator;
    private  Button btnNext;
    private int position = 0;
    private Button btnGetStarted;
    private  Animation btnAnim;
    private  TextView tvSkip;
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        context = this;

        if (restorePrefData()) {

            Intent mainActivity = new Intent(context, SplashActivity.class);
            startActivity(mainActivity);
            finish();

        }

        btnNext = findViewById(R.id.btn_next);
        btnGetStarted = findViewById(R.id.btn_get_started);
        tabIndicator = findViewById(R.id.tab_indicator);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.button_animation);
        tvSkip = findViewById(R.id.tv_skip);

        screenPager = findViewById(R.id.screen_viewpager);


        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem(getString(R.string.welcome_4k), getString(R.string.welcome_more), R.drawable.imga));
        mList.add(new ScreenItem(getString(R.string.count_wall), getString(R.string.count_more), R.drawable.imgb));
        mList.add(new ScreenItem(getString(R.string.finish), getString(R.string.wall_take), R.drawable.imgd));

        introViewPagerAdapter = new IntroViewPagerAdapter(context, mList);
        screenPager.setAdapter(introViewPagerAdapter);

        tabIndicator.setupWithViewPager(screenPager);


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position = screenPager.getCurrentItem();
                if (position < mList.size()) {

                    position++;
                    screenPager.setCurrentItem(position);


                }

                if (position == mList.size() - 1) { // when we rech to the last screen

                    // TODO : show the GET STARTED Button and hide the indicator and the next button

                    loaddLastScreen();


                }


            }
        });


        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == mList.size() - 1) {

                    loaddLastScreen();

                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //open main activity

                Intent mainActivity = new Intent(context, SplashActivity.class);
                startActivity(mainActivity);
                // also we need to save a boolean value to storage so next time when the user run the app
                // we could know that he is already checked the intro screen activity
                // i'm going to use shared preferences to that process
                savePrefsData();
                finish();


            }
        });


        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenPager.setCurrentItem(mList.size());
            }
        });

    }


    private boolean restorePrefData() {


        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);

        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend", false);

        return isIntroActivityOpnendBefore;


    }

    private void savePrefsData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean("isIntroOpnend", true);

        editor.commit();


    }


    private void loaddLastScreen() {

        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tvSkip.setVisibility(View.INVISIBLE);
        btnGetStarted.setAnimation(btnAnim);


    }


}
