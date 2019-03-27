package com.frama.miserend.hu.application.di

import com.frama.miserend.hu.api.MiserendApi

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Balazs on 2018. 03. 04..
 */
@Module
class ApiModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): MiserendApi {
        return retrofit.create(MiserendApi::class.java)
    }

    companion object {

        private val BASE_URL = "http://miserend.hu"
    }

}
