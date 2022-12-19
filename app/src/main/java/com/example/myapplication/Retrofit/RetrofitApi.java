package com.example.myapplication.Retrofit;

import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApi {

    private Retrofit retrofit;

    public static Retrofit getClient(String base_url) {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(logging);
        Retrofit retrofit1 = new retrofit2.Retrofit.Builder().baseUrl(base_url).addConverterFactory(GsonConverterFactory.create()).client(builder.build()).build();
        return retrofit1;
    }

    public  RetrofitApi()
    {
        retrofit = new Retrofit.Builder()
                .baseUrl("http://10.170.58.185:9000")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

    }


    public Retrofit getRetrofit() {
        return retrofit;
    }
}
