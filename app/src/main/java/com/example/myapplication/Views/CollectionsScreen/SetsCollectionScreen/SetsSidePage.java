package com.example.myapplication.Views.CollectionsScreen.SetsCollectionScreen;

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
import com.example.myapplication.Adapter.SetListItem.SetAdapter;
import com.example.myapplication.Model.User.Users;
import com.example.myapplication.Model.WorkOutUnit.Routine.Routine;
import com.example.myapplication.Model.WorkOutUnit.WorkOutSet.WorkOutSet;
import com.example.myapplication.R;
import com.example.myapplication.Retrofit.RetrofitApi;
import com.example.myapplication.Retrofit.WorkOutSetApi;
import com.example.myapplication.Supporter.SharePreferenceManager;
import com.example.myapplication.Views.CollectionsScreen.RoutineCollectionScreen.AddRoutineDialog;
import com.example.myapplication.Views.MainActivity;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetsSidePage extends Fragment {

    Button btnAdd;
    RecyclerView recyclerView;
    Users users;
    ArrayList<WorkOutSet> workOuSets = new ArrayList<>();
    ArrayList<WorkOutSet> dbList = new ArrayList<>();
    RetrofitApi retrofitApi = new RetrofitApi();
    WorkOutSetApi workOutSetApi = retrofitApi.getRetrofit().create(WorkOutSetApi.class);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.slide_page_set_pager,
                container,
                false
        );

        btnAdd = rootView.findViewById(R.id.btnAdd);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        users = (Users) (new SharePreferenceManager(getActivity())).getObject("User",Users.class);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 AddSetDialog addSetDialog = new AddSetDialog();
                addSetDialog.show(getParentFragmentManager(),"AddSetDialog");
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        setWorkOutSetList();
    }

    private void setWorkOutSetList()
    {
        WorkOutSet[] data = (WorkOutSet[]) new SharePreferenceManager(getActivity()).getObject("Sets", WorkOutSet[].class);
        ArrayList<WorkOutSet> responseBody = new ArrayList<>();
        if(data != null && data.length != 0) responseBody = new ArrayList<>(Arrays.asList(data));
        dbList.clear();
        dbList.addAll(responseBody);
        if(dbList != null)
        {
            workOuSets = responseBody;
            populateListView(workOuSets);
        }
    }

    private void populateListView(ArrayList<WorkOutSet> list)
    {
        SetAdapter adapter = new SetAdapter(list, getActivity());
        recyclerView.setAdapter(adapter);
    }

    public void deleteSet(int position)
    {
        WorkOutSet w = workOuSets.get(position);
        //delete in db
        Call<Void> call = workOutSetApi.deleteWorkOutSet(w.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                for(int i = 0; i < dbList.size(); i++)
                {
                    if(dbList.get(i).getId() == w.getId())
                    {
                        dbList.remove(i);
                    }
                }
                new SharePreferenceManager(getActivity()).deleteWorkOutSet(w.getId());
                workOuSets.remove(position);
                recyclerView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println(t.getMessage());
                showNotice("Xóa lỗi: Lỗi mạng");
            }
        });
    }

    public  void listenToKeyChange(String s)
    {
//        System.out.println("Sets Side "+s);
    }

    public void showNotice(String s)
    {
        ((MainActivity)getActivity()).showNotice(s);
    }
}
