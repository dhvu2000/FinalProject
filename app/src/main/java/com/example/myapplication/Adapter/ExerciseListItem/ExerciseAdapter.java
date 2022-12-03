package com.example.myapplication.Adapter.ExerciseListItem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.Model.WorkOutUnit.Exercise;
import com.example.myapplication.R;
import com.example.myapplication.Views.ExerciseScreen.AddExerciseDialog;
import com.example.myapplication.Views.ExerciseScreen.DetailExerciseDialog;
import com.example.myapplication.Views.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseHolder> {

    private List<Exercise> exercises;
    Context context;
    boolean longClicked = false;




    public ExerciseAdapter(List<Exercise> exercises, Context context) {
        this.exercises = exercises;
        this.context = context;
    }

    @NonNull
    @Override
    public ExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_exercise_item,parent,false);

        return new ExerciseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        if(exercise.getImg()!=null && !exercise.getImg().isEmpty())
        {
            Glide.with(context).asBitmap().centerCrop().load(exercise.getImg()).error(R.drawable.add_image).into(holder.img);
        }else
        {
            Picasso.get().load(R.drawable.add_image).into(holder.img);
        }
        if(exercise.getName()!= null)holder.txtName.setText(exercise.getName());
        String shortIntroduction = exercise.getIntroduction();
        if(shortIntroduction != null && shortIntroduction.length() > 60)
        {
            shortIntroduction = shortIntroduction.substring(0,59);
        }
        holder.txtIntroduction.setText(shortIntroduction);


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailExerciseDialog detailExerciseDialog = new DetailExerciseDialog(exercise);
                detailExerciseDialog.show(((AppCompatActivity)context).getSupportFragmentManager(),"AddExerciseDialog");
            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                openDeleteAlertDialog(holder.getAdapterPosition());
                longClicked = true;
                return false;
            }
        });

//        holder.cardView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if(longClicked){
//                    //Do whatever you want here!!
//                    openAlertDialog();
//                    longClicked = false;
//                }
//                return false;
//            }
//        });


    }

    public void delete(int position)
    {
        exercises.remove(position);
        notifyItemRemoved(position);
    }
    @Override
    public int getItemCount() {
        return exercises.size();
    }

    private void openDeleteAlertDialog(int position)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("What you want to the exercise?");
        builder.setCancelable(true);
        builder.setPositiveButton(
                "Delete",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //delete the exercise
                        ((MainActivity)context).sendDeleteExercisesInExercisePage(position);
                        dialog.dismiss();
                    }
                });
        builder.setNegativeButton(
                "Fix",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Exercise e = exercises.get(position);
                        AddExerciseDialog addExerciseDialog = new AddExerciseDialog(e);
                        addExerciseDialog.show(((AppCompatActivity)context).getSupportFragmentManager(), "AddExerciseDialog");
                    }
                });
        builder.setNeutralButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder.create();
        alert11.show();
    }
}
