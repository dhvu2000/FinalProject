package com.example.myapplication.Adapter.RoutineDayListItem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.myapplication.Model.WorkOutUnit.Routine.Routine;
import com.example.myapplication.Model.WorkOutUnit.Routine.RoutineDay;
import com.example.myapplication.R;
import com.example.myapplication.Views.CollectionsScreen.BothUseScreen.DetailCollectionScreen;
import com.example.myapplication.Views.CollectionsScreen.RoutineCollectionScreen.RoutineDetailScreen;

import java.util.List;

public class RoutineDayAdapter extends RecyclerView.Adapter<RoutineDayHolder> {

    List<RoutineDay> routineDays;
    Routine routine;
    Context context;
    int currentSequence;

    public RoutineDayAdapter(List<RoutineDay> routineDays, Routine routine, int currentSequence, Context context) {
        this.routineDays = routineDays;
        this.routine = routine;
        this.context = context;
        this.currentSequence = currentSequence;
    }

    @NonNull
    @Override
    public RoutineDayHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_routine_day_item,parent,false);

        return new RoutineDayHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineDayHolder holder, int position) {
        RoutineDay routineDay = routineDays.get(position);

        if(routineDay.getExercises() ==  null || routineDay.getExercises().size() == 0)
        {
            holder.txtDay.setText("Buổi "+routineDay.getSequence() + ": NGHỈ NGƠI");
            holder.txtExerciseNum.setText("");
        }
        else
        {
            if(routineDay.getSequence() == currentSequence)
            {
                holder.cardView.setBackgroundResource(R.drawable.rounded_shinning);
            }
            holder.txtDay.setText("Buổi "+routineDay.getSequence());

            int exerciseNum = 0;
            if(routineDay.getExercises() != null)
            {
                exerciseNum = routineDay.getExercises().size();
            }
            holder.txtExerciseNum.setText(exerciseNum +" động tác");
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    routineDay.setRoutine(routine);
                    Intent intent = new Intent(context, DetailCollectionScreen.class);
                    intent.putExtra("type","routine");
                    //remember to put Routine inside Routine Day
                    intent.putExtra("set",routineDay);
                    context.startActivity(intent);
                }
            });

            holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    openDeleteAlertDialog(routineDay.getId());
                    return false;
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return routineDays.size();
    }

    private void openDeleteAlertDialog(int dayID)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Bạn có muốn xóa");
        builder.setCancelable(true);
        builder.setPositiveButton(
                "Có",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //delete the exercise
                        ((RoutineDetailScreen)context).deleteRoutineDay(dayID);
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton(
                "Không",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder.create();
        alert11.show();
    }
}
