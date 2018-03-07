package com.frama.miserend.hu.api;


import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Balazs on 2018. 03. 03..
 */

public interface MiserendApi {

    @GET("api/v4/updated/{date}")
    Single<Integer> updateAvailable(@Path("date") String date);

}
