package com.neodevloper.hdwallpaper2021.activitys.assistant.reason;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.neodevloper.hdwallpaper2021.BuildConfig;
import com.neodevloper.hdwallpaper2021.R;
import com.neodevloper.hdwallpaper2021.activitys.call_back_activity.ultimate.FinishActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class AboutActivity extends AppCompatActivity {

    private Context context;
    private Toolbar toolbar;
    private TextView versionCode;
    private ImageView twitteR,facebooK,_arrow_back;
    private CircleImageView instagraM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        context = this;

        toolbar = findViewById(R.id.toolbar_about);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");



        versionCode = findViewById(R.id.ver_code);
        versionCode.setText(BuildConfig.VERSION_NAME);

        twitteR = findViewById(R.id.imageView4);
        facebooK = findViewById(R.id.circleImageView);
        instagraM = findViewById(R.id.insmm);

        _arrow_back = findViewById(R.id._arrow_back);

        _arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, FinishActivity.class));
            }
        });



    }
}
