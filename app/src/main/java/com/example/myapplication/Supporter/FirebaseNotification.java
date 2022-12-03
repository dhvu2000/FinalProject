package com.example.myapplication.Supporter;

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
import androidx.core.app.NotificationCompat;

import com.example.myapplication.R;
import com.example.myapplication.Views.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseNotification extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        System.out.println(message.getNotification());
        if (message.getData() != null) {
            int id=Integer.parseInt(message.getData().get("id"));
            String title=message.getData().get("title");
            String body=message.getData().get("message");
            String isSchedule= message.getData().get("isScheduled");
            if(isSchedule.equals("true")){
                long due= Long.parseLong(message.getData().get("scheduledTime"));
                scheduleDueDate(id,due,title,body,"duedate");
            }
            else{
                showNotification(title,body,"duedate");
            }
        }
    }
    private Notification receiveNotification(String title, String messageBody, String channel){
        Intent intent = new Intent(this, MainActivity.class);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channel)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle(title)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setPriority(NotificationManager.IMPORTANCE_HIGH);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    channel,
                    "Due Date",
                    NotificationManager.IMPORTANCE_DEFAULT);


            notificationManager.createNotificationChannel(notificationChannel);
        }
        return  notificationBuilder.build();
    }

    private void scheduleDueDate(int id,long due,String title, String body,String channel) {
        Notification n= receiveNotification(title,body,channel);
        Intent i = new Intent(this,NotificationReceiver.class);
        i.putExtra("id",id);
        i.putExtra("notification",n);
        System.out.println(due);
        PendingIntent pdi= PendingIntent.getBroadcast(this,id,i,PendingIntent.FLAG_MUTABLE);
//        long futureInMillis = SystemClock.elapsedRealtime();
//        System.out.println(futureInMillis);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, due, pdi);
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
