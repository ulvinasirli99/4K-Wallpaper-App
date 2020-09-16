package com.neodevloper.hdwallpaper2021.database;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.neodevloper.hdwallpaper2021.R;

import java.util.ArrayList;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.RVViewHolderClass>
{

    ArrayList<ImageModel> objectModelClassList;

    public RVAdapter(ArrayList<ImageModel> objectModelClassList) {
        this.objectModelClassList = objectModelClassList;
    }

    @NonNull
    @Override
    public RVViewHolderClass onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RVViewHolderClass(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_row,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RVViewHolderClass holder, int position) {

        ImageModel modelClass = objectModelClassList.get(position);

        holder.imageView.setImageBitmap(modelClass.getImage());

    }

    @Override
    public int getItemCount() {
        return objectModelClassList.size();
    }

    public static class RVViewHolderClass extends RecyclerView.ViewHolder
    {
        ImageView imageView;
        public RVViewHolderClass(@NonNull View itemView)
        {
            super(itemView);
            imageView = itemView.findViewById(R.id.srImageTb);
        }
    }

}
