package com.example.myapplication.Views.CollectionsScreen.RoutineCollectionScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.RoutineDayListItem.RoutineDayAdapter;
import com.example.myapplication.Model.WorkOutUnit.Routine.Routine;
import com.example.myapplication.Model.WorkOutUnit.Routine.RoutineDay;
import com.example.myapplication.R;
import com.example.myapplication.Retrofit.RetrofitApi;
import com.example.myapplication.Retrofit.RoutineApi;
import com.example.myapplication.Retrofit.RoutineDayApi;
import com.example.myapplication.Supporter.SharePreferenceManager;
import com.example.myapplication.Views.CollectionsScreen.BothUseScreen.UpdateSaveCollectionScreen;
import com.example.myapplication.Views.MainActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoutineDetailScreen extends AppCompatActivity {


    Routine routine;
    Button btnBack, btnAdd;
    ImageButton btnDelete;
    TextView txtName, txtDaysNum;
    RecyclerView recyclerView;
    RetrofitApi retrofitApi = new RetrofitApi();
    RoutineDayApi routineDayApi = retrofitApi.getRetrofit().create(RoutineDayApi.class);
    RoutineApi routineApi = retrofitApi.getRetrofit().create(RoutineApi.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_routine_detail_screen);
        btnBack = findViewById(R.id.btnBack);
        btnAdd = findViewById(R.id.btnAdd);
        btnDelete = findViewById(R.id.btnDelete);
        txtName = findViewById(R.id.txtName);
        txtDaysNum = findViewById(R.id.txtDaysNum);

        recyclerView = findViewById(R.id.routineDayList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RoutineDay routineDay = new RoutineDay("",routine.getCreatedBy(),MainActivity.getRandomImg(),0,0,null, "routine", 0);
                routineDay.setRoutine(routine);
                Intent intent = new Intent(RoutineDetailScreen.this, UpdateSaveCollectionScreen.class);
                intent.putExtra("type","routine");
                //remember to put Routine inside Routine Day
                intent.putExtra("set",routineDay);
                startActivity(intent);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDeleteAlertDialog();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setRoutine();
        sortDays();
        populateRoutineDaysList(routine.getDays());
    }

    public void populateRoutineDaysList(List<RoutineDay> list)
    {
        if(list != null)
        {
            RoutineDayAdapter adapter = new RoutineDayAdapter(list, routine,this );
            recyclerView.setAdapter(adapter);
        }
    }

    public void setRoutine()
    {
        try{
            routine = (Routine) getIntent().getSerializableExtra("Routine");
            routine = new SharePreferenceManager(this).getRoutine(routine.getId());
            if(routine.getDays() == null) routine.setDays(new ArrayList<>());
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        if(routine != null)
        {
            txtName.setText(routine.getName());
            txtDaysNum.setText("Days: "+routine.getDayNum());
        }
    }

    public void sortDays(){
        if(routine.getDays() != null && routine.getDays().size() != 0)
        {
            Collections.sort(routine.getDays(), new Comparator<RoutineDay>() {
                @Override
                public int compare(RoutineDay routineDay, RoutineDay t1) {
                    Integer a = routineDay.getSequence();
                    Integer b = t1.getSequence();
                    return a.compareTo(b);
                }
            });
        }
    }

    public void deleteRoutineDay(int position)
    {
        RoutineDay deleteOne = routine.getDays().get(position);
        Call<Void> call = routineDayApi.deleteRoutineDayById(deleteOne.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                routine.getDays().remove(position);
                recyclerView.getAdapter().notifyDataSetChanged();
                updateRoutine();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showNotice("Delete Fail: Network Error");
            }
        });
    }

    private void updateRoutine() {
        if(routine.getDays() == null || routine.getDays().size() == 0) routine.setDayNum(0);
        else routine.setDayNum(routine.getDays().get(routine.getDays().size()-1).getSequence());
        txtDaysNum.setText("Số ngày: "+routine.getDayNum());
        new SharePreferenceManager(RoutineDetailScreen.this).saveRoutine(routine);
        Call<Routine> call  = routineApi.save(routine);
        call.enqueue(new Callback<Routine>() {
            @Override
            public void onResponse(Call<Routine> call, Response<Routine> response) {
                Routine responseBody = response.body();
            }

            @Override
            public void onFailure(Call<Routine> call, Throwable t) {
                System.out.println("Update Fail: Network Error");
            }
        });
    }

    private void openDeleteAlertDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có muốn xóa?");
        builder.setCancelable(true);
        builder.setPositiveButton(
                "Có",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //delete the exercise
                        deleteRoutine();
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

    private void deleteRoutine()
    {
        Call<Void> call = routineApi.deleteRoutine(routine.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                new SharePreferenceManager(RoutineDetailScreen.this).deleteRoutine(routine.getId());
                finish();
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println(t.getMessage());
                showNotice("Xóa thất bại: Lỗi mạng!");
            }
        });
    }


    public  void showNotice(String s)
    {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }


}