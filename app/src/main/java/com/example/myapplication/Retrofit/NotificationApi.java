package com.example.myapplication.Retrofit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NotificationApi {
    @Headers({"Content-Type: application/json",
            "Authorization: key=AAAAU5HElcQ:APA91bGc-jD-S06civxJpaCvIXrit979TaO2s92jzSgn8CINTHpNNM6jhdsOqzdXlHxviGHIF-9jDyg9I39Q1gy_Nqc2ROfkJrBE_aXp9klb-jDL0O0DCwhOhQavdENVIr364ykCCRK_"})
    @POST("fcm/send")
    Call<JsonObject> setNotificationDueDate(@Body JsonObject noti);
}
