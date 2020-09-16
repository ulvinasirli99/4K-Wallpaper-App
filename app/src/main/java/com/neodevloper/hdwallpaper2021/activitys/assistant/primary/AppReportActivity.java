package com.neodevloper.hdwallpaper2021.activitys.assistant.primary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.neodevloper.hdwallpaper2021.R;
import com.neodevloper.hdwallpaper2021.activitys.call_back_activity.ultimate.FinishActivity;
import com.neodevloper.hdwallpaper2021.view.model.InputModel;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.UUID;

import es.dmoral.toasty.Toasty;

public class AppReportActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 111111000;
    private static final String CHANNEL_ID = " HD WALLPAPER";
    private static final String CHANNEL_NAME = "4K Wallpaper";
    private static final String CHANNEL_DESC = "Download";
    private NotificationCompat.Builder builder;
    private NotificationManager notificationManager;
    private RemoteViews remoteViews;
    private Context mContext;
    private ImageView img1;
    private EditText comeMsg;
    private Bitmap bitmap;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Button comeBtn;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_report);

        mContext = this;

        /**
         * Button is findView set Clicked
         *
         */

        comeMsg = findViewById(R.id.comeMsg);
        comeBtn = findViewById(R.id.btnMsg);
        img1 = findViewById(R.id.img1);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        comeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (comeMsg.getText().toString().isEmpty()) {
                    Toasty.info(mContext, "Your message is empty", Toast.LENGTH_SHORT).show();
                } else {
                    uploadImage();
                    appErrorMessageDatabase();
                    _waitDisplayNotification();
                }

            }
        });

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

    }

    private void chooseImage() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK

                && data != null && data.getData() != null) {

            filePath = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), filePath
                );
                img1.setImageBitmap(bitmap);
            } catch (IOException e) {

                e.printStackTrace();

            }


        }
    }

    private void uploadImage() {

        if (filePath != null) {

            final ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference reference = storageReference.child("AppErrorImages/" + UUID.randomUUID().toString());

            reference.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toasty.success(mContext, "Image Uploaded", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                            double progress = (100.0 * taskSnapshot.getBytesTransferred()
                                    / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + (int) progress + "%");

                        }
                    });
        }

    }

    private void appErrorMessageDatabase() {

        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("Message Send...");
        progressDialog.show();

        InputModel inputModel = new InputModel(

                comeMsg.getText().toString()

        );
        String currentTime = DateFormat.getDateTimeInstance()
                .format(Calendar.getInstance().getTime());

        FirebaseDatabase.getInstance().getReference("UserErrorMessage")
                .child(currentTime)
                .setValue(inputModel)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            Toasty.normal(mContext, "Successful", Toast.LENGTH_SHORT).show();
                            img1.setImageResource(R.drawable.ic_camera_alt_black_24dp);
                            comeMsg.setText("");
                            progressDialog.dismiss();
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });

    }

    private void _waitDisplayNotification() {

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(20000);
                    _sendMessageReportNotification();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.start();

    }

    private void _sendMessageReportNotification() {

        long[] pattern = {100, 200, 300, 400, 500, 600, 700, 800};

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.small_report_msg)
                        .setContentTitle("Thank you for feedback")
                        .setContentText("Your message has been received !")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setWhen(System.currentTimeMillis())
                        .setAutoCancel(true);

        mBuilder.setVibrate(pattern);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(alarmSound);

        NotificationManagerCompat mNotificationMgr = NotificationManagerCompat.from(this);

        mNotificationMgr.notify(2, mBuilder.build());

    }

}