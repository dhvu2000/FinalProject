package com.example.myapplication.Retrofit;

import com.example.myapplication.Model.WorkOutUnit.WorkOutSet.WorkOutRecord;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WorkOutRecordApi {

    @GET("/work-out-record/get-by-user/{userId}")
    Call<ArrayList<WorkOutRecord>> getWorkOutRecordByUserId(@Path("userId") int userId);

    @POST("/work-out-record/save")
    Call<WorkOutRecord> save(@Body WorkOutRecord record);
}
