package com.frama.miserend.hu.api;


import com.frama.miserend.hu.report.model.ReportProblemBody;
import com.frama.miserend.hu.report.model.ReportProblemResponse;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Balazs on 2018. 03. 03..
 */

public interface MiserendApi {

    @GET("api/v4/updated/{date}")
    Single<Integer> updateAvailable(@Path("date") String date);

    @POST("api/v4/report")
    Single<ReportProblemResponse> reportProblem(@Body ReportProblemBody reportProblemBody);

}
