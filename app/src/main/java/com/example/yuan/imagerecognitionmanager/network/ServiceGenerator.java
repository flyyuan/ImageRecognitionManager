package com.example.yuan.imagerecognitionmanager.network;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Yuan on 2017/5/5.
 * class comment:
 */

public class ServiceGenerator {
    private static final String API_BASE_URL= "http://114.115.139.232:8080/";
    private static OkHttpClient httpClient = new OkHttpClient.Builder()
            .build();

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());
    public static <S> S createService(Class<S> serviceClass){
        Retrofit retrofit = builder.client(httpClient).build();
        return retrofit.create(serviceClass);}
   }