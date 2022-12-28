package com.example.myapplication.Adapter.RoutineListItem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.User.Users;
import com.example.myapplication.Model.WorkOutUnit.Routine.Routine;
import com.example.myapplication.Model.WorkOutUnit.Routine.RoutineDay;
import com.example.myapplication.R;
import com.example.myapplication.Supporter.SharePreferenceManager;
import com.example.myapplication.Views.CollectionsScreen.RoutineCollectionScreen.RoutineDetailScreen;
import com.example.myapplication.Views.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineHolder> {

    List<Routine> routines;
    Context context;
    boolean isEditable = true;

    public RoutineAdapter(List<Routine> routines, Context context)
    {
        this.context = context;
        this.routines = routines;
    }

    public RoutineAdapter(List<Routine> routines, Context context, boolean isEditable) {
        this.routines = routines;
        this.context = context;
        this.isEditable = isEditable;
    }

    @NonNull
    @Override
    public RoutineHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_routine_item,parent,false);
        return new RoutineHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoutineHolder holder, int position) {
        Routine routine = routines.get(position);

        if(routine.getImg()!=null && !routine.getImg().isEmpty())
        {
            Picasso.get().load(routine.getImg()).into(holder.img);
        }else
        {
            Picasso.get().load(R.drawable.add_image).into(holder.img);
        }
        if(routine.getName()!= null) holder.txtName.setText(routine.getName());
        holder.txtDaysNum.setText("Số buổi: "+routine.getDayNum());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, RoutineDetailScreen.class);
                intent.putExtra("Routine", routine);
                context.startActivity(intent);
            }
        });

        if(isTrueUser(routine))
        {
            holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(isEditable)
                    {
                        openDeleteAlertDialog(holder.getAdapterPosition());
                    }
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return routines.size();
    }

    private void openDeleteAlertDialog(int position)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Bạn có muốn xóa?");
        builder.setCancelable(true);
        builder.setPositiveButton(
                "Có",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //delete the exercise
                        ((MainActivity)context).sendDeleteRoutineInCollectionPage(position);
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

    public boolean isTrueUser(Routine r)
    {
        Users u = (Users) new SharePreferenceManager(context).getObject("User", Users.class);
        if(u.getId() == r.getCreatedBy().getId())
        {
            return true;
        }
        return false;
    }
}
