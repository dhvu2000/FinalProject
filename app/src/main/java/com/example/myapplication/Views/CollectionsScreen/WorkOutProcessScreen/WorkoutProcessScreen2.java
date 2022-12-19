package com.example.myapplication.Views.CollectionsScreen.WorkOutProcessScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Model.User.Users;
import com.example.myapplication.Model.WorkOutUnit.Routine.Routine;
import com.example.myapplication.Model.WorkOutUnit.Routine.RoutineAct;
import com.example.myapplication.Model.WorkOutUnit.Routine.RoutineDay;
import com.example.myapplication.Model.WorkOutUnit.WorkOutSet.SetExercise;
import com.example.myapplication.Model.WorkOutUnit.WorkOutSet.WorkOutRecord;
import com.example.myapplication.Model.WorkOutUnit.WorkOutSet.WorkOutSet;
import com.example.myapplication.R;
import com.example.myapplication.Retrofit.RetrofitApi;
import com.example.myapplication.Retrofit.RoutineActApi;
import com.example.myapplication.Retrofit.WorkOutRecordApi;
import com.example.myapplication.Supporter.NotificationCreator;
import com.example.myapplication.Supporter.SharePreferenceManager;
import com.example.myapplication.Supporter.TimeFormatter;
import com.example.myapplication.Views.ExerciseScreen.DetailExerciseDialog;
import com.example.myapplication.Views.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkoutProcessScreen2 extends AppCompatActivity {


    ImageButton btnSkip, btnPauseContinue, btnStop;
    TextView txtName, txtBtnPauseContinue, txtBtnSkip, txtReps, txtTime;
    ImageView img;
    ProgressBar progressBar;
    ConstraintLayout areaBtnPauseContinue, areaTime;
    int sequence;
    double calories;
    long time;
    WorkOutSet workOutSet;
    MediaPlayer mediaPlayer;
    MediaPlayer countDownVoice;
    Timer T;

    //Timer
    private CountDownTimer countDownTimer;
    private boolean timerRunning = true;
    private int timeLeft;
    private int waitTime;

    //Retrofit
    RetrofitApi retrofitApi = new RetrofitApi();
    RoutineActApi routineActApi = retrofitApi.getRetrofit().create(RoutineActApi.class);
    WorkOutRecordApi workOutRecordApi = retrofitApi.getRetrofit().create(WorkOutRecordApi.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_workout_process_screen2);

        txtName = findViewById(R.id.txtName);
        areaBtnPauseContinue = findViewById(R.id.areaBtnPauseContinue);
        btnSkip = findViewById(R.id.btnSkip);
        btnPauseContinue = findViewById(R.id.btnPauseContinue);
        btnStop = findViewById(R.id.btnStop);
        txtBtnPauseContinue = findViewById(R.id.txtBtnPauseContinue);
        txtReps = findViewById(R.id.txtRepsNum);
        txtTime = findViewById(R.id.txtTime);
        txtBtnSkip = findViewById(R.id.txtBtnSkip);
        img = findViewById(R.id.img);
        progressBar = findViewById(R.id.progressBar);
        areaTime = findViewById(R.id.areaTime);

        setValue();
        SetExercise setExercise = workOutSet.getExercises().get(sequence);

       //setTimerValue
        waitTime = setExercise.getTimeLength();
        timeLeft = waitTime;
        progressBar.setProgress(100);

        if(setExercise.getExercise().getImg() != null && !setExercise.getExercise().getImg().trim().equals(""))
        {
            Glide.with(this).load(setExercise.getExercise().getImg()).error(R.drawable.add_image).into(img);
        }else{
            Picasso.get().load(R.drawable.add_image).into(img);
        }


        setTxt();
        setButtons();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(waitTime != 0 && timerRunning == true)
            startTimer();
        else
            startCounter();
    }

    @Override
    protected void onPause() {
        super.onPause();
        boolean oldState = timerRunning;
        pauseTimer();
        timerRunning = oldState;
    }

    private void setTxt()
    {
        SetExercise setExercise = workOutSet.getExercises().get(sequence);
        txtName.setText(setExercise.getExercise().getName());
        if (setExercise.getRepNum() != 0) txtReps.setText("x"+setExercise.getRepNum());
        else txtReps.setVisibility(View.GONE);
        if(waitTime != 0)txtTime.setText(waitTime+" s");
        else
        {
            areaTime.setVisibility(View.GONE);
            areaBtnPauseContinue.setVisibility(View.GONE);
            txtBtnSkip.setText("Tiếp tục");
        }
    }

    private void setValue() {

        sequence = getIntent().getIntExtra("sequence", 0);
        calories = getIntent().getDoubleExtra("calories", 0);
        time = getIntent().getLongExtra("time", 0);
        workOutSet = (WorkOutSet) getIntent().getSerializableExtra("set");
    }

    private void setButtons() {
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailExerciseDialog dialog = new DetailExerciseDialog(workOutSet.getExercises().get(sequence).getExercise());
                dialog.show(getSupportFragmentManager(), "DetailExerciseDialog");
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseTimer();
                if(calories > 0)
                {
                    saveRecord();
                }
                else
                {
                    moveToEnd();
                }
                finish();
            }
        });

        btnPauseContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBtnPauseContinue();

            }
        });
    }


    private void startTimer()
    {
        startCounter();
        if(countDownVoice != null) countDownVoice.start();
        countDownTimer = new CountDownTimer(timeLeft * 1000, 1000) {
            @Override
            public void onTick(long l) {
                timeLeft = (int) (l/1000);
                updateCountDownDisplay();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                next();
            }
        }.start();
        timerRunning = true;
    }

    private void pauseTimer()
    {
        if(T != null) T.cancel();
        if(countDownVoice != null) countDownVoice.pause();
        if(countDownTimer != null)
            countDownTimer.cancel();
        timerRunning = false;
    }

    private void updateCountDownDisplay()
    {
        if(timeLeft == 6)
        {
            startVoice(R.raw.countdown);
        }
        txtTime.setText(timeLeft+ " s");
        int progress = timeLeft * 100 /waitTime ;
        progressBar.setProgress(progress);
    }

    private void onClickBtnPauseContinue()
    {
        if(timerRunning == true)
        {
            pauseTimer();
            btnPauseContinue.setImageResource(R.drawable.continue_icon);
            txtBtnPauseContinue.setText("Tiếp tục");
        }
        else
        {
            startTimer();
            btnPauseContinue.setImageResource(R.drawable.pause_icon);
            txtBtnPauseContinue.setText("Dừng");
        }
    }


    private void moveToEnd() {
        Intent intent = new Intent(WorkoutProcessScreen2.this, WorkOutProcessEndScreen.class);
        intent.putExtra("calories", calories);
        intent.putExtra("time", time);
        if(sequence == workOutSet.getExercises().size()) intent.putExtra("done", true);
        startActivity(intent);
        finish();
    }

    private void moveToNext()
    {
        Intent intent = new Intent(WorkoutProcessScreen2.this, WorkOutProcessScreen1.class);
        intent.putExtra("set", workOutSet);
        intent.putExtra("sequence", sequence + 1);
        intent.putExtra("calories", calories);
        intent.putExtra("time", time);
        startActivity(intent);
        finish();
    }

    private void next()
    {
        pauseTimer();
        if(sequence + 1 == workOutSet.getExercises().size())
        {
            if(workOutSet instanceof RoutineDay)
            {
                saveProgress();
            }
            else
            {
                saveRecord();
            }
            updateSharedPreference();
            moveToEnd();
        }
        else{
            moveToNext();
        }
    }

    private void saveProgress() {
        String actTime = TimeFormatter.FormatDateTime(new Date());
        int progress = ((RoutineDay)workOutSet).getSequence();
        Users user = (Users) new SharePreferenceManager(this).getObject("User", Users.class);
        sendNotification();
        //////////
        Routine routine = ((RoutineDay) workOutSet).getRoutine();
        routine.setDays(null);
        RoutineAct routineAct = new RoutineAct(actTime, progress, user, routine);
        Call<RoutineAct> call = routineActApi.save(routineAct);
        call.enqueue(new Callback<RoutineAct>() {
            @Override
            public void onResponse(Call<RoutineAct> call, Response<RoutineAct> response) {
                saveRecord();
            }

            @Override
            public void onFailure(Call<RoutineAct> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    private void sendNotification() {
        RoutineDay routineDay = (RoutineDay)workOutSet;
        SharePreferenceManager sharePreferenceManager = new SharePreferenceManager(this);
        String oldWorkOutID = (String) sharePreferenceManager.getObject("OldWorkOutID",  String.class);
        ArrayList<RoutineDay> rds = (ArrayList<RoutineDay>) routineDay.getRoutine().getDays();
        int currentDayPos =  -1;
        for(int i = 0; i < rds.size() - 1; i++)
        {
            if(rds.get(i).getId() == routineDay.getId())
            {
                currentDayPos = i;
            }
        }
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MILLISECOND, 30000);
        NotificationCreator notificationCreator = new NotificationCreator(this);
        notificationCreator.deleteNotificationChannel(MainActivity.ROUTINE_ALARM_ID,MainActivity.ROUTINE_CHANNEL_ID);
        System.out.println("now: "+ new Date());
        if(currentDayPos != -1)
        {
            int distance = rds.get(currentDayPos + 1).getSequence() - rds.get(currentDayPos).getSequence();
            c.add(Calendar.DATE, distance);
            notificationCreator.setNextRoutineReminder(MainActivity.ROUTINE_ALARM_ID,c.getTimeInMillis(),rds.get(currentDayPos + 1).getName(),
                    "Bài tập tiếp theo của bạn đang tới",  MainActivity.ROUTINE_CHANNEL_ID);
        }
    }

    private void updateSharedPreference() {

    }

    private void saveRecord() {
        Users user = (Users) new SharePreferenceManager(this).getObject("User", Users.class);
        String datetime = TimeFormatter.FormatDateTime(new Date());
        if(workOutSet instanceof  RoutineDay)
        {
            ((RoutineDay) workOutSet).setRoutine(null);
        }
        WorkOutRecord workOutRecord = new WorkOutRecord(user,workOutSet, datetime, time, calories);
        updateSharedPreference();
        Call<WorkOutRecord> call = workOutRecordApi.save(workOutRecord);
        call.enqueue(new Callback<WorkOutRecord>() {
            @Override
            public void onResponse(Call<WorkOutRecord> call, Response<WorkOutRecord> response) {
            }

            @Override
            public void onFailure(Call<WorkOutRecord> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
    }

    private void startVoice(int mp3Id)
    {
        if(countDownVoice == null)
        {
            countDownVoice = MediaPlayer.create(this, mp3Id);
            countDownVoice.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    countDownVoice.release();
                    countDownVoice = null;
                }
            });
            countDownVoice.start();
        }
    }

    private void startCounter()
    {
        T=new Timer();
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        double calPerSec = workOutSet.getExercises().get(sequence).getExercise().getCalories()/60;
                        calories += calPerSec;
                        time ++;
                    }
                });
            }
        }, 1000, 1000);
    }
}