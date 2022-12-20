package com.example.myapplication.Adapter.SetListItem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.WorkOutUnit.WorkOutSet.WorkOutSet;
import com.example.myapplication.R;
import com.example.myapplication.Views.CollectionsScreen.BothUseScreen.DetailCollectionScreen;
import com.example.myapplication.Views.CollectionsScreen.RoutineCollectionScreen.RoutineDetailScreen;
import com.example.myapplication.Views.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SetAdapter extends RecyclerView.Adapter<SetHolder> {


    private List<WorkOutSet> workOutSets;
    private Context context;

    public SetAdapter(List<WorkOutSet> workOutSets, Context context)
    {
        this.workOutSets = workOutSets;
        this.context = context;
    }

    @NonNull
    @Override
    public SetHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_set_item,parent,false);

        return new SetHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SetHolder holder, int position) {
        WorkOutSet workOutSet = workOutSets.get(position);

        if(workOutSet.getImg()!=null && !workOutSet.getImg().isEmpty())
        {
            Picasso.get().load(workOutSet.getImg()).into(holder.img);
        }else
        {
            Picasso.get().load(R.drawable.add_image).into(holder.img);
        }

        holder.txtName.setText(workOutSet.getName());
        holder.txtExerciseNum.setText(workOutSet.getExercises().size()+ " động tác");

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailCollectionScreen.class);
                intent.putExtra("type","single");
                //remember to put Routine inside Routine Day
                intent.putExtra("set",workOutSet);
                context.startActivity(intent);
            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                openDeleteAlertDialog(holder.getAdapterPosition());
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return workOutSets.size();
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
                        ((MainActivity)context).sendDeleteSetInCollectionPage(position);
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
