package com.example.myapplication.Views.TimerScreen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.Supporter.RepeatListener;

public class TimerPageFragment extends Fragment {

    private TextView txtPreTime, txtRestTime, txtWorkTime, txtRoundNum;
    private Button btnPlusPre, btnMinusPre, btnPlusRest, btnMinusRest, btnPlusWork, btnMinusWork,
                    btnPlusRound, btnMinusRound;
    private ImageButton btnStart;

    private static final int PLUS_REST = 1;
    private static final int MINUS_REST = 2;
    private static final int PLUS_PRE = 3;
    private static final int MINUS_PRE = 4;
    private static final int PLUS_ROUND = 5;
    private static final int MINUS_ROUND = 6;
    private static final int PLUS_WORK = 7;
    private static final int MINUS_WORK = 8;


    public TimerPageFragment()
    {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_timer_page, container, false);
        txtPreTime = view.findViewById(R.id.txtPreTime);
        txtRestTime = view.findViewById(R.id.txtRestTime);
        txtWorkTime = view.findViewById(R.id.txtWorkTime);
        txtRoundNum = view.findViewById(R.id.txtRoundNum);
        btnPlusRound = view.findViewById(R.id.btnPlusRound);
        btnMinusRound = view.findViewById(R.id.btnMinusRound);
        btnPlusPre = view.findViewById(R.id.btnPlusPre);
        btnMinusPre = view.findViewById(R.id.btnMinusPre);
        btnPlusWork = view.findViewById(R.id.btnPlusWork);
        btnMinusWork = view.findViewById(R.id. btnMinusWork);
        btnPlusRest = view.findViewById(R.id.btnPlusRest);
        btnMinusRest = view.findViewById(R.id.btnMinusRest);
        btnStart = view.findViewById(R.id.btnStart);

        setPlusMinusButton(btnMinusPre, MINUS_PRE);
        setPlusMinusButton(btnPlusPre, PLUS_PRE);
        setPlusMinusButton(btnMinusRest, MINUS_REST);
        setPlusMinusButton(btnPlusRest, PLUS_REST);
        setPlusMinusButton(btnMinusRound, MINUS_ROUND);
        setPlusMinusButton(btnPlusRound, PLUS_ROUND);
        setPlusMinusButton(btnMinusWork, MINUS_WORK);
        setPlusMinusButton(btnPlusWork, PLUS_WORK);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pre = Integer.parseInt(txtPreTime.getText().toString());
                int rest = Integer.parseInt(txtRestTime.getText().toString());
                int round = Integer.parseInt(txtRoundNum.getText().toString());
                int work = Integer.parseInt(txtWorkTime.getText().toString());
                Intent i = new Intent(getContext(), CountingTimerScreen.class);
                i.putExtra("pre",pre );
                i.putExtra("rest",rest );
                i.putExtra("work",work );
                i.putExtra("round",round );
                startActivity(i);
            }
        });

        return view;
    }

    private void setPlusMinusButton(Button btn, int type)
    {
        btn.setOnTouchListener(new RepeatListener(400, 100, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // the code to execute repeatedly
                actionDo(type);
            }
        }));
    }

    private void actionDo(int type)
    {
        int pre;
        switch (type)
        {
            case PLUS_REST:
                pre = Integer.parseInt(txtRestTime.getText().toString()) + 1;
                if(pre >= 5) txtRestTime.setText(pre + "");
                break;
            case MINUS_REST:
                pre = Integer.parseInt(txtRestTime.getText().toString()) - 1;
                if(pre >= 5) txtRestTime.setText(pre + "");
                break;
            case PLUS_PRE:
                pre = Integer.parseInt(txtPreTime.getText().toString()) + 1;
                if(pre >= 5) txtPreTime.setText(pre + "");
                break;
            case MINUS_PRE:
                pre = Integer.parseInt(txtPreTime.getText().toString()) - 1;
                if(pre >= 5) txtPreTime.setText(pre + "");
                break;
            case PLUS_WORK:
                pre = Integer.parseInt(txtWorkTime.getText().toString()) + 1;
                if(pre >= 5) txtWorkTime.setText(pre + "");
                break;
            case MINUS_WORK:
                pre = Integer.parseInt(txtWorkTime.getText().toString()) - 1;
                if(pre >= 5) txtWorkTime.setText(pre + "");
                break;
            case PLUS_ROUND:
                pre = Integer.parseInt(txtRoundNum.getText().toString()) + 1;
                if(pre >= 1) txtRoundNum.setText(pre + "");
                break;
            case MINUS_ROUND:
                pre = Integer.parseInt(txtRoundNum.getText().toString()) - 1;
                if(pre >= 1) txtRoundNum.setText(pre + "");
                break;
        }
    }
}