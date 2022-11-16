package com.example.myapplication.Retrofit;

import com.example.myapplication.Model.WorkOutUnit.WorkOutSet.SetExercise;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface SetExerciseApi {

    @POST("/set-exercise/save")
    Call<SetExercise> save(@Body SetExercise setExercise);

    @POST("/set-exercise/save-all")
    Call<ArrayList<SetExercise>> saveAll(@Body ArrayList<SetExercise> setExercises);

    @DELETE("/set-exercise/delete/{Id}")
    Call<Void> deleteSetExerciseById(@Path("Id") int Id);

    @DELETE("/set-exercise/delete-all/{ids}")
    Call<Void>  deleteAllSetExerciseById(@Path("ids") String ids);
}
