package com.neodevloper.hdwallpaper2021.activitys.call_back_activity.end_final;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.neodevloper.hdwallpaper2021.R;
import com.neodevloper.hdwallpaper2021.fragments.basic_fragment.CategoryFragment;
import com.neodevloper.hdwallpaper2021.fragments.basic_fragment.HomeFragment;
import com.neodevloper.hdwallpaper2021.fragments.basic_fragment.LatestFragment;


public class BasicActivity extends AppCompatActivity {

    //TODO POSTMANA GET VE ORDAN COLECTIONLARI GETIR.. Hemcinin yazdigin lisPhptos classini basa catdri ama esas catg[ry bolmesini yazmaslisan

    private Toolbar toolbar;
    LatestFragment fragment;
    private TabLayout tabLayout;
    private ViewPager pager;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic);
        context = this;
        toolbar = findViewById(R.id.basic_toolbar);
        setSupportActionBar(toolbar);
        tabLayout = findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(pager);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.category_white));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.home_white));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.latest));
        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);


        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        pager = findViewById(R.id.pager);
//        PageAdapter pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
//        pager.setAdapter(pageAdapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_b, new CategoryFragment()).commit();
                }

                if (tab.getPosition() == 1) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_b, new HomeFragment()).commit();

                }

                if (tab.getPosition() == 2) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_b, new LatestFragment()).commit();

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


}
