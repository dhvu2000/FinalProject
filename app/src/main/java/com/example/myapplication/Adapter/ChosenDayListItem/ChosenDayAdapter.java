package com.example.myapplication.Adapter.ChosenDayListItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.ExerciseListItem.ExerciseHolder;
import com.example.myapplication.R;

import java.util.List;

public class ChosenDayAdapter extends RecyclerView.Adapter<ChosenDayHolder> {

    private List<Integer> list;
    private List<Integer> connectedList;
    private Context context;

    public ChosenDayAdapter(List<Integer> list, List<Integer> connectedList, Context context) {
        this.list = list;
        this.context = context;
        this.connectedList = connectedList;
    }

    @NonNull
    @Override
    public ChosenDayHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_chosen_day_item,parent,false);



        return new ChosenDayHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChosenDayHolder holder, int position) {
        Integer day = list.get(position);
        holder.txtDay.setText(day+"");
        holder.txtDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                connectedList.remove(day);
                list.remove(day);
                notifyDataSetChanged();
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
