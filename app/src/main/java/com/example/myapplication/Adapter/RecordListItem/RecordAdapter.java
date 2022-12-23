package com.example.myapplication.Adapter.RecordListItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.FindAndAddExerciseItem.FindAndAddExerciseHolder;
import com.example.myapplication.Model.WorkOutUnit.WorkOutSet.WorkOutRecord;
import com.example.myapplication.R;
import com.example.myapplication.Supporter.TimeFormatter;

import java.util.List;

public class RecordAdapter extends RecyclerView.Adapter<RecordHolder> {

    private List<WorkOutRecord> records;
    private Context context;

    public RecordAdapter(List<WorkOutRecord> records, Context context) {
        this.records = records;
        this.context = context;
    }

    @NonNull
    @Override
    public RecordHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_record_item,parent,false);

        return new RecordHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecordHolder holder, int position) {
        WorkOutRecord record = records.get(position);

        String calories = String.format("%.1f", record.getTotalCalories());
        String spentTime = TimeFormatter.FormatToHourTime(record.getLength());

        holder.txtSetName.setText(record.getWorkOutSet().getName());
        holder.txtCalories.setText(calories+ " calo");
        holder.txtSpentTime.setText(spentTime);
        holder.txtDateTime.setText(record.getTime());

    }

    @Override
    public int getItemCount() {
        return records.size();
    }
}
