package com.neodevloper.hdwallpaper2021.activitys.assistant.primary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.snackbar.Snackbar;
import com.neodevloper.hdwallpaper2021.R;
import com.neodevloper.hdwallpaper2021.database.DatabaseHandler;
import com.neodevloper.hdwallpaper2021.database.ImageModel;
import com.neodevloper.hdwallpaper2021.spotlight.OnSpotlightEndedListener;
import com.neodevloper.hdwallpaper2021.spotlight.OnSpotlightStartedListener;
import com.neodevloper.hdwallpaper2021.spotlight.SimpleTarget;
import com.neodevloper.hdwallpaper2021.spotlight.Spotlight;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class PreviewActivity extends AppCompatActivity {

    private static final String TAG = "PreviewActivity";
    private static final String CHANNEL_ID = " HD WALLPAPER";
    private static final String CHANNEL_NAME = "4K Wallpaper";
    private static final String CHANNEL_DESC = "Download";
    private Bitmap bitmap;
    private Toolbar toolbar;
    private static final int WRITE_EXTERNAL_STORAGE_CODE = 1;
    private ImageView view,like_list_image;
    private OutputStream out;
    private Button addDatabase;
    private File path, dir, file;
    private boolean download = false;
    private LottieAnimationView animationView;
    private Context mContext;
    private RelativeLayout image_preview_activ;
    private WallpaperManager wallpaperManager;
    private FloatingActionButton auto_wall_change, save;
    private FloatingActionMenu actionMenu;

    DatabaseHandler objectDatabaseHandler;
    Bitmap imageToStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        mContext = this;

        objectDatabaseHandler = new DatabaseHandler(mContext);



        actionMenu = findViewById(R.id.floatingBar);

        image_preview_activ = findViewById(R.id.image_preview_activ);

        animationView = findViewById(R.id.wait);


        view = findViewById(R.id.photo_image);
        auto_wall_change = findViewById(R.id.auto_wall_change);
        save = findViewById(R.id.save);

        fullHDD();
        /**
         * Action Menu Show this is stareed...
         */
        _actionMenuShow();//todo burasi qalibb........................................................................

//        nextImages();//TODO BURDA PREVIEW HISSEDE SEKILEERINI ARD ARDA  CEVIRMEK UCN YAZMLASAIN VE HLEDE YARIMCIQDI...


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);

            channel.setDescription(CHANNEL_DESC);

            NotificationManager manager = getSystemService(NotificationManager.class);

            manager.createNotificationChannel(channel);

        }


        toolbar = findViewById(R.id.toolbar_preview_for);
        setSupportActionBar(toolbar);

//todo burasini shared prefeces yazib doldurmaq lazimdui

        if (storageData()) {

            auto_wall_change.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (download == true) {
                        setWallpaper();
                    } else {

                        Snackbar
                                .make(image_preview_activ, R.string.go_download_image,
                                        Snackbar.LENGTH_SHORT).setBackgroundTint(ContextCompat.getColor(mContext, R.color.yellow)).show();

                    }

                }
            });

        }


        save.setOnClickListener(new View.OnClickListener() {
            @SuppressLint({"NewApi", "ObsoleteSdkInt"})
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED) {

                        String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

                        requestPermissions(permission, WRITE_EXTERNAL_STORAGE_CODE);

                    } else {

                        saveImage();

                        displayNotification();

                    }

                }

