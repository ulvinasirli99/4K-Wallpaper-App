package com.neodevloper.hdwallpaper2021.fragments.categoy_fragment.special;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neodevloper.hdwallpaper2021.R;
import com.neodevloper.hdwallpaper2021.adapter.listadapters.single_item.SingleAdapter;
import com.neodevloper.hdwallpaper2021.interfaces.activity_interface.Clone;
import com.neodevloper.hdwallpaper2021.services.ApiClient;
import com.neodevloper.hdwallpaper2021.view.listSearch.Models;
import com.neodevloper.hdwallpaper2021.view.listSearch.ResultsItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnimalFrag extends AppCompatActivity {


    private Toolbar toolbar;
    private ProgressBar progressBar;
    private Context context;
    private static final String quest = "Animal";
    private Random random = new Random();
    private int current_page = random.nextInt(13);
    private boolean isLoading = true;
    private int pastVisibleItem, visibleItemCount, totalItemCount, previous_total = 0;
    private int view_threeshold = 40;
    private GridLayoutManager layoutManager;
    private List<ResultsItem> resultsItems = new ArrayList<>();
    private String key = "RF0gtjnzXrS0msdIN5UD_lxcVf7yjjJS_8u9LzzYCWg";
    private View root;
    private RecyclerView recyclerView;
    private SingleAdapter listAdapter = new SingleAdapter(resultsItems, context);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_animal_frag);
        context = this;

        recyclerView = findViewById(R.id.recycler_view_animal_frag);
        layoutManager = new GridLayoutManager(context, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        progressBar = findViewById(R.id.progress_bar_animal_frag);
        toolbar = findViewById(R.id.toolbar_a_f);
        setSupportActionBar(toolbar);

        Clone klon = ApiClient.getRetrofit().create(Clone.class);

        Call<Models> call = klon.getAll(quest, current_page, key);

        call.enqueue(new Callback<Models>() {
            @Override
            public void onResponse(Call<Models> call, Response<Models> response) {

                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful()) {

                    if (!resultsItems.isEmpty()) {
                        resultsItems.clear();
                    }

                    resultsItems = response.body().getResults();
                    listAdapter = new SingleAdapter(resultsItems, context);
                    recyclerView.setAdapter(listAdapter);
                    listAdapter.notifyDataSetChanged();

                } else {
                    Toast.makeText(context, "Olmadi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Models> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
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
                        pag();
                        isLoading = true;

                    }

                }

            }
        });

    }

    private void pag() {

        progressBar.setVisibility(View.VISIBLE);

        Clone klon = ApiClient.getRetrofit().create(Clone.class);

        Call<Models> call = klon.getAll(quest, current_page, key);

        call.enqueue(new Callback<Models>() {
            @Override
            public void onResponse(Call<Models> call, Response<Models> response) {
                if (response.isSuccessful() && response.body().getResults() != null) {

                    List<ResultsItem> images = response.body().getResults();

                    listAdapter.addImages(images);

                } else {
                    Toast.makeText(context, "Olmadi", Toast.LENGTH_SHORT).show();
                }

                progressBar.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<Models> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}
