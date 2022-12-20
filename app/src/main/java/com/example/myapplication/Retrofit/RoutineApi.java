package com.example.myapplication.Retrofit;

import com.example.myapplication.Model.WorkOutUnit.Routine.Routine;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RoutineApi {

    @GET("/routine/get-by-user/{userId}")
    Call<ArrayList<Routine>> getRoutineByUserId(@Path("userId") int userId);

    @POST("/routine/save")
    Call<Routine> save(@Body Routine user);

    @DELETE("/routine/delete/{Id}")
    Call<Void> deleteRoutine(@Path("Id") int Id);
}