//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
//                    saveImage();
//
//                    displayNotification();
//                }

            }
        });

        Log.d(TAG, "onCreate: create");

        getIncomingIntent();


    }

    private String image_url;//TODO burda linki aldin bu sekilinin linkidi diger activiylere burdan gedecek...

    private void getIncomingIntent() {

        Log.d(TAG, "getIncomingIntent: setImage");

        if (getIntent().hasExtra("image")) {

            image_url = getIntent().getStringExtra("image");

            setImage(image_url);

        }

    }

    private void setImage(String image_url) {

        Log.d(TAG, "setImage: data create");

        Glide.with(mContext)

                .asBitmap()

                .load(image_url)

                .thumbnail(0.5f)

                .centerCrop()

                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        animationView.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        animationView.setVisibility(View.GONE);
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view);

    }

    //TODO Full Activity put Image.....
    private void fullHDD() {

        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                Log.d(TAG, "onClick: full HDD image put Intent");

                Intent intent = new Intent(mContext, FullScreenActivity.class);

                intent.putExtra("full", image_url);

                startActivity(intent);

                return true;
            }
        });

    }

    private void saveImage() {

        download = true;

        bitmap = ((BitmapDrawable) view.getDrawable()).getBitmap();

        String time = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());

        path = Environment.getExternalStorageDirectory();

        dir = new File(path.getAbsolutePath() + "/DCIM/Wallpaper_HD2020");//todo if yaz ve onu yoxla yaddasda...

        dir.mkdir();

        String imageName = time + ".png";

        file = new File(dir, imageName);//TODO burnunla telde sekilini olub olmaidigi yoxlanilir///


        try {

            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();


        } catch (Exception e) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void setWallpaper() {

        download = true;

        wallpaperManager = WallpaperManager.getInstance(getApplicationContext());

        try {
            wallpaperManager.setBitmap(bitmap);

            Toasty.success(mContext, "Set Wallpaper", Toast.LENGTH_SHORT).show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {

            case WRITE_EXTERNAL_STORAGE_CODE: {

                if (grantResults.length > 0 &&

                        grantResults[0] ==

                                PackageManager.PERMISSION_GRANTED) {

                } else {

                    Toast.makeText(mContext, "Permission Granted", Toast.LENGTH_SHORT).show();

                }
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_preview_for, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.report) {

            Intent intent = new Intent(mContext, AppReportActivity.class);
            startActivity(intent);

        } else if (id == R.id.shareImage) {

            shareImageUri();

        }

        return super.onOptionsItemSelected(item);
    }

    private void shareImageUri() {


        Intent shareIntent = new Intent(Intent.ACTION_SEND);

        shareIntent.setType("text/plain");

        String shareBody = image_url;

        String shareSub = "Your Subject Here";

        shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);

        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);

        startActivity(Intent.createChooser(shareIntent, "4K Wallpaper"));


    }

    private void displayNotification() {

        long[] pattern = {100, 200, 300, 400, 500, 600, 700, 800};

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.noticust)
                        .setContentTitle("Download Notification")
                        .setContentText("Image download successful")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setWhen(System.currentTimeMillis())
                        .setAutoCancel(true);

        mBuilder.setVibrate(pattern);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(alarmSound);

        NotificationManagerCompat mNotificationMgr = NotificationManagerCompat.from(this);

        mNotificationMgr.notify(2, mBuilder.build());


    }

    private void _actionMenuShow() {

        auto_wall_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionMenuSpotlight();
                dataPrefsSave();
            }
        });

    }

    private void actionMenuSpotlight() {

        View one = findViewById(R.id.auto_wall_change);
        int[] oneLocation = new int[2];
        one.getLocationInWindow(oneLocation);
        float oneX = oneLocation[0] + one.getWidth() / 2f;
        float oneY = oneLocation[1] + one.getHeight() / 2f;

        SimpleTarget firstTarget = new SimpleTarget.Builder(PreviewActivity.this).setPoint(oneX, oneY)
                .setRadius(80f)
                .setDescription(getString(R.string.follow_the_wallpaper))
                .setTitle(getString(R.string.go_back_click))
                .build();

        Spotlight.with(PreviewActivity.this)
                .setDuration(1000L)
                .setAnimation(new DecelerateInterpolator(2f))
                .setTargets(firstTarget)
                .setOnSpotlightStartedListener(new OnSpotlightStartedListener() {
                    @Override
                    public void onStarted() {
                        /**
                         * Started
                         */
                    }
                })
                .setOnSpotlightEndedListener(new OnSpotlightEndedListener() {
                    @Override
                    public void onEnded() {
                        /**
                         * Ended
                         */
                    }
                }).start();

    }

    private boolean storageData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        Boolean dataSave = pref.getBoolean("mmXl", false);
        return dataSave;

    }

    private void dataPrefsSave() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("mmXl", true);
        editor.commit();

    }

    private void storeImage(View pattern){

        try {

            imageToStore = Bitmap.createBitmap(view.getWidth(),
                    view.getHeight(),Bitmap.Config.ARGB_8888);
            view.setImageBitmap(imageToStore);

            if (view.getDrawable()!=null && imageToStore!=null){
                objectDatabaseHandler.storeImage(new ImageModel(imageToStore));
            }else {
                Toast.makeText(mContext, "Please select image", Toast.LENGTH_SHORT).show();
            }


        }catch (Exception deft){
            Toast.makeText(this, deft.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }





    }



}
