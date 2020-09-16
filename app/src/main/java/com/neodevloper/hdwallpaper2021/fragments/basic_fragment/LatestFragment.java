package com.neodevloper.hdwallpaper2021.fragments.basic_fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neodevloper.hdwallpaper2021.R;
import com.neodevloper.hdwallpaper2021.adapter.listadapters.pair_list.ListAdapter;
import com.neodevloper.hdwallpaper2021.view.listSearch.ResultsItem;

import java.util.ArrayList;
import java.util.List;

public class LatestFragment extends Fragment {

    Context context;
    List<ResultsItem> resultsItems = new ArrayList<>();
    String key = "RF0gtjnzXrS0msdIN5UD_lxcVf7yjjJS_8u9LzzYCWg";
    View root;
    int current_page = 1;
    private ProgressBar progressBar;
    public static final String quest = "sport";
    private boolean isLoading = true;
    private GridLayoutManager layoutManager;
    private int pastVisibleItem, visibleItemCount, totalItemCount, previous_total = 0;
    private int view_threeshold = 40;
    RecyclerView recyclerView;
    ListAdapter listAdapter = new ListAdapter(resultsItems, context);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_category, container, false);

        return root;
    }

}
