package com.neodevloper.hdwallpaper2021.activitys.call_back_activity.end_final;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.neodevloper.hdwallpaper2021.activitys.call_back_activity.ultimate.InfoActivity;
import com.neodevloper.hdwallpaper2021.adapter.listadapters.pair_list.CustomAdapter;
import com.neodevloper.hdwallpaper2021.services.ApiClient;
import com.neodevloper.hdwallpaper2021.interfaces.activity_interface.Clone;
import com.neodevloper.hdwallpaper2021.R;
import com.neodevloper.hdwallpaper2021.view.listSearch.Models;
import com.neodevloper.hdwallpaper2021.view.listSearch.ResultsItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private View view;
    public static final String orintation = "portrait";
    public static final String QUERY = "black";
    int current_page = 1;
    private boolean isLoading = true;
    private int pastVisibleItem, visibleItemCount, totalItemCount, previous_total = 0;
    private int view_threeshold = 400;
    private Clone clone;
    private Switch aSwitch;
    private ProgressBar progressBar;
    public String key = "zujfFcHZrlb5FKHffCuO84ii41at_nC1ktmZG6WgUMo";
    private List<ResultsItem> itemList = new ArrayList<>();
    private Context context;
    private LottieAnimationView animationView;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private static final String ex = "Switch";
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private RecyclerView recyclerView;
    private CustomAdapter adapter = new CustomAdapter(context, itemList);
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        animationView = findViewById(R.id.inter_check);// TODO heleki yazmamisan axtar bax bunu yazmalisan...
        view = this.getWindow().getDecorView();
        view.setBackgroundResource(R.color.red);
        aSwitch = findViewById(R.id.dark_back_btn);
        progressBar = findViewById(R.id.progress_bar);
        adapter = new CustomAdapter(context, itemList);
        recyclerView = findViewById(R.id.recycler_view);
        layoutManager = new GridLayoutManager(context, 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open, R.string.close);
        toggle.syncState();
        aSwitch = findViewById(R.id.dark_back_btn);
        setSupportActionBar(toolbar);
        clone = ApiClient.getRetrofit().create(Clone.class);

        Call<Models> modelsCall = clone.getAll(QUERY, current_page,key);

        modelsCall.enqueue(new Callback<Models>() {
            @Override
            public void onResponse(Call<Models> call, Response<Models> response) {

                if (response.isSuccessful()) {

                    if (!itemList.isEmpty()) {
                        itemList.clear();
                    }


                    itemList = response.body().getResults();

                    adapter = new CustomAdapter(context, itemList);
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onFailure(Call<Models> call, Throwable t) {
                checkInternet();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisibleItem = layoutManager.findFirstVisibleItemPosition();

                if (dy > 0) {
                    if (isLoading) {
                        if (totalItemCount > previous_total) {
                            isLoading = false;
                            previous_total = totalItemCount;
                        }
                    }
                    if (!isLoading && (totalItemCount - visibleItemCount) <= (pastVisibleItem + view_threeshold)) {

                        current_page++;
                        performPagaination();
                        isLoading = true;

                    }
                }

            }
        });
    }

    private void performPagaination() {

        progressBar.setVisibility(View.VISIBLE);

        Call<Models> modelsCall = clone.getAll(QUERY, current_page, key);

        modelsCall.enqueue(new Callback<Models>() {
            @Override
            public void onResponse(Call<Models> call, Response<Models> response) {

                if (response.isSuccessful() && response.body().getResults() != null) {

                    List<ResultsItem> images = response.body().getResults();

                    adapter.addImages(images);


                }

                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<Models> call, Throwable t) {

                checkInternet();

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.about_apps) {
            Intent i = new Intent(context, InfoActivity.class);
            startActivity(i);
        } else if (id == R.id.deyis) {
            backroundChnaged();
        } else if (id == R.id.restart) {
            restartApp();
        }else if (id == R.id.share_images){
            shareApp();
        }

        return super.onOptionsItemSelected(item);
    }

    public void backroundChnaged() {

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(
                MainActivity.this, R.style.BottomSheetDialogTheme
        );

        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(
                        R.layout.dark_backround,
                        (RelativeLayout) findViewById(R.id.bootomSheetContanier)
                );

        bottomSheetView.findViewById(R.id.dark_back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                view.setBackgroundResource(R.color.black);
                toolbar.setBackgroundDrawable(new ColorDrawable(Color.BLACK));

            }
        });

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

    }

    private void restartApp() {

        PendingIntent p = PendingIntent.getActivity(context, 1000, getIntent(), PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, p);

        System.exit(0);

    }

    private void checkInternet() {

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                !networkInfo.isAvailable()) {

            animationView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void shareApp(){

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareBody = "https://play.google.com/store/apps/details?id=com.neodevloper.hdwallpaper2021";
        String shareSub = "Your Subject Here";
        shareIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
        shareIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
        startActivity(Intent.createChooser(shareIntent,"4K Wallpaper"));

    }

}