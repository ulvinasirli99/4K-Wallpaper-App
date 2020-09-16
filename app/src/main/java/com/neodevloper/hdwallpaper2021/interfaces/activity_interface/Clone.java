package com.neodevloper.hdwallpaper2021.interfaces.activity_interface;

import com.neodevloper.hdwallpaper2021.view.listSearch.Models;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Clone {

    @GET("search/photos?")
    Call<Models> getAll(

            @Query("query") String query,

            @Query("page") int page,

//            @Query("orientation") String orientation,

//            @Query("per_page") int per_page,

            @Query("client_id") String client_id

    );

}
