package com.neodevloper.hdwallpaper2021.adapter.page_adapter.viewadapter.screen_item.ui_page;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.neodevloper.hdwallpaper2021.fragments.basic_fragment.CategoryFragment;
import com.neodevloper.hdwallpaper2021.fragments.basic_fragment.HomeFragment;
import com.neodevloper.hdwallpaper2021.fragments.basic_fragment.LatestFragment;

public class PagerAdapter extends FragmentPagerAdapter {


    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new HomeFragment();
        } else if (position == 1) {
            return new CategoryFragment();
        } else if (position == 2) {
            return new LatestFragment();
        }
        return null;


    }

    @Override
    public int getCount() {
        return 3;
    }
}
