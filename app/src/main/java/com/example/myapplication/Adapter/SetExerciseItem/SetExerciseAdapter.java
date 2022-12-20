package com.example.myapplication.Adapter.SetExerciseItem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.WorkOutUnit.WorkOutSet.SetExercise;
import com.example.myapplication.R;
import com.example.myapplication.Views.CollectionsScreen.BothUseScreen.UpdateSaveCollectionScreen;
import com.example.myapplication.Views.ExerciseScreen.DetailExerciseDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SetExerciseAdapter extends RecyclerView.Adapter<SetExerciseHolder> {

    private List<SetExercise> setExercises = new ArrayList<>();
    private Context context;

    public SetExerciseAdapter(List<SetExercise> setExercises, Context context) {
        this.setExercises = setExercises;
        this.context = context;
    }

    @NonNull
    @Override
    public SetExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_set_exercise_item,parent,false);
        return new SetExerciseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SetExerciseHolder holder, int position) {
        SetExercise setExercise = setExercises.get(position);
        if(setExercise.getExercise().getImg()!=null && !setExercise.getExercise().getImg().isEmpty())
        {
            Picasso.get().load(setExercise.getExercise().getImg()).error(R.drawable.add_image).into(holder.img);
        }else
        {
            Picasso.get().load(R.drawable.add_image).into(holder.img);
        }
        holder.txtName.setText(setExercise.getExercise().getName());
        holder.txtRep.setText("reps: "+setExercise.getRepNum());
        holder.txtTime.setText("số giây:"+ setExercise.getTimeLength());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailExerciseDialog detailExerciseDialog = new DetailExerciseDialog(setExercise.getExercise());
                detailExerciseDialog.show(((AppCompatActivity)context).getSupportFragmentManager(),"DetailExerciseDialog");
            }
        });

        if(context instanceof UpdateSaveCollectionScreen)
        {
            holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    openDeleteAlertDialog(holder.getAdapterPosition());
//                longClicked = true;
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return setExercises.size();
    }

    private void openDeleteAlertDialog(int position)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Do you want to delete the exercise");
        builder.setCancelable(true);
        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //delete the exercise
                        if(context instanceof UpdateSaveCollectionScreen)
                        {
                            ((UpdateSaveCollectionScreen)context).deleteSetExercise(position);
                        }
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder.create();
        alert11.show();
    }
}
