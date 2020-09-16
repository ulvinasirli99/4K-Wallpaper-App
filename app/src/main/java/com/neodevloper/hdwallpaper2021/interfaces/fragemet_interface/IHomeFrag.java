package com.neodevloper.hdwallpaper2021.interfaces.fragemet_interface;

import com.neodevloper.hdwallpaper2021.view.listSearch.Models;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IHomeFrag {

    @GET("search/photos?")
    Call<Models> getHomeFragment(

            @Query("query") String query,

            @Query("page") int page,

            @Query("orientation") String orientation,

            @Query("client_id") String client_id

    );
}
