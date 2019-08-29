package com.test.playground.api;

import com.test.playground.models.ITunes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestInterface {

    @GET("search")
    Call<ITunes> getJSON(@Query("term") String term);
}
