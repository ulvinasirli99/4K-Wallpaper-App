package com.neodevloper.hdwallpaper2021.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class DatabaseHandler extends SQLiteOpenHelper {

    private Context context;
    private static String DATABASE_NAME = "IMAGE.DB";
    private static int DATABASE_VERSION = 2;
    private static String createTableQuery = "create table imageInfo (image BLOB)";

    private ByteArrayOutputStream objectByteArrayOutputStream;
    private byte[] imageInBytes;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(createTableQuery);
            Toast.makeText(context, "Table created successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toasty.info(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void storeImage(ImageModel objectModelClass) {

        try {
            SQLiteDatabase objectSqLiteDatabase = this.getWritableDatabase();
            Bitmap imageToStoreBitmap = objectModelClass.getImage();

            objectByteArrayOutputStream = new ByteArrayOutputStream();
            imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream);

            imageInBytes = objectByteArrayOutputStream.toByteArray();
            ContentValues objectContentValues = new ContentValues();

            objectContentValues.put("image", imageInBytes);
            long checkIfQueryRuns = objectSqLiteDatabase.insert("imageInfo", null, objectContentValues);

            if (checkIfQueryRuns!=-1){
                Toasty.info(context,"Data added into our table",Toast.LENGTH_SHORT).show();
                objectSqLiteDatabase.close();
            }else{
                Toasty.info(context,"Fails to add data",Toast.LENGTH_SHORT).show();
            }

        } catch (Exception pref) {
            Toasty.info(context, pref.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    public ArrayList<ImageModel> getAllImageData(){

        try {
            SQLiteDatabase objectSqLiteDatabase = this.getReadableDatabase();
            ArrayList<ImageModel> objectModelClassList = new ArrayList<>();

            Cursor objectCursor = objectSqLiteDatabase.rawQuery("select * from imageInfo",null);

            if (objectCursor.getCount()!=0){

                while (objectCursor.moveToNext()){
                    byte[] imageBytes = objectCursor.getBlob(1);
                    Bitmap objectBitmap = BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
                    objectModelClassList.add(new ImageModel(objectBitmap));
                }
                return objectModelClassList;

            }else {
                Toast.makeText(context, "No Values in Databases", Toast.LENGTH_SHORT).show();
                return null;
            }

        }catch (Exception allImageData){
            Toast.makeText(context, allImageData.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }

    }


}
