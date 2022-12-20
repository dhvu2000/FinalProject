package com.example.myapplication.Views.CollectionsScreen.RoutineCollectionScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Adapter.RoutineListItem.RoutineAdapter;
import com.example.myapplication.Model.User.Users;
import com.example.myapplication.Model.WorkOutUnit.Routine.Routine;
import com.example.myapplication.R;
import com.example.myapplication.Retrofit.RetrofitApi;
import com.example.myapplication.Retrofit.RoutineApi;
import com.example.myapplication.Supporter.SharePreferenceManager;
import com.example.myapplication.Views.MainActivity;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RoutineSidePage extends Fragment {

    Button btnAdd;
    RecyclerView recyclerView;
    Users users;
    ArrayList<Routine> routines = new ArrayList<>();
    ArrayList<Routine> dbList = new ArrayList<>();
    RetrofitApi retrofitApi = new RetrofitApi();
    RoutineApi routineApi = retrofitApi.getRetrofit().create(RoutineApi.class);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.slide_page_routine_pager,
                container,
                false
        );
        btnAdd = rootView.findViewById(R.id.btnAdd);
        recyclerView = rootView.findViewById(R.id.routineList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        users = (Users) (new SharePreferenceManager(getActivity())).getObject("User",Users.class);



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddRoutineDialog addRoutineDialog = new AddRoutineDialog();
                addRoutineDialog.show(getParentFragmentManager(),"AddRoutineDialog");
            }
        });

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        setRoutineList();
    }

    private void setRoutineList()
    {
        Routine[] data = (Routine[]) new SharePreferenceManager(getActivity()).getObject("Routines", Routine[].class);
        ArrayList<Routine> responseBody = new ArrayList<>();
        if(data != null && data.length != 0) responseBody = new ArrayList<>(Arrays.asList(data));
        dbList.clear();
        dbList.addAll(responseBody);
        if(dbList != null)
        {
            routines = responseBody;
            populateListView(routines);
        }
    }

    private void populateListView(ArrayList<Routine> list)
    {
        RoutineAdapter adapter = new RoutineAdapter(list, getActivity());
        recyclerView.setAdapter(adapter);
    }

    public void deleteRoutine(int position)
    {
        Routine r = routines.get(position);
        //delete in db
        Call<Void> call = routineApi.deleteRoutine(r.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                for(int i = 0; i < dbList.size(); i++)
                {
                    if(dbList.get(i).getId() == r.getId())
                    {
                        dbList.remove(i);
                    }
                }
                new SharePreferenceManager(getActivity()).deleteRoutine(r.getId());
                routines.remove(position);
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println(t.getMessage());
                showNotice("Xóa lỗi: lỗi mạng");
            }
        });
    }

    public void listenToKeyChange(String s)
    {
        System.out.println("Rountine page "+ s);
    }

    public void showNotice(String s)
    {
        ((MainActivity)getActivity()).showNotice(s);
    }


}
