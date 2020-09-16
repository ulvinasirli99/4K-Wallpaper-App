package com.neodevloper.hdwallpaper2021.fragments.basic_fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.neodevloper.hdwallpaper2021.R;
import com.neodevloper.hdwallpaper2021.fragments.categoy_fragment.latter.CarFrag;
import com.neodevloper.hdwallpaper2021.fragments.categoy_fragment.latter.LightFrag;
import com.neodevloper.hdwallpaper2021.fragments.categoy_fragment.latter.WallFrag;
import com.neodevloper.hdwallpaper2021.fragments.categoy_fragment.sides.ArchitectureFrag;
import com.neodevloper.hdwallpaper2021.fragments.categoy_fragment.sides.FashionFrag;
import com.neodevloper.hdwallpaper2021.fragments.categoy_fragment.sides.LoveFrag;
import com.neodevloper.hdwallpaper2021.fragments.categoy_fragment.sides.SpaceFrag;
import com.neodevloper.hdwallpaper2021.fragments.categoy_fragment.sides.TravelFrag;
import com.neodevloper.hdwallpaper2021.fragments.categoy_fragment.special.AnimalFrag;
import com.neodevloper.hdwallpaper2021.fragments.categoy_fragment.special.CloudFrag;
import com.neodevloper.hdwallpaper2021.fragments.categoy_fragment.special.FlowerFrag;
import com.neodevloper.hdwallpaper2021.fragments.categoy_fragment.special.SeaFrag;
import com.smarteist.autoimageslider.DefaultSliderView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderLayout;

public class FinishCategoryList extends Fragment {

    private ListView listView;
    private Context context;
    private Toolbar toolbar;
    private SliderLayout sliderLayout;
    private View rootView;

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.finish_category_list, container, false);

        context = getActivity();

        sliderLayout = rootView.findViewById(R.id.category_image_slider_auto_fc);

        sliderLayout.setIndicatorAnimation(IndicatorAnimations.FILL);

        sliderLayout.setScrollTimeInSec(1);

        listView = rootView.findViewById(R.id.finish_category_list_fc);

        CategoryMListAdapters mListAdapter = new CategoryMListAdapters();

        listView.setAdapter(mListAdapter);

        setsliderLayout();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0) {
                    Intent car = new Intent(getContext(), SeaFrag.class);
                    startActivity(car);
                } else if (position == 1) {
                    Intent light = new Intent(getActivity(), CarFrag.class);
                    startActivity(light);
                } else if (position == 2) {
                    Intent wall = new Intent(getActivity(), LoveFrag.class);
                    startActivity(wall);
                } else if (position == 3) {
                    Intent arc = new Intent(getActivity(), AnimalFrag.class);
                    startActivity(arc);
                } else if (position == 4) {
                    Intent fash = new Intent(getActivity(), FashionFrag.class);
                    startActivity(fash);
                } else if (position == 5) {
                    Intent love = new Intent(getActivity(), SpaceFrag.class);
                    startActivity(love);
                } else if (position == 6) {
                    Intent spa = new Intent(getActivity(), TravelFrag.class);
                    startActivity(spa);
                } else if (position == 7) {
                    Intent tra = new Intent(getActivity(), ArchitectureFrag.class);
                    startActivity(tra);
                } else if (position == 8) {
                    Intent anim = new Intent(getActivity(), FlowerFrag.class);
                    startActivity(anim);
                } else if (position == 9) {
                    Intent bird = new Intent(getActivity(), WallFrag.class);
                    startActivity(bird);
                } else if (position == 10) {
                    Intent cloud = new Intent(getActivity(), LightFrag.class);
                    startActivity(cloud);
                } else if (position == 11) {
                    Intent flow = new Intent(getActivity(), CloudFrag.class);
                    startActivity(flow);
                }

            }
        });

        return rootView;

    }

    class CategoryMListAdapters extends BaseAdapter {


        @Override
        public int getCount() {

            return images.length;

        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


                LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.custom_list_view, parent, false);
                ImageView imageView = convertView.findViewById(R.id.category_image_list);
                imageView.setImageResource(images[position]);


            return convertView;

        }

    }

    private void setsliderLayout() {

        for (int i = 0; i <= 20; i++) {

            DefaultSliderView sliderView = new DefaultSliderView(context);

            switch (i) {
                case 0:
                    sliderView.setImageUrl("https://images.unsplash.com/photo-1590854690659-5637ffc859e6?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=634&q=80");
                    break;
                case 1:
                    sliderView.setImageUrl("https://images.unsplash.com/photo-1590855291949-fcccfbf48fec?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=634&q=80");
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

                case 10:
                    sliderView.setImageUrl("https://images.unsplash.com/photo-1590943339443-acf005b77d0a?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=634&q=80");
                    break;

                case 11:
                    sliderView.setImageUrl("https://images.unsplash.com/photo-1590886251595-32546bc31514?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=634&q=80");
                    break;

                case 12:
                    sliderView.setImageUrl("https://images.unsplash.com/photo-1590918595998-192659cb7f2c?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=668&q=80");
                    break;

                case 13:
                    sliderView.setImageUrl("https://images.unsplash.com/photo-1590896003488-fc97417d2302?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=634&q=80");
                    break;

                case 14:
                    sliderView.setImageUrl("https://images.unsplash.com/photo-1590807017447-3bb2ab6050f9?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=634&q=80");
                    break;

                case 15:
                    sliderView.setImageUrl("https://images.unsplash.com/photo-1478980236323-01c287f81aed?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1053&q=80");
                    break;

                case 16:
                    sliderView.setImageUrl("https://images.unsplash.com/photo-1590336751349-f65720fee481?ixlib=rb-1.2.1&auto=format&fit=crop&w=800&q=80");
                    break;

                case 17:
                    sliderView.setImageUrl("https://images.unsplash.com/photo-1590928597823-1be21fd7230d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=675&q=80");
                    break;

                case 18:
                    sliderView.setImageUrl("https://images.unsplash.com/photo-1590927371266-528185d0368d?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=621&q=80");
                    break;

                case 19:
                    sliderView.setImageUrl("https://images.unsplash.com/photo-1590805877322-f6544d4c69e2?ixlib=rb-1.2.1&auto=format&fit=crop&w=634&q=80");
                    break;

                case 20:
                    sliderView.setImageUrl("https://images.unsplash.com/photo-1590865748093-cb7fb60fbf20?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=634&q=80");
                    break;

            }

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);


            sliderLayout.addSliderView(sliderView);

        }


    }

}
