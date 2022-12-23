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
import com.example.myapplication.Model.WorkOutUnit.Routine.RoutineAct;
import com.example.myapplication.Model.WorkOutUnit.Routine.RoutineDay;
import com.example.myapplication.R;
import com.example.myapplication.Retrofit.RetrofitApi;
import com.example.myapplication.Retrofit.RoutineApi;
import com.example.myapplication.Retrofit.RoutineDayApi;
import com.example.myapplication.Supporter.SharePreferenceManager;
import com.example.myapplication.Views.CollectionsScreen.BothUseScreen.UpdateSaveCollectionScreen;
import com.example.myapplication.Views.MainActivity;

import java.util.ArrayList;
import java.util.Arrays;
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
        ArrayList<RoutineDay> renderList = new ArrayList<>();
        int sequenceTrace = 1;
        int pos = 0;

        if(list != null)
        {
            while(pos < list.size())
            {
                RoutineDay rd = list.get(pos);
                if(sequenceTrace == rd.getSequence())
                {
                    renderList.add(rd);
                    pos ++;
                }
                else
                {
                    RoutineDay tmp = new RoutineDay();
                    tmp.setSequence(sequenceTrace);
                    renderList.add(tmp);
                }
                sequenceTrace ++;
            }
        }
        RoutineDayAdapter adapter = new RoutineDayAdapter(renderList, routine,getCurrentSequence(),this );
        recyclerView.setAdapter(adapter);
        ((LinearLayoutManager)recyclerView.getLayoutManager()).scrollToPositionWithOffset(getCurrentSequence()-1,70);
    }

    public int getCurrentSequence()
    {
        int sequence = 0;
        int reachedSequence = 0;
        if(routine.getDays() != null && routine.getDays().size() > 0)
        {
            sequence = routine.getDays().get(0).getSequence();
        }
        RoutineAct[] data = (RoutineAct[]) new SharePreferenceManager(RoutineDetailScreen.this).getObject("Progress", RoutineAct[].class);
        if(data !=  null && routine.getDays().size() > 0)
        {
            ArrayList<RoutineAct> progress = new ArrayList<>(Arrays.asList(data));
            for(int i = progress.size() - 1; i >= 0; i--)
            {
                if(progress.get(i).getRoutine().getId() == routine.getId())
                {
                    reachedSequence = progress.get(i).getProgress();
                    break;
                }
            }

            for(RoutineDay i : routine.getDays())
            {
                if(i.getSequence() > reachedSequence)
                {
                    sequence = i.getSequence();
                    break;
                }
            }
        }
        return sequence;
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
            txtDaysNum.setText("Số buổi: "+routine.getDayNum());
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


    public void deleteRoutineDay(int dayID)
    {
        Call<Void> call = routineDayApi.deleteRoutineDayById(dayID);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                for(int i = 0 ; i < routine.getDays().size(); i++)
                {
                    if(routine.getDays().get(i).getId() == dayID)
                    {
                        routine.getDays().remove(i);
                    }
                }
                sortDays();
                populateRoutineDaysList(routine.getDays());
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
        txtDaysNum.setText("Số buổi: "+routine.getDayNum());
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