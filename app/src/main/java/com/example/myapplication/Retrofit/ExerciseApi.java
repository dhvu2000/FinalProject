package com.example.myapplication.Retrofit;

import com.example.myapplication.Model.User.Users;
import com.example.myapplication.Model.WorkOutUnit.Exercise;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ExerciseApi {

    @GET("/exercise/get-by-user/{userId}")
    Call<ArrayList<Exercise>> getExercisesByUserId(@Path("userId") int userId);

    @POST("/exercise/save")
    Call<Exercise> save(@Body Exercise exercise);

    @DELETE("/exercise/delete/{exerciseId}")
    Call<Void> deleteExerciseById(@Path("exerciseId") int exerciseId);


}
