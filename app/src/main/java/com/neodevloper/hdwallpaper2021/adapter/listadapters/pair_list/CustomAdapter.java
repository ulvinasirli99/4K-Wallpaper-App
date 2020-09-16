package com.neodevloper.hdwallpaper2021.adapter.listadapters.pair_list;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.neodevloper.hdwallpaper2021.R;
import com.neodevloper.hdwallpaper2021.activitys.assistant.primary.PreviewActivity;
import com.neodevloper.hdwallpaper2021.view.listSearch.ResultsItem;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    private static final String TAG = "Adapter";
    private Context context;
    private List<ResultsItem> resultsItems;
    private boolean isLoadingAdded = false;
    private static final int ITEM = 0;
    private static final int LOADING = 1;

    public CustomAdapter(Context context, List<ResultsItem> resultsItems) {
        this.context = context;
        this.resultsItems = resultsItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);

        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final ViewHolder holders = holder;

        ResultsItem modem = resultsItems.get(position);

        Glide.with(context)
                .asBitmap()
                .load(modem.getUrls().getRegular())
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        holders.animationView.setVisibility(View.VISIBLE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        holders.animationView.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.imageView);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "onClick: started");

                Intent i = new Intent(context, PreviewActivity.class);
                i.putExtra("image",resultsItems.get(position).getUrls().getRegular());
                context.startActivity(i);
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
        RelativeLayout parentLayout;
        LottieAnimationView animationView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id
                    .parent_layout);
            animationView = itemView.findViewById(R.id.list_wait);
            imageView = itemView.findViewById(R.id.image_name);

        }

    }

    public void addImages(List<ResultsItem> images) {
        for (ResultsItem im : images) {
            resultsItems.add(im);
        }
        notifyDataSetChanged();
    }

}
