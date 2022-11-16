package com.example.myapplication.Retrofit;

import com.example.myapplication.Model.WorkOutUnit.Routine.RoutineDay;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RoutineDayApi {

    @POST("/routine-day/save")
    Call<RoutineDay> save(@Body RoutineDay routineDay);

    @POST("/routine-day/save-all")
    Call<ArrayList<RoutineDay>> saveAll(@Body ArrayList<RoutineDay> routineDays);

    @DELETE("/routine-day/delete/{Id}")
    Call<Void> deleteRoutineDayById(@Path("Id") int Id);
}
