package com.neodevloper.hdwallpaper2021.interfaces.activity_interface;

import com.neodevloper.hdwallpaper2021.view.collections.ListCollections;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CInterface {

    @GET("search/collections?")
    Call<ListCollections> getCall(

            @Query("query") String query,

            @Query("page") int page,

            @Query("client_id") String client_id

    );

}
