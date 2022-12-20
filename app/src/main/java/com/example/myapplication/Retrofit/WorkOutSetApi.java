package com.example.myapplication.Retrofit;

import com.example.myapplication.Model.WorkOutUnit.WorkOutSet.WorkOutSet;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WorkOutSetApi {

    @GET("/set/get-by-user/{userId}")
    Call<ArrayList<WorkOutSet>> getWorkOutSetByUserId(@Path("userId") int userId);

    @POST("/set/save")
    Call<WorkOutSet> saveWorkOutSet(@Body WorkOutSet workOutSet);

    @DELETE("/set/delete/{Id}")
    Call<Void> deleteWorkOutSet(@Path("Id") int Id);
}
