package com.example.myapplication.Retrofit;

import com.example.myapplication.Model.User.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UsersApi {

    @GET("/user/get-all")
    Call<List<Users>> getAllUsers();

    @GET("/user/get/{userId}")
    Call<Users> getUserById(@Path("userId") int userId);

    @POST("/user/save")
    Call<Users> save(@Body Users user);

}