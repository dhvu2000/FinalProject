package com.example.myapplication.Views.CollectionsScreen.RoutineCollectionScreen;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.ChosenDayListItem.ChosenDayAdapter;
import com.example.myapplication.Model.WorkOutUnit.Exercise;
import com.example.myapplication.Model.WorkOutUnit.Routine.Routine;
import com.example.myapplication.Model.WorkOutUnit.Routine.RoutineDay;
import com.example.myapplication.Model.WorkOutUnit.WorkOutSet.SetExercise;
import com.example.myapplication.Model.WorkOutUnit.WorkOutSet.WorkOutSet;
import com.example.myapplication.R;
import com.example.myapplication.Retrofit.RetrofitApi;
import com.example.myapplication.Retrofit.RoutineApi;
import com.example.myapplication.Retrofit.RoutineDayApi;
import com.example.myapplication.Retrofit.SetExerciseApi;
import com.example.myapplication.Supporter.SharePreferenceManager;
import com.example.myapplication.Views.MainActivity;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PickDayDialog extends DialogFragment {

    private EditText txtPickDay;
    private TextView txtNotice;
    private Button btnPick;
    private Button btnDone;
    private RecyclerView recyclerView;
    private ArrayList<Integer> pickDays = new ArrayList<>();
    private RoutineDay savedRoutineDay;
    private ArrayList<Integer> availableDays = new ArrayList<>();
    RetrofitApi retrofitApi =  new RetrofitApi();
    RoutineApi routineApi = retrofitApi.getRetrofit().create(RoutineApi.class);
    RoutineDayApi routineDayApi = retrofitApi.getRetrofit().create(RoutineDayApi.class);
    SetExerciseApi setExerciseApi = retrofitApi.getRetrofit().create(SetExerciseApi.class);
    Routine routine;
    ArrayList<RoutineDay> savedRoutineDays = new ArrayList<>();
    ArrayList<Integer> deletedList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View  view = inflater.inflate(R.layout.dialog_pick_repetation_day,container,false);

        txtPickDay = view.findViewById(R.id.txtPickDay);
        txtNotice = view.findViewById(R.id.txtNotice);
        btnPick = view.findViewById(R.id.btnPick);
        recyclerView = view.findViewById(R.id.recyclerView);
        savedRoutineDay = (RoutineDay) getArguments().getSerializable("routineDay");
        deletedList = (ArrayList<Integer>) getArguments().getSerializable("deletedList");
        btnDone = view.findViewById(R.id.btnDone);



        //get available days
        if(savedRoutineDay != null)
        {
            try {
                routine = (Routine) savedRoutineDay.getRoutine().clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            savedRoutineDay.setRoutine(null);
            if(routine != null)
            {
                if(routine.getDays() != null)
                {
                    for(RoutineDay i: routine.getDays())
                    {
                        availableDays.add(i.getSequence());
                    }
                }
            }
        }


        LinearLayoutManager layoutManager
                = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        ChosenDayAdapter adapter = new ChosenDayAdapter(pickDays, availableDays, getActivity());
        recyclerView.setAdapter(adapter);

        btnPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer a = Integer.parseInt(txtPickDay.getText().toString());
                if(isDayAvailable(a))
                {
                    showNotice("The day is occupied");
                    txtPickDay.setText("");
                }
                else
                {
                    pickDays.add(a);
                    txtPickDay.setText("");
                    availableDays.add(a);
                    Collections.sort(pickDays);
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(savedRoutineDay.getId() == 0 && pickDays.size() == 0)
                {
                    showNotice("Choose a day, if you want to save");
                    return;
                }
                beforeSave();
                //save to DB
                saveProcess();
                //

            }
        });


        return  view;
    }

    private void beforeSave()
    {
        ArrayList<SetExercise> cloneExercises = new ArrayList<>();
        if(savedRoutineDay.getId() != 0)
        {
            savedRoutineDays.add(savedRoutineDay);
        }
        if(savedRoutineDay.getExercises() != null)
        {
            ArrayList<SetExercise> exs = (ArrayList<SetExercise>) savedRoutineDay.getExercises();
            for(int i = 0; i < exs.size(); i++)
            {
                SetExercise newexs = new SetExercise(exs.get(i).getTimeLength(),
                        exs.get(i).getRepNum(),exs.get(i).getSequence(), exs.get(i).getExercise());
                cloneExercises.add(newexs);
            }
        }

        for(int i: pickDays)
        {
            RoutineDay newDay = new RoutineDay("Day "+i + " of " + routine.getName(),routine.getCreatedBy(),R.drawable.add_image+"",
                    savedRoutineDay.getPreTime(),savedRoutineDay.getRestTime(),cloneExercises, savedRoutineDay.getType(), i);
            savedRoutineDays.add(newDay);
        }
        if(availableDays != null && availableDays.size() > 0)
        {
            Collections.sort(availableDays);
            routine.setDayNum(availableDays.get(availableDays.size()-1));
        }
    }

    private void saveProcess()
    {
        Call<Routine> call = routineApi.save(routine);
        call.enqueue(new Callback<Routine>() {
            @Override
            public void onResponse(Call<Routine> call, Response<Routine> response) {
                    Routine responseBody = response.body();
                    routine = responseBody;
                    for(RoutineDay i : savedRoutineDays)
                    {
                        i.setRoutine(routine);
                    }
                    if(deletedList != null && deletedList.size() > 0)
                    {
                        deleteSetExercises();
                    }
                    else {
                        saveSavedRoutineDays();
                    }
            }

            @Override
            public void onFailure(Call<Routine> call, Throwable t) {
                showNotice("Save Routine Day Fail: Network Error");
                System.out.println(t.getMessage());
            }
        });
    }


    private void deleteSetExercises()
    {
        String nums = "";
        for(int i : deletedList)
        {
            nums += (i+",");
        }
        Call<Void> call = setExerciseApi.deleteAllSetExerciseById(nums);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                System.out.println("DELETE SUCCESS");
                saveSavedRoutineDays();;
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showNotice("Save Routine Day Fail: Network Error");
                System.out.println(t.getMessage());
            }
        });
    }

    private void saveSavedRoutineDays() {
        Call<ArrayList<RoutineDay>> call = routineDayApi.saveAll(savedRoutineDays);
        call.enqueue(new Callback<ArrayList<RoutineDay>>() {
            @Override
            public void onResponse(Call<ArrayList<RoutineDay>> call, Response<ArrayList<RoutineDay>> response) {
                savedRoutineDays = response.body();
                afterSave();
            }

            @Override
            public void onFailure(Call<ArrayList<RoutineDay>> call, Throwable t) {
                showNotice("Save Routine Day Fail: Network Error");
                System.out.println(t.getMessage());
            }
        });
    }


    private void afterSave()
    {
        updateDaysToRoutine();
        updateRoutineToSharedPreferenece(routine);
        getActivity().finish();
        dismiss();
    }

    private void updateDaysToRoutine()
    {
        for(RoutineDay i : savedRoutineDays)
        {
            if(pickDays.contains(i.getSequence())) routine.getDays().add(i);
            else
            {
                for(int j =  0; j < routine.getDays().size(); j++)
                {
                    if(routine.getDays().get(j).getId() == i.getId() )
                    {
                        routine.getDays().set(j,i);
                    }
                }
            }
        }
    }

    private void updateRoutineToSharedPreferenece(Routine routine) {
        boolean check = new SharePreferenceManager(getActivity()).saveRoutine(routine);
        System.out.println("Save Routine Result: "+ check);
    }

    private boolean isDayAvailable(int day)
    {
        if(availableDays != null)
        {
            for(int i: availableDays)
            {
                if(day == i)
                {
                    return true;
                }
            }
        }
        return false;
    }

    private void showNotice(String s)
    {
        if(txtNotice != null)
        {
            txtNotice.setText(s);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 5s = 5000ms
                    if(txtNotice != null)
                    {
                        txtNotice.setText("");
                    }
                }
            }, 2000);
        }
    }

}
