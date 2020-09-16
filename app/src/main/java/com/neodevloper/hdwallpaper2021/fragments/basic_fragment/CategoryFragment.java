package com.neodevloper.hdwallpaper2021.fragments.basic_fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.neodevloper.hdwallpaper2021.R;

public class CategoryFragment extends Fragment {
    private Context context;
    private View root;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_category, container, false);

        context = getActivity();

        return root;
    }

}
