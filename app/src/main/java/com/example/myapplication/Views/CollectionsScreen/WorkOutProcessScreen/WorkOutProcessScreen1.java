package com.example.myapplication.Views.CollectionsScreen.WorkOutProcessScreen;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.myapplication.Model.WorkOutUnit.Routine.RoutineDay;
import com.example.myapplication.Model.WorkOutUnit.WorkOutSet.SetExercise;
import com.example.myapplication.Model.WorkOutUnit.WorkOutSet.WorkOutRecord;
import com.example.myapplication.Model.WorkOutUnit.WorkOutSet.WorkOutSet;
import com.example.myapplication.R;
import com.example.myapplication.Retrofit.RetrofitApi;
import com.example.myapplication.Retrofit.WorkOutRecordApi;
import com.example.myapplication.Supporter.SharePreferenceManager;
import com.example.myapplication.Supporter.TimeFormatter;
import com.example.myapplication.Views.ExerciseScreen.DetailExerciseDialog;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkOutProcessScreen1 extends AppCompatActivity {

    ProgressBar progressBar;
    ImageButton btnSkip, btnPauseContinue, btnStop;
    TextView txtProgressTime, txtHead, txtBtnPauseContinue, txtNextExerciseName, txtReps, txtTime;
    ImageView img;
    WorkOutSet workOutSet;
    int sequence;
    double calories;
    long time;
    Timer T;
    MediaPlayer mediaPlayer;
    MediaPlayer countDownVoice;

    //Timer
    private CountDownTimer countDownTimer;
    private boolean timerRunning = true;
    private int timeLeft;
    private int waitTime;

    //Retrofit
    RetrofitApi retrofitApi = new RetrofitApi();
    WorkOutRecordApi workOutRecordApi = retrofitApi.getRetrofit().create(WorkOutRecordApi.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_work_out_process_screen1);

        txtProgressTime = findViewById(R.id.progressTime);
        progressBar = findViewById(R.id.progressBar);
        btnSkip = findViewById(R.id.btnSkip);
        btnPauseContinue = findViewById(R.id.btnPauseContinue);
        btnStop = findViewById(R.id.btnStop);
        txtHead = findViewById(R.id.txtHead);
        txtBtnPauseContinue = findViewById(R.id.txtBtnPauseContinue);
        txtReps = findViewById(R.id.txtNextReps);
        txtTime = findViewById(R.id.txtNextTime);
        txtNextExerciseName = findViewById(R.id.txtNextExerciseName);
        img = findViewById(R.id.img);



        progressBar.setProgress(100);

        setValue();

        //set timer value
        if(sequence == 0)
        {
            txtHead.setText("Preparation");
            waitTime = workOutSet.getPreTime();
        }
        else {
            txtHead.setText("Rest");
            waitTime = workOutSet.getRestTime();
        }

        timeLeft = waitTime;

        SetExercise setExercise = workOutSet.getExercises().get(sequence);



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
        mediaPlayer = MediaPlayer.create(this, R.raw.bell);
        mediaPlayer.start();

        if(timerRunning == true)
            startTimer();
    }

    @Override
    protected void onPause() {
        super.onPause();
        boolean oldState = timerRunning;
        pauseTimer();
        timerRunning = oldState;
    }


    private void setValue() {
        sequence = getIntent().getIntExtra("sequence", 0);
        workOutSet = (WorkOutSet) getIntent().getSerializableExtra("set");
        if(sequence == 0)
        {
            calories = 0;
            time = 0;
        }
        else
        {
            calories = getIntent().getDoubleExtra("calories", 0);
            time = getIntent().getLongExtra("time", 0);
        }
    }

    private void setTxt()
    {
        SetExercise setExercise = workOutSet.getExercises().get(sequence);
        txtProgressTime.setText(timeLeft + " giây");
        txtNextExerciseName.setText("Tiếp: "+ setExercise.getExercise().getName());
        if(setExercise.getRepNum() != 0) txtReps.setText("x"+setExercise.getRepNum());
        else txtReps.setVisibility(View.INVISIBLE);
        if(setExercise.getTimeLength() != 0) txtTime.setText(setExercise.getTimeLength()+" secs");
        else txtTime.setVisibility(View.INVISIBLE);
    }

    private void setButtons() {
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moveToNext();
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

    private void moveToNext()
    {
        pauseTimer();
        Intent  intent = new Intent(WorkOutProcessScreen1.this, WorkoutProcessScreen2.class);
        intent.putExtra("set", workOutSet);
        intent.putExtra("sequence", sequence);
        intent.putExtra("calories", calories);
        intent.putExtra("time", time);
        startActivity(intent);
        finish();
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
                moveToNext();
            }
        }.start();
        timerRunning = true;
    }

    private void pauseTimer()
    {
        if(T!= null) T.cancel();
        if(countDownVoice != null) countDownVoice.pause();
        countDownTimer.cancel();
        timerRunning = false;
    }

    private void updateCountDownDisplay()
    {
        if(timeLeft == 6)
        {
            startVoice(R.raw.countdown);
        }
        if(timeLeft == 0)
        {
            mediaPlayer = MediaPlayer.create(this, R.raw.start);
            mediaPlayer.start();
        }
        txtProgressTime.setText(timeLeft+ " secs");
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

    private void startVoice(int mp3Id)
    {
        if(countDownVoice != null)
        {
            countDownVoice.release();
            countDownVoice = null;
        }
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
                        time ++;
                    }
                });
            }
        }, 1000, 1000);
    }

    private void moveToEnd() {
        Intent intent = new Intent(this, WorkOutProcessEndScreen.class);
        intent.putExtra("calories", calories);
        intent.putExtra("time", time);
        if(sequence == workOutSet.getExercises().size()) intent.putExtra("done", true);
        startActivity(intent);
        finish();
    }

    private void saveRecord() {
        Users user = (Users) new SharePreferenceManager(this).getObject("User", Users.class);
        String datetime = TimeFormatter.FormatDateTime(new Date());
        if(workOutSet instanceof RoutineDay)
        {
            ((RoutineDay) workOutSet).setRoutine(null);
        }
        WorkOutRecord workOutRecord = new WorkOutRecord(user,workOutSet, datetime, time, calories);
        updateRecordToSharedPreference(workOutRecord);
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
        moveToEnd();
    }

    private void updateRecordToSharedPreference(WorkOutRecord wr) {
        new SharePreferenceManager(WorkOutProcessScreen1.this).addRecord(wr);
    }

}