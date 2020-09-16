package com.neodevloper.hdwallpaper2021.adapter.listadapters.single_item;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.neodevloper.hdwallpaper2021.R;
import com.neodevloper.hdwallpaper2021.activitys.assistant.primary.PreviewActivity;
import com.neodevloper.hdwallpaper2021.view.listSearch.ResultsItem;

import java.util.List;

public class SingleAdapter extends RecyclerView.Adapter<SingleAdapter.ViewHolder> {

    private static final String TAG = "ListAdapter";

    private List<ResultsItem> resultsItems;
    private Context mContext;
    private boolean isLoadingAdded = false;
    private static final int ITEM = 0;
    private static final int LOADING = 1;

    public SingleAdapter(List<ResultsItem> resultsItems, Context mContext) {
        this.resultsItems = resultsItems;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.list_single_item,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        ResultsItem modem = resultsItems.get(position);

        final ViewHolder holders = holder;

        Glide.with(mContext)
                .asBitmap()
                .load(modem.getUrls().getSmall())
                .thumbnail(0.5f)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        holders._progressBar.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        holders._progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView);

        holder._constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: started");

                Intent i = new Intent(mContext, PreviewActivity.class);
                i.putExtra("image",resultsItems.get(position).getUrls().getRegular());
                mContext.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return resultsItems == null ? 0 : resultsItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == resultsItems.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        ConstraintLayout _constraintLayout;
        ProgressBar _progressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.list_single_image);
            _constraintLayout = itemView.findViewById(R.id.list_single_items);
            _progressBar = itemView.findViewById(R.id.list_single_progress);

        }

    }


    public void addImages(List<ResultsItem> images) {
        for (ResultsItem im : images) {
            resultsItems.add(im);
        }
        notifyDataSetChanged();
    }

}
