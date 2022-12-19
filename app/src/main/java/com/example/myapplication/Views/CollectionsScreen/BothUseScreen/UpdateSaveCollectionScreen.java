package com.example.myapplication.Views.CollectionsScreen.BothUseScreen;

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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.SetExerciseItem.SetExerciseAdapter;
import com.example.myapplication.Model.WorkOutUnit.Routine.Routine;
import com.example.myapplication.Model.WorkOutUnit.Routine.RoutineDay;
import com.example.myapplication.Model.WorkOutUnit.WorkOutSet.SetExercise;
import com.example.myapplication.Model.WorkOutUnit.WorkOutSet.WorkOutSet;
import com.example.myapplication.R;
import com.example.myapplication.Retrofit.RetrofitApi;
import com.example.myapplication.Retrofit.RoutineApi;
import com.example.myapplication.Retrofit.RoutineDayApi;
import com.example.myapplication.Supporter.RepeatListener;
import com.example.myapplication.Supporter.SharePreferenceManager;
import com.example.myapplication.Views.CollectionsScreen.RoutineCollectionScreen.PickDayDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateSaveCollectionScreen extends AppCompatActivity {

    private Button btnBack, btnAdd, btnPlusRest, btnPlusPre, btnMinusPre, btnMinusRest;
    private ImageButton btnSave, btnDelete;
    private TextView txtName, txtExerciseNum, txtPre, txtRest;
    private RecyclerView recyclerView;
    private WorkOutSet workOutSet;
    private LinearLayout deleteArea;
    private String type;
    private ArrayList<SetExercise> setExercises = new ArrayList<>();
    private ArrayList<Integer> deletedList = new ArrayList<>();
    private RetrofitApi retrofitApi = new RetrofitApi();
    private RoutineDayApi routineDayApi = retrofitApi.getRetrofit().create(RoutineDayApi.class);
    private RoutineApi routineApi = retrofitApi.getRetrofit().create(RoutineApi.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_detail_set_save_screen);
        btnBack = findViewById(R.id.btnBack);
        btnAdd = findViewById(R.id.btnAdd);
        btnPlusPre = findViewById(R.id.btnPlusPre);
        btnPlusRest = findViewById(R.id.btnPlusRest);
        btnMinusPre = findViewById(R.id.btnMinusPre);
        btnMinusRest = findViewById(R.id.btnMinusRest);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        txtName = findViewById(R.id.txtName);
        txtExerciseNum = findViewById(R.id.txtExerciseNum);
        txtPre = findViewById(R.id.txtPreTime);
        txtRest = findViewById(R.id.txtRestTime);
        deleteArea = findViewById(R.id.deleteArea);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        setWorkOutSet();
        populateListView();

        setButtons();

    }

    public void setWorkOutSet(){
        Intent intent = getIntent();
        type = intent.getStringExtra("type");
        if(type != null && type.equals("routine"))
        {
            workOutSet = (RoutineDay) intent.getSerializableExtra("set");
            if(((RoutineDay)workOutSet).getSequence() != 0) txtName.setText("Day " + ((RoutineDay)workOutSet).getSequence());
        }
        else
        {
            workOutSet = (WorkOutSet) intent.getSerializableExtra("set");
            if(workOutSet.getName()!= null && !workOutSet.getName().trim().equals("")) txtName.setText(workOutSet.getName());
        }

        if(workOutSet.getPreTime() < 5) workOutSet.setPreTime(5);
        if(workOutSet.getRestTime() < 5) workOutSet.setRestTime(5);
        txtPre.setText(workOutSet.getPreTime()+"");
        txtRest.setText(workOutSet.getRestTime()+"");

        if(workOutSet.getExercises()!= null && workOutSet.getExercises().size() != 0)
        {
            txtExerciseNum.setText("Number of exercise: "+ workOutSet.getExercises().size());
        }
        else
        {
            workOutSet.setExercises(new ArrayList<>());
        }
        setExercises = (ArrayList<SetExercise>) workOutSet.getExercises();
    }

    private void setButtons()
    {
        if(workOutSet == null || workOutSet.getId() == 0 )
        {
            deleteArea.setVisibility(View.GONE);
        }

        setPlusMinusButton(btnPlusPre,"plusPre");
        setPlusMinusButton(btnMinusPre,"minusPre");
        setPlusMinusButton(btnPlusRest,"plusRest");
        setPlusMinusButton(btnMinusRest,"minusRest");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FindAndAddExerciseDialog findAndAddExerciseDialog = new FindAndAddExerciseDialog();
                findAndAddExerciseDialog.show(getSupportFragmentManager(),"findAndAddExerciseDialog");
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(type != null && type.equals("routine"))
                {
                    int preTime = Integer.parseInt(txtPre.getText().toString());
                    int restTime = Integer.parseInt(txtRest.getText().toString());
                    RoutineDay routineDay = (RoutineDay) workOutSet;
                    //set Pre, Rest
                    routineDay.setPreTime(preTime);
                    routineDay.setRestTime(restTime);
                    openPickDaysDialog(routineDay);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDeleteAlertDialog();
            }
        });

    }

    private void openPickDaysDialog(RoutineDay routineDay)
    {
        Bundle  bundle = new Bundle();
        bundle.putSerializable("routineDay",routineDay);
        bundle.putSerializable("deletedList",deletedList);
        PickDayDialog pickDayDialog = new PickDayDialog();
        pickDayDialog.setArguments(bundle);
        pickDayDialog.show(getSupportFragmentManager(),"pickDayDialog");
    }

    private void setPlusMinusButton(Button btn, String type)
    {
        btn.setOnTouchListener(new RepeatListener(400, 100, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // the code to execute repeatedly
                actionDo(type);
            }
        }));
    }

    private void populateListView()
    {
        sortExercises();
        SetExerciseAdapter adapter = new SetExerciseAdapter(setExercises, this);
        recyclerView.setAdapter(adapter);
    }



    private void actionDo(String type)
    {
        int pre;
        switch (type)
        {
            case "plusRest":
                pre = Integer.parseInt(txtRest.getText().toString()) + 1;
                if(pre >= 5) txtRest.setText(pre + "");
                break;
            case "minusRest":
                pre = Integer.parseInt(txtRest.getText().toString()) - 1;
                if(pre >= 5) txtRest.setText(pre + "");
                break;
            case "plusPre":
                pre = Integer.parseInt(txtPre.getText().toString()) + 1;
                if(pre >= 5) txtPre.setText(pre + "");
                break;
            case "minusPre":
                pre = Integer.parseInt(txtPre.getText().toString()) - 1;
                if(pre >= 5) txtPre.setText(pre + "");
                break;
        }
    }

    public void addSetExercise(SetExercise input) {
        int sequence = 1;
        if(setExercises != null && setExercises.size() > 0)
        {
            sequence = setExercises.get(setExercises.size() - 1).getSequence() + 1;
        }
        input.setSequence(sequence);
        setExercises.add(input);
        recyclerView.getAdapter().notifyDataSetChanged();
        txtExerciseNum.setText("Number of exercise: "+ setExercises.size());
    }

    public void deleteSetExercise(int position)
    {
        SetExercise setExe = setExercises.get(position);
        if(setExe.getId() != 0) deletedList.add(setExe.getId());
        setExercises.remove(position);
        recyclerView.getAdapter().notifyDataSetChanged();
        txtExerciseNum.setText("Số lượng động tác: "+ workOutSet.getExercises().size());

    }

    private void sortExercises()
    {
        Collections.sort(setExercises, new Comparator<SetExercise>() {
            @Override
            public int compare(SetExercise setExercise, SetExercise t1) {
                Integer a = t1.getSequence();
                Integer b = setExercise.getSequence();
                return b.compareTo(a);
            }
        });
    }

    private void openDeleteAlertDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Bạn có thật sự muốn xóa động tác?");
        builder.setCancelable(true);
        builder.setPositiveButton(
                "Có",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //delete the exercise
                        deleteCollection();
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

    private void deleteCollection() {
        if(workOutSet != null && workOutSet.getId() != 0)
        {
            Call<Void> call = routineDayApi.deleteRoutineDayById(workOutSet.getId());
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if(type != null && type.equals("routine"))
                    {
                        Routine routine = ((RoutineDay) workOutSet).getRoutine();
                        for( int i = 0; i < routine.getDays().size() ; i++)
                        {
                            if(routine.getDays().get(i).getId() == workOutSet.getId())
                            {
                                routine.getDays().remove(i);
                            }
                        }
                        updateRoutine(routine);
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    showNotice("Xóa thất bại");
                }
            });
        }
        else {
            showNotice("Bài tập này chưa tồn tại");
        }

    }

    private void updateRoutine(Routine routine) {
        routine.setDayNum(routine.getDays().get(routine.getDays().size()-1).getSequence());
        new SharePreferenceManager(this).saveRoutine(routine);
        Call<Routine> call  = routineApi.save(routine);
        call.enqueue(new Callback<Routine>() {
            @Override
            public void onResponse(Call<Routine> call, Response<Routine> response) {
                Routine responseBody = response.body();
                finish();
            }

            @Override
            public void onFailure(Call<Routine> call, Throwable t) {
                System.out.println("Chỉnh sửa thất bại: Lỗi mạng!");
                finish();
            }
        });
    }

    private void showNotice(String s)
    {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}