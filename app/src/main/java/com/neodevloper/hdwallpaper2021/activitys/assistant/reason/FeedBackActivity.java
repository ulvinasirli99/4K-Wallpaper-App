package com.neodevloper.hdwallpaper2021.activitys.assistant.reason;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.snackbar.Snackbar;
import com.neodevloper.hdwallpaper2021.R;

public class FeedBackActivity extends AppCompatActivity {

    private static final String TAG = "FeedBackActivity";
    private EditText email, sub, message;
    private Button btn_send;
    private Context context;
    private ScrollView feedBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

        context = this;

        feedBack = findViewById(R.id.feedBack);

        email = findViewById(R.id.email);
        sub = findViewById(R.id.subject);
        message = findViewById(R.id.message);
        btn_send = findViewById(R.id.sendFeedback);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int duration = 10;

                String mail = email.getText().toString().trim();
                String movzu = sub.getText().toString().trim();
                String mesaj = message.getText().toString().trim();

                if (email.getText().toString().isEmpty()) {
                    Snackbar.make(feedBack,"The email is blank",Snackbar.LENGTH_LONG).
                            setBackgroundTint(ContextCompat.getColor(context,R.color.blue))
                            .setTextColor(ContextCompat.getColor(context,R.color.black))
                            .show();
                    return;
                }
                else if (sub.getText().toString().isEmpty()) {
                    Snackbar.make(feedBack,"The subject is blank",Snackbar.LENGTH_LONG).
                            setBackgroundTint(ContextCompat.getColor(context,R.color.blue))
                            .setTextColor(ContextCompat.getColor(context,R.color.black))
                            .show();
                    return;
                }
               else if (message.getText().toString().isEmpty()){
                    Snackbar.make(feedBack,"The message is blank",Snackbar.LENGTH_LONG).
                            setBackgroundTint(ContextCompat.getColor(context,R.color.blue))
                            .setTextColor(ContextCompat.getColor(context,R.color.black))
                            .show();
                    return;
                }else {
                    sendFeed("4Kwallpaper2020@hotmail.com", movzu, mesaj);
                }

            }
        });

    }

    private void sendFeed(String mail, String movzu, String mesaj) {

        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("milato:"));
        emailIntent.setType("text/plain");

        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{mail});

        emailIntent.putExtra(Intent.EXTRA_SUBJECT, movzu);

        emailIntent.putExtra(Intent.EXTRA_TEXT, mesaj);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send Feedback"));
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

}
