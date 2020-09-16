package com.neodevloper.hdwallpaper2021.activitys.assistant.reason;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.neodevloper.hdwallpaper2021.R;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

public class CategoryActivity extends AppCompatActivity {

    private ListView listView;
    private Context context;
    private Toolbar toolbar;
    private SliderLayout sliderLayout;
    private int[] images = {

            R.drawable.msea,
            R.drawable.cars,
            R.drawable.loves,
            R.drawable.manimal,
            R.drawable.mfashion,
            R.drawable.mspace,
            R.drawable.travels,
            R.drawable.marxektura,
            R.drawable.mflower,
            R.drawable.walls,
            R.drawable.mlight,
            R.drawable.mcloud,

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_finish);

        context = this;

        sliderLayout = findViewById(R.id.category_image_slider_auto);
        sliderLayout.setIndicatorAnimation(IndicatorAnimations.FILL);
        sliderLayout.setScrollTimeInSec(1);

        listView = findViewById(R.id.finish_category_list);
        toolbar = findViewById(R.id.category_finish_toolbar);
        setSupportActionBar(toolbar);


        CategoryMListAdapter mListAdapter = new CategoryMListAdapter();

        listView.setAdapter(mListAdapter);

        setsliderLayout();




    }


    class CategoryMListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View view = getLayoutInflater().inflate(R.layout.custom_list_view, null);

            ImageView mImageView = view.findViewById(R.id.category_image_list);

            mImageView.setImageResource(images[position]);

            return view;
        }
    }


    private void setsliderLayout() {

        for (int i = 0;i<=9;i++){

            DefaultSliderView sliderView  = new DefaultSliderView(this);

            switch (i){
                case 0:
                    sliderView.setImageUrl("https://images.unsplash.com/photo-1489824904134-891ab64532f1?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
                    break;
                case 1:
                    sliderView.setImageUrl("https://images.unsplash.com/photo-1504703395950-b89145a5425b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
                    break;
                case 2:
                    sliderView.setImageUrl("https://images.unsplash.com/photo-1462953491269-9aff00919695?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
                    break;
                case 3:
                    sliderView.setImageUrl("https://images.unsplash.com/photo-1567570744802-06798cc9d079?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
                    break;
                case 4:
                    sliderView.setImageUrl("https://images.unsplash.com/photo-1504608524841-42fe6f032b4b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
                    break;

                case 5:
                    sliderView.setImageUrl("https://images.unsplash.com/photo-1543076659-9380cdf10613?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
                    break;

                case 6:
                    sliderView.setImageUrl("https://images.unsplash.com/photo-1459259191495-52eccde892c7?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
                    break;

                case 7:
                    sliderView.setImageUrl("https://images.unsplash.com/photo-1523480717984-24cba35ae1ef?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
                    break;

                case 8:
                    sliderView.setImageUrl("https://images.unsplash.com/photo-1579497347163-ef1626df6a3f?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
                    break;

                case 9:
                    sliderView.setImageUrl("https://images.unsplash.com/photo-1493612276216-ee3925520721?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=500&q=60");
                    break;
            }

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);

            final int finalI = i;

            sliderLayout.addSliderView(sliderView);

        }


    }


}
