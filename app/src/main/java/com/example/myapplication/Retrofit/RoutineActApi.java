package com.example.myapplication.Retrofit;

import com.example.myapplication.Model.WorkOutUnit.Routine.RoutineAct;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RoutineActApi {

    @GET("/routine-act/get-by-user/{userId}")
    Call<ArrayList<RoutineAct>> getRoutineActByUserId(@Path("userId") int userId);

    @POST("/routine-act/save")
    Call<RoutineAct> save(@Body RoutineAct routineAct);
}
