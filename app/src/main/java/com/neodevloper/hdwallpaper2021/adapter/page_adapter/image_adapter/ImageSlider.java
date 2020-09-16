package com.neodevloper.hdwallpaper2021.adapter.page_adapter.image_adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.neodevloper.hdwallpaper2021.R;
import com.squareup.picasso.Picasso;

public class ImageSlider extends PagerAdapter {

    private Context mContext;
    private String [] image_urls;

    public ImageSlider(Context mContext, String[] image_urls) {
        this.mContext = mContext;
        this.image_urls = image_urls;
    }

    @Override
    public int getCount() {
        return image_urls.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        ImageView imageView = new ImageView(mContext);
        Picasso.get()
                .load(image_urls[position])
                .fit()
                .centerCrop()
                .into(imageView);

        container.addView(imageView);

        return imageView;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((View) object);

    }
}
