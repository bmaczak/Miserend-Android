package com.frama.miserend.hu.di.modules;

import com.frama.miserend.hu.api.MiserendApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Balazs on 2018. 03. 04..
 */
@Module
public class ApiModule {

    private static final String BASE_URL = "http://miserend.hu";

    @Singleton
    @Provides
    public Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    @Singleton
    @Provides
    public MiserendApi provideApi(Retrofit retrofit) {
        return retrofit.create(MiserendApi.class);
    }

}
