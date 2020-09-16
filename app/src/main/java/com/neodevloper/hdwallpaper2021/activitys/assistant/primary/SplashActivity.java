package com.neodevloper.hdwallpaper2021.activitys.assistant.primary;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.neodevloper.hdwallpaper2021.R;
import com.neodevloper.hdwallpaper2021.activitys.call_back_activity.ultimate.FinishActivity;

public class SplashActivity extends AppCompatActivity {

    private Context context;
    private Button btnCoonect;
    private ImageView imageView;
    private TextView k4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageView = findViewById(R.id.sp_icon);
        k4 = findViewById(R.id.k4);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.splash_transition);
        imageView.setAnimation(animation);
        k4.setAnimation(animation);

        context = this;

        coonectNetwork();

    }

    private void coonectNetwork() {

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                !networkInfo.isAvailable()) {

            Dialog dialog = new Dialog(context);

            dialog.setCancelable(false);
            dialog.setContentView(R.layout.network_alert_dialog);
            dialog.setCanceledOnTouchOutside(false);
            dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT);

            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            dialog.getWindow().getAttributes().windowAnimations =
                    android.R.style.Animation_Dialog;

            btnCoonect = dialog.findViewById(R.id.bt_try_again);

            btnCoonect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recreate();
                }
            });

            dialog.show();

        } else {

            Thread timer = new Thread() {
                public void run() {
                    try {
                        sleep(2400);
                        Intent intent = new Intent(context, FinishActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            timer.start();

        }

    }
}
