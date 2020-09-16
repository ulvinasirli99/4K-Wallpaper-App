package com.neodevloper.hdwallpaper2021.database;

import android.graphics.Bitmap;

public class ImageModel {

    private Bitmap image;

    public ImageModel(Bitmap image) {
        this.image = image;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
