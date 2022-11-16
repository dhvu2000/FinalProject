package com.example.myapplication.Views.CollectionsScreen.BothUseScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.Model.WorkOutUnit.Exercise;
import com.example.myapplication.Model.WorkOutUnit.WorkOutSet.SetExercise;
import com.example.myapplication.R;
import com.example.myapplication.Supporter.RepeatListener;
import com.squareup.picasso.Picasso;

public class SettingExerciseDialog extends DialogFragment {


    private Exercise exercise;
    private ImageView img;
    private TextView txtName, txtRepNum, txtTime;
    private Button btnOk, btnPlusRep, btnMinusRep, btnPlusTime, btnMinusTime;
    private TextView txtGuide;

    public SettingExerciseDialog(Exercise exercise)
    {
        this.exercise = exercise;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View addSetExerciseDialog =  inflater.inflate(R.layout.dialog_setting_exercise_in_set,container,false);

        img = addSetExerciseDialog.findViewById(R.id.img);
        txtName = addSetExerciseDialog.findViewById(R.id.txtName);
        txtGuide = addSetExerciseDialog.findViewById(R.id.txtGuide);
        txtRepNum = addSetExerciseDialog.findViewById(R.id.txtRepsNum);
        txtTime = addSetExerciseDialog.findViewById(R.id.txtTime);
        btnOk = addSetExerciseDialog.findViewById(R.id.btnOk);
        btnMinusRep = addSetExerciseDialog.findViewById(R.id.btnMinusReps);
        btnPlusRep = addSetExerciseDialog.findViewById(R.id.btnPlusReps);
        btnMinusTime = addSetExerciseDialog.findViewById(R.id.btnMinusTime);
        btnPlusTime = addSetExerciseDialog.findViewById(R.id.btnPlusTime);

        if(exercise.getImg()!=null && !exercise.getImg().isEmpty())
        {
            Picasso.get().load(exercise.getImg()).error(R.drawable.add_image).into(img);
        }else
        {
            Picasso.get().load(R.drawable.add_image).into(img);
        }
        txtName.setText(exercise.getName());
//        txtGuide.setText(exercise.getGuideline());
        setButtons();


        return addSetExerciseDialog;
    }

    public void setButtons()
    {
        setPlusMinusButton(btnMinusRep,"minusRep");
        setPlusMinusButton(btnPlusRep,"plusRep");
        setPlusMinusButton(btnPlusTime,"plusTime");
        setPlusMinusButton(btnMinusTime,"minusTime");

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int time = Integer.parseInt(txtTime.getText().toString());
                int rep = Integer.parseInt(txtRepNum.getText().toString());
                //generate sequence
                SetExercise input = new SetExercise(time,rep,1,exercise);
                ((UpdateSaveCollectionScreen)getActivity()).addSetExercise(input);
                dismiss();
            }
        });
    }

    private void setPlusMinusButton(Button btn, String type)
    {
        btn.setOnTouchListener(new RepeatListener(400, 100, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // the code to execute repeatedly
                actionDo(type);
            }
        }));
    }

    private void actionDo(String type)
    {
        int pre;
        switch (type)
        {
            case "plusRep":
                pre = Integer.parseInt(txtRepNum.getText().toString()) + 1;
                if(pre >= 0) txtRepNum.setText(pre + "");
                break;
            case "minusRep":
                pre = Integer.parseInt(txtRepNum.getText().toString()) - 1;
                if(pre >= 0) txtRepNum.setText(pre + "");
                break;
            case "plusTime":
                pre = Integer.parseInt(txtTime.getText().toString()) + 1;
                if(pre >= 0) txtTime.setText(pre + "");
                break;
            case "minusTime":
                pre = Integer.parseInt(txtTime.getText().toString()) - 1;
                if(pre >= 0) txtTime.setText(pre + "");
                break;
        }
    }
}
