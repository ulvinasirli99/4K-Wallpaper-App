package com.neodevloper.hdwallpaper2021.fragments.basic_fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

public class HomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private Context context;
    private SwipeRefreshLayout _refreshLayout;
    private ProgressBar progressBar;
    private static final String quest = "images";
    private Random random = new Random();
    private int current_page = random.nextInt(50);
    private boolean isLoading = true;
    private int pastVisibleItem, visibleItemCount, totalItemCount, previous_total = 0;
    private int view_threeshold = 40;
    private GridLayoutManager layoutManager;
    private List<ResultsItem> resultsItems = new ArrayList<>();
    private String key = "RF0gtjnzXrS0msdIN5UD_lxcVf7yjjJS_8u9LzzYCWg";
    private View root;
    private RecyclerView recyclerView;
    private SingleAdapter listAdapter = new SingleAdapter(resultsItems, context);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_category, container, false);

        context = getActivity();

        progressBar = root.findViewById(R.id.progress_bar_frag);

        _refreshLayout = root.findViewById(R.id.refresh_data);

        _refreshLayout.setOnRefreshListener(this);

        _refreshLayout.setColorSchemeResources(R.color.red);

        layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView = root.findViewById(R.id.recycler_view_homeCate);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        onLoadRefreshing();


        return root;

    }

    private void hasData() {

        _refreshLayout.setRefreshing(true);

        Clone _serviceCall = ApiClient.getRetrofit().create(Clone.class);

        Call<Models> call = _serviceCall.getAll(quest, current_page, key);

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

                    _refreshLayout.setRefreshing(false);

                } else {
                    _refreshLayout.setRefreshing(false);
                    Toast.makeText(context, R.string.not_found_sever, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Models> call, Throwable t) {

                _refreshLayout.setRefreshing(false);

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

                    _refreshLayout.setRefreshing(false);

                } else {
                    _refreshLayout.setRefreshing(false);
                    Toast.makeText(context, R.string.not_found_sever, Toast.LENGTH_SHORT).show();
                }

                progressBar.setVisibility(View.GONE);


            }

            @Override
            public void onFailure(Call<Models> call, Throwable t) {
                _refreshLayout.setRefreshing(false);
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onRefresh() {
        hasData();
        pag();
    }

    private void onLoadRefreshing() {
        _refreshLayout.post(new Runnable() {
                                @Override
                                public void run() {
                                    hasData();
                                    pag();
                                }
                            }
        );
    }

}