package com.neodevloper.hdwallpaper2021.fragments.basic_fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

public class SearchFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private View rootView;
    private Context context;
    private int current_page = 1;
    private String DEFAULT_QUERY = "images";
    private boolean isLoading = true;
    private int pastVisibleItem, visibleItemCount, totalItemCount, previous_total = 0;
    private int view_threeshold = 40;
    private Button btnSearch;
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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_search, container, false);

        context = getActivity();

        refreshLayout = rootView.findViewById(R.id.swipe_refresh_f);

        refreshLayout.setOnRefreshListener(this);

        refreshLayout.setColorSchemeResources(R.color.red);


        dialog = new Dialog(context);

        dialog.setCancelable(false);

        dialog.setContentView(R.layout.search_progress_bar);

        dialog.setCanceledOnTouchOutside(false);

        dialog.getWindow().setLayout(WindowManager.LayoutParams.WRAP_CONTENT,

                WindowManager.LayoutParams.WRAP_CONTENT);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog.getWindow().getAttributes().windowAnimations =

                android.R.style.Animation_Dialog;

//        dialog.show();


        btnSearch = rootView.findViewById(R.id.call_search_f);

        recyclerView = rootView.findViewById(R.id.search_recycler_show_f);

        layoutManager = new GridLayoutManager(context, 2);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);

        editSearch = rootView.findViewById(R.id.activity_search_f);

        onLoadingRefresh();

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                query = editSearch.getText().toString();
                if (editSearch.getText().toString().isEmpty()) {

                    Toast.makeText(context, R.string.text_blank, Toast.LENGTH_SHORT).show();

                    return;
                }

                searchResult(query);

                searchRecScroll(query);

                dialog.show();

                editSearch.setText("");

            }
        });


        return rootView;

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
                    Toast.makeText(context, R.string.not_found_sever, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(context, R.string.not_found_sever, Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(context, R.string.not_found_sever, Toast.LENGTH_SHORT).show();
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

    private void onLoadingRefresh() {

        refreshLayout.post(
                new Runnable() {
                    @Override
                    public void run() {

                        defaultImages();

                    }
                }
        );

    }

    @Override
    public void onRefresh() {

        defaultImages();

    }



}

