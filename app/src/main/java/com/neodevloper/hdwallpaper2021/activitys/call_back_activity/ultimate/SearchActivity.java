package com.neodevloper.hdwallpaper2021.activitys.call_back_activity.ultimate;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.neodevloper.hdwallpaper2021.R;
import com.neodevloper.hdwallpaper2021.adapter.listadapters.pair_list.ListAdapter;
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

public class SearchActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    int current_page = 1;
    private String DEFAULT_QUERY = "images";
    private boolean isLoading = true;
    private int pastVisibleItem, visibleItemCount, totalItemCount, previous_total = 0;
    private int view_threeshold = 40;
    private Button btnSearch;
    private Toolbar toolbar;
    private Context context;
    private EditText editSearch;
    private Dialog dialog;
    private String query;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private List<ResultsItem> itemList = new ArrayList<>();
    private Random random = new Random();
    private int random_page = random.nextInt(50);
    private ListAdapter adapter = new ListAdapter(itemList, context);
    private String key = "ZnRhW9TWVninElSM2kpAUZ7a-zzMR-1INmazv9qmGoE";
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        context = this;

        refreshLayout = findViewById(R.id.swipe_refresh);

        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeResources(R.color.red);

        /*
        TODO BURDA DIALOG OLARAQ  SET OLUNUR
        Metod olaraq line line 202 bit next start....
         */

        dialog = new Dialog(context);

        dialog.setCancelable(false);

        dialog.setContentView(R.layout.search_progress_bar);

        dialog.setCanceledOnTouchOutside(false);

        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,

                WindowManager.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.getWindow().getAttributes().windowAnimations =

                android.R.style.Animation_Dialog;

        dialog.show();

        btnSearch = findViewById(R.id.call_search);

        recyclerView = findViewById(R.id.search_recycler_show);

        layoutManager = new GridLayoutManager(context, 2);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

        editSearch = findViewById(R.id.activity_search);

        onLoadingRefresh();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                query = editSearch.getText().toString();

                if (editSearch.getText().toString().isEmpty()) {

                    Toast.makeText(context, "Bos Olmaz", Toast.LENGTH_SHORT).show();

                    return;
                }

                searchResult(query);

                searchRecScroll(query);

                dialog.show();

            }
        });

//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//
//                visibleItemCount = layoutManager.getChildCount();
//                totalItemCount = layoutManager.getItemCount();
//                pastVisibleItem = layoutManager.findFirstVisibleItemPosition();
//
//                if (dy > 0) {
//
//                    if (isLoading) {
//
//                        if (totalItemCount > previous_total) {
//
//                            isLoading = false;
//
//                            previous_total = totalItemCount;
//
//                        }
//
//                    }
//
//                    if (!isLoading && (totalItemCount - visibleItemCount) <= (pastVisibleItem + view_threeshold)) {
//
//                        current_page++;
//
//                        defaultImageNextPagnitation();
//
//                        isLoading = true;
//
//                    }
//
//                }
//
//            }
//        });


    }


    private void searchResult(String keyword) {

        Clone clone = ApiClient.getRetrofit().create(Clone.class);

        Call<Models> callSearch = clone.getAll(keyword, current_page, key);

        callSearch.enqueue(new Callback<Models>() {
            @Override
            public void onResponse(Call<Models> call, Response<Models> response) {

                dialog.dismiss();

                if (response.isSuccessful()) {


                    itemList = response.body().getResults();

                    adapter = new ListAdapter(itemList, context);

                    recyclerView.setAdapter(adapter);

                    adapter.notifyDataSetChanged();


                } else {
                    Toast.makeText(context, "Yoxdu", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Models> call, Throwable t) {

                dialog.dismiss();

                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void searchRecScroll(final String keyword) {

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

                        searchNextPagnitation(keyword);

                        isLoading = true;

                    }

                }

            }
        });

    }

    private void searchNextPagnitation(String keyword) {

        Clone clone = ApiClient.getRetrofit().create(Clone.class);

        Call<Models> callSearch = clone.getAll(keyword, current_page, key);

        callSearch.enqueue(new Callback<Models>() {
            @Override
            public void onResponse(Call<Models> call, Response<Models> response) {

                dialog.dismiss();

                if (response.isSuccessful()) {


                    List<ResultsItem> images = response.body().getResults();

                    adapter.addImages(images);


                } else {
                    Toast.makeText(context, "Yoxdu", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Models> call, Throwable t) {

                dialog.dismiss();

                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }


    private void defaultImages() {

        refreshLayout.setRefreshing(true);

        Clone clone = ApiClient.getRetrofit().create(Clone.class);

        Call<Models> callSearch = clone.getAll(DEFAULT_QUERY, random_page, key);

        callSearch.enqueue(new Callback<Models>() {
            @Override
            public void onResponse(Call<Models> call, Response<Models> response) {

                dialog.dismiss();

                if (response.isSuccessful()) {


                    itemList = response.body().getResults();

                    adapter = new ListAdapter(itemList, context);

                    recyclerView.setAdapter(adapter);

                    adapter.notifyDataSetChanged();

                    refreshLayout.setRefreshing(false);


                } else {
                    Toast.makeText(context, "Yoxdu", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Models> call, Throwable t) {

                refreshLayout.setRefreshing(false);

                dialog.dismiss();

                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

        private void defaultImageNextPagnitation() {

        Clone klon = ApiClient.getRetrofit().create(Clone.class);

        Call<Models> call = klon.getAll(DEFAULT_QUERY, current_page, key);

        call.enqueue(new Callback<Models>() {
            @Override
            public void onResponse(Call<Models> call, Response<Models> response) {
                if (response.isSuccessful() && response.body().getResults() != null) {

                    List<ResultsItem> images = response.body().getResults();

                    adapter.addImages(images);

                } else {
                    Toast.makeText(context, "Olmadi", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Models> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void searchResultDialog() {

        dialog = new Dialog(context);

        dialog.setCancelable(false);

        dialog.setContentView(R.layout.search_progress_bar);

        dialog.setCanceledOnTouchOutside(false);

        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,

                WindowManager.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.getWindow().getAttributes().windowAnimations =

                android.R.style.Animation_Dialog;

        dialog.show();

    }

    @Override
    public void onRefresh() {

        defaultImages();

    }

    private  void onLoadingRefresh(){

        refreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {
                        defaultImages();
                    }
                }
        );

    }

}
