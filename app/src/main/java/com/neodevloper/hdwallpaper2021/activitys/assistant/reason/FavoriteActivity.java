package com.neodevloper.hdwallpaper2021.activitys.assistant.reason;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.neodevloper.hdwallpaper2021.R;
import com.neodevloper.hdwallpaper2021.database.DatabaseHandler;
import com.neodevloper.hdwallpaper2021.database.RVAdapter;

public class FavoriteActivity extends AppCompatActivity {

    private Context context;
    private DatabaseHandler objectDatabaseHandler;
    private RecyclerView objectRecyclerView;
    private RVAdapter objectRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        context = this;

        try {

            objectRecyclerView = findViewById(R.id.favorite_list);

            objectDatabaseHandler = new DatabaseHandler(context);


        } catch (Exception d) {
            Toast.makeText(context, d.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    public void getData() {

        try {

            objectRVAdapter = new RVAdapter(objectDatabaseHandler.getAllImageData());
            objectRecyclerView.setHasFixedSize(true);
            objectRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            objectRecyclerView.setAdapter(objectRVAdapter);

        } catch (Exception b) {
            Toast.makeText(context, b.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }


    }

}
