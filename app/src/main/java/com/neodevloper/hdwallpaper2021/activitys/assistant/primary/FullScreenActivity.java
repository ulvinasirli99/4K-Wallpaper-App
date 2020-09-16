package com.neodevloper.hdwallpaper2021.activitys.assistant.primary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.neodevloper.hdwallpaper2021.R;
import com.squareup.picasso.Picasso;

public class FullScreenActivity extends AppCompatActivity {

    PhotoView imageView;
    Context context;

    private static final String TAG = "FullScreenActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        context = this;

        imageView = findViewById(R.id.image_preview_full);

        _getFullHdd();

    }

    private void _getFullHdd(){

        if (getIntent().hasExtra("full")){
            String image = getIntent().getStringExtra("full");
            _setFullHdd(image);
        }

    }

    private void _setFullHdd(String image){

        Glide.with(context)
                .asBitmap()
                .load(image)
                .centerCrop().into(imageView);

    }

}
