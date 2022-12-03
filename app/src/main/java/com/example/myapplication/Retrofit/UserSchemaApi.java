package com.example.myapplication.Retrofit;

import com.example.myapplication.Model.User.UserSchema;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserSchemaApi {

    @POST("/user-schema/save")
    Call<UserSchema> saveUserSchema(@Body UserSchema schema);

    @DELETE("/user-schema/delete/{id}")
    Call<Void> deleteSchema(@Path("id") int id);
}
