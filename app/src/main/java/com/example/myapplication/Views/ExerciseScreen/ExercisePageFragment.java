package com.example.myapplication.Views.ExerciseScreen;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Adapter.ExerciseListItem.ExerciseAdapter;
import com.example.myapplication.Model.User.Users;
import com.example.myapplication.Model.WorkOutUnit.Exercise;
import com.example.myapplication.R;
import com.example.myapplication.Retrofit.ExerciseApi;
import com.example.myapplication.Retrofit.RetrofitApi;
import com.example.myapplication.Retrofit.UsersApi;
import com.example.myapplication.Supporter.SharePreferenceManager;
import com.example.myapplication.Views.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExercisePageFragment extends Fragment{



    Button btnAddExercise;
    EditText txtSearch;
    RecyclerView recyclerView;
    RetrofitApi retrofitApi = new RetrofitApi();
    ExerciseApi exerciseApi = retrofitApi.getRetrofit().create(ExerciseApi.class);
    ArrayList<Exercise> dbList = new ArrayList<>();
    ArrayList<Exercise> exercises = new ArrayList<>();

    public ExercisePageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercise_page, container, false);
        txtSearch = view.findViewById(R.id.txtSearch);
        btnAddExercise = view.findViewById(R.id.btnAddExercise);
        recyclerView = view.findViewById(R.id.exerciseList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        btnAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddExerciseDialog addExerciseDialog = new AddExerciseDialog();
                addExerciseDialog.show(getParentFragmentManager(),"AddExerciseDialog");
            }
        });

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filterExercise();
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        setExerciseList();
    }

    private void setExerciseList()
    {
        Exercise[] data = (Exercise[]) new SharePreferenceManager(getActivity()).getObject("Exercises", Exercise[].class);
        if(data != null)
        {
            ArrayList<Exercise> responseBody = new ArrayList<>(Arrays.asList(data));
            dbList.clear();
            dbList.addAll(responseBody);
            exercises = responseBody;
        }
        populateListView(exercises);
    }

    private void populateListView(ArrayList<Exercise> list)
    {
        ExerciseAdapter adapter = new ExerciseAdapter(list, getActivity());
        recyclerView.setAdapter(adapter);
    }

    public void sendChangedNotify() {
        setExerciseList();
        filterExercise();
    }

    public void deleteExercise(int position)
    {
        Exercise deletedExercise = exercises.get(position);
        Call<Void> call = exerciseApi.deleteExerciseById(deletedExercise.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                ((MainActivity)getActivity()).deleteExercise();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

    }

    private void filterExercise()
    {
        if(txtSearch != null)
        {
            String key = txtSearch.getText().toString();
            exercises.clear();
            for(Exercise i: dbList)
            {
                if(i.getName().toLowerCase().contains(key.toLowerCase()))
                {
                    exercises.add(i);
                }
            }
            recyclerView.getAdapter().notifyDataSetChanged();
        }
    }

    private void showNotice(String s)
    {
        if(getActivity()!= null)
             Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
    }


}