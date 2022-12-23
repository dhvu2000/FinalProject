package com.example.myapplication.Views.HomeScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Adapter.RecordListItem.RecordAdapter;
import com.example.myapplication.Model.WorkOutUnit.WorkOutSet.WorkOutRecord;
import com.example.myapplication.R;
import com.example.myapplication.Supporter.SharePreferenceManager;
import com.example.myapplication.Supporter.TimeFormatter;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class RecordScreen extends AppCompatActivity {

    private TextView txtTotalCal, txtPerDay, txtPerWeek, txtPerMonth;
    private RecyclerView recyclerView;
    private Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_record_screen);

        txtTotalCal = findViewById(R.id.txtTotal);
        txtPerMonth = findViewById(R.id.txtPerMonth);
        txtPerDay = findViewById(R.id.txtPerDay);
        txtPerWeek = findViewById(R.id.txtPerWeek);
        recyclerView = findViewById(R.id.recyclerView);
        btnBack = findViewById(R.id.btnBack);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        WorkOutRecord[] data = (WorkOutRecord[]) new SharePreferenceManager(this).getObject("Records",WorkOutRecord[].class);
        ArrayList<WorkOutRecord> records = new ArrayList<>();
        if(data != null)
        {
            records = new ArrayList<>(Arrays.asList(data));
            Collections.reverse(records);
        }
        double totalCal = 0;

        for(WorkOutRecord i : records)
        {
            totalCal += i.getTotalCalories();
        }
        txtTotalCal.setText(String.format("%.1f", totalCal));

        if(records != null && records.size() > 2)
        {
            Date startDate = TimeFormatter.convertToDate(records.get(records.size() - 1).getTime());
            Date endDate = TimeFormatter.convertToDate(records.get(0).getTime());
            long dayNum = TimeUnit.DAYS.convert(endDate.getTime() - startDate.getTime(), TimeUnit.MILLISECONDS);
            if(dayNum > 0)
            {
                double calPerDay = totalCal/dayNum;
                double calPerWeek = totalCal/dayNum * 7;
                double calPerMonth = totalCal/dayNum * 30;
                txtPerDay.setText(String.format("%.1f", calPerDay));
                txtPerWeek.setText(String.format("%.1f", calPerWeek));
                txtPerMonth.setText(String.format("%.1f", calPerMonth));
            }
        }

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        RecordAdapter recordAdapter = new RecordAdapter(records, this);
        recyclerView.setAdapter(recordAdapter);

    }
}