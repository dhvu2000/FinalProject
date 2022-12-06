package com.example.myapplication.Supporter;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.myapplication.R;
import com.example.myapplication.Retrofit.NotificationApi;
import com.example.myapplication.Retrofit.RetrofitApi;
import com.example.myapplication.Retrofit.RoutineApi;
import com.example.myapplication.Views.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Date;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationCreator extends FirebaseMessagingService {


    private String token;


    NotificationApi notificationApi = RetrofitApi.getClient("https://fcm.googleapis.com/").create(NotificationApi.class);


    public void sendNotification(String title, String message, long dueDate, long repeatedTime, String channel)
    {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        token = task.getResult();
                        String noti = createNotification(title, message, dueDate, repeatedTime, channel, "add");
                        System.out.println("-------------Token-----------: "+token);


                        System.out.println(noti);
                        JsonObject convertnoti= new Gson().fromJson(noti,JsonObject.class);
                        Call<JsonObject> setschedule= notificationApi.setNotificationDueDate(convertnoti);
                        setschedule.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                System.out.println("send Message success: "+ response.body());
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                System.out.println("send Message error: "+ t.toString());
                            }
                        });
                    }

                });
    }

    public void deleteNotification(String channel)
    {
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<String> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        token = task.getResult();
                        String noti = createNotification("null", "null", 0, 0, channel, "delete");
                        System.out.println("-------------Token-----------: "+token);


                        System.out.println(noti);
                        JsonObject convertnoti= new Gson().fromJson(noti,JsonObject.class);
                        Call<JsonObject> setschedule= notificationApi.setNotificationDueDate(convertnoti);
                        setschedule.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                System.out.println("send Message success: "+ response.body());
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                System.out.println("send Message error: "+ t.toString());
                            }
                        });
                    }

                });
    }

    private String createNotification(String title, String message, long dueDate, long repeatedTime, String channel, String type)
    {
        String noti = "{" +
                "    \"to\":\"" + token + "\"," +
                " \"priority\": \"high\"," +
                " \"data\" : {" +
                " \"id\":" + (new Date().getSeconds()+ new Random().nextInt()) + "," +
                "  \"title\" : \""+title+"\"," +
                "  \"message\" :\"" + message  + "\"," +
                "  \"scheduledTime\" : \""+ dueDate+"\"," +
                "  \"type\" : \""+ type+"\"," +
                "  \"repeatedTime\" : \""+ repeatedTime+"\"," +
                "  \"channel\" : \""+ channel+"\" }" +
                "}";
        return noti;
    }


    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        System.out.println(message.getNotification());
        if (message.getData() != null) {
            int id=Integer.parseInt(message.getData().get("id"));
            String title=message.getData().get("title");
            String body=message.getData().get("message");
            String channel= message.getData().get("channel");
            String type= message.getData().get("type");
            String scheduled= message.getData().get("scheduledTime");
            long repeatedTime= Long.parseLong(message.getData().get("repeatedTime"));
            long due= Long.parseLong(scheduled);
            if(type.equals("add"))
            {
                scheduleDueDate(id,due,title,body,channel, repeatedTime);
            }
            else if (type.equals("delete"))
            {
                receivedDeleteNotification(channel);
            }
//            else{
//                showNotification(title,body,"duedate");
//            }
        }
    }

    private Notification receiveNotification(String title, String messageBody, String channel){

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    channel,
                    channel,
                    NotificationManager.IMPORTANCE_DEFAULT);
            try{
                notificationManager.deleteNotificationChannel(channel);
            }catch (Exception ex)
            {
                ex.getStackTrace();
            }
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channel)
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setPriority(NotificationManager.IMPORTANCE_HIGH);

        return  notificationBuilder.build();
    }

    private void receivedDeleteNotification(String channel)
    {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try{
                notificationManager.deleteNotificationChannel(channel);
            }catch (Exception ex)
            {
                ex.getStackTrace();
            }
        }
    }

    public void scheduleDueDate(int id,long due,String title, String body,String channel, long repeatedTime) {
        Notification n= receiveNotification(title,body,channel);
        Intent i = new Intent(this,NotificationReceiver.class);
        i.putExtra("id",id);
        i.putExtra("notification",n);
        System.out.println(due);
        PendingIntent pdi= PendingIntent.getBroadcast(this,id,i,PendingIntent.FLAG_MUTABLE);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        //test
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,due, repeatedTime, pdi );
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
    }



    private void showNotification(String title, String body,String channelID) {
        Notification n= receiveNotification(title,body,channelID);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, n);
    }
}
