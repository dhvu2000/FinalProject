package com.example.myapplication.Views.TimerScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.Views.CollectionsScreen.WorkOutProcessScreen.WorkOutProcessScreen1;
import com.example.myapplication.Views.CollectionsScreen.WorkOutProcessScreen.WorkoutProcessScreen2;

import java.util.Timer;

public class CountingTimerScreen extends AppCompatActivity {

    ImageButton btnSkip, btnStop, btnPauseContinue;
    ProgressBar progressBar;
    TextView txtTime, txtHead, txtBtnPauseContinue, txtProcess;

    int work, pre, rest, round;
    int sequence = 0;
    boolean isResting = true;
    long time;
    Timer T;
    MediaPlayer mediaPlayer;
    MediaPlayer countDownVoice;

    //Timer
    private CountDownTimer countDownTimer;
    private boolean timerRunning = true;
    private int timeLeft;
    private int waitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_counting_timer_screen);

        btnSkip = findViewById(R.id.btnSkip);
        btnStop = findViewById(R.id.btnStop);
        btnPauseContinue = findViewById(R.id.btnPauseContinue);
        txtBtnPauseContinue = findViewById(R.id.txtBtnPauseContinue);
        progressBar = findViewById(R.id.progressBar);
        txtTime = findViewById(R.id.txtTime);
        txtHead = findViewById(R.id.txtHead);
        txtProcess = findViewById(R.id.txtProcess);

        Intent i = getIntent();
        pre = i.getIntExtra("pre", 4);
        rest = i.getIntExtra("rest", 4);
        work = i.getIntExtra("work", 4);
        round = i.getIntExtra("round", 1);

        progressBar.setProgress(100);
        timeLeft = pre;
        waitTime = pre;

        txtProcess.setText(sequence+"/"+round+" hiệp");

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseTimer();
                if(countDownVoice != null) {
                    countDownVoice.release();
                    countDownVoice = null;
                }
                moveToNext();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseTimer();
                finish();
            }
        });

        btnPauseContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickBtnPauseContinue();

            }
        });


        mediaPlayer = MediaPlayer.create(this, R.raw.bell);
        mediaPlayer.start();

    }

    @Override
    protected void onStart() {
        super.onStart();

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

    private void startTimer()
    {
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

    private void moveToNext()
    {
        progressBar.setProgress(100);
        if(isResting)
        {
            isResting = false;
            sequence ++;
            timeLeft = work;
            waitTime = work;
            txtHead.setText("Tập");
            txtProcess.setText(sequence+"/"+round+" hiệp");
            startTimer();
            mediaPlayer = MediaPlayer.create(this, R.raw.start);
            mediaPlayer.start();
        }
        else
        {
            if(sequence == round)
            {
                mediaPlayer = MediaPlayer.create(this, R.raw.bell);
                mediaPlayer.start();
                finish();
                return;
            }
            txtHead.setText("Nghỉ giữa hiệp");
            isResting = true;
            timeLeft = rest;
            waitTime = rest;
            startTimer();
            mediaPlayer = MediaPlayer.create(this, R.raw.rest);
            mediaPlayer.start();
        }
    }

    private void onClickBtnPauseContinue()
    {
        if(timerRunning == true)
        {
            pauseTimer();
            btnPauseContinue.setImageResource(R.drawable.continue_icon);
            txtBtnPauseContinue.setText("Continue");
        }
        else
        {
            startTimer();
            btnPauseContinue.setImageResource(R.drawable.pause_icon);
            txtBtnPauseContinue.setText("Pause");
        }
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

    private void pauseTimer()
    {
        if(countDownVoice != null) countDownVoice.pause();
        countDownTimer.cancel();
        timerRunning = false;
    }
}