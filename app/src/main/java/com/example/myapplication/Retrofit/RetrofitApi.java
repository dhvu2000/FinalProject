package com.example.myapplication.Retrofit;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApi {

    private Retrofit retrofit;

    public  RetrofitApi()
    {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.195:9000")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
