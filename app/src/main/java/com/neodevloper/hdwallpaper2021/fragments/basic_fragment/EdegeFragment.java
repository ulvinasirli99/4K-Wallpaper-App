package com.neodevloper.hdwallpaper2021.fragments.basic_fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.neodevloper.hdwallpaper2021.R;
import com.neodevloper.hdwallpaper2021.adapter.listadapters.pair_list.ListAdapter;
import com.neodevloper.hdwallpaper2021.interfaces.activity_interface.Clone;
import com.neodevloper.hdwallpaper2021.services.ApiClient;
import com.neodevloper.hdwallpaper2021.view.listSearch.Models;
import com.neodevloper.hdwallpaper2021.view.listSearch.ResultsItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EdegeFragment extends Fragment {
    Context context;
    List<ResultsItem> resultsItems = new ArrayList<>();
    String key = "RF0gtjnzXrS0msdIN5UD_lxcVf7yjjJS_8u9LzzYCWg";
    View rootView;
    RecyclerView recyclerView;
    ListAdapter listAdapter = new ListAdapter(resultsItems,context);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.e_fragment,container,false);

        context = getActivity();

        recyclerView = rootView.findViewById(R.id.e_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setHasFixedSize(true);

        Clone klon = ApiClient.getRetrofit().create(Clone.class);

        Call<Models> call = klon.getAll("city",1,key);

        call.enqueue(new Callback<Models>() {
            @Override
            public void onResponse(Call<Models> call, Response<Models> response) {
                if (response.isSuccessful()){

                    if (!resultsItems.isEmpty()){
                        resultsItems.clear();
                    }

                    resultsItems = response.body().getResults();
                    listAdapter = new ListAdapter(resultsItems,context);
                    recyclerView.setAdapter(listAdapter);
                    listAdapter.notifyDataSetChanged();

                }
                else {
                    Toast.makeText(context, "Olmadi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Models> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}
