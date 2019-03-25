package com.frama.miserend.hu.api


import com.frama.miserend.hu.report.model.ReportProblemBody
import com.frama.miserend.hu.report.model.ReportProblemResponse

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by Balazs on 2018. 03. 03..
 */

interface MiserendApi {

    @GET("api/v4/updated/{date}")
    fun updateAvailable(@Path("date") date: String): Single<Int>

    @POST("api/v4/report")
    fun reportProblem(@Body reportProblemBody: ReportProblemBody): Single<ReportProblemResponse>

}
