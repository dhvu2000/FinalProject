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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExercisePageFragment extends Fragment{



    Button btnAddExercise;
    EditText txtSearch;
    RecyclerView recyclerView;
    Users user;
    private StorageReference mStorageRef;
    RetrofitApi retrofitApi = new RetrofitApi();
    ExerciseApi exerciseApi = retrofitApi.getRetrofit().create(ExerciseApi.class);
    String uploadUrl;
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
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        txtSearch = view.findViewById(R.id.txtSearch);
        btnAddExercise = view.findViewById(R.id.btnAddExercise);
        recyclerView = view.findViewById(R.id.exerciseList_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        btnAddExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle  bundle = new Bundle();
                bundle.putSerializable("users",user);
                AddExerciseDialog addExerciseDialog = new AddExerciseDialog();
                addExerciseDialog.setArguments(bundle);
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

        user = (Users) (new SharePreferenceManager(getActivity())).getObject("User",Users.class);
        setExerciseList();
        return view;
    }

    private void setExerciseList()
    {
        int id = 0;
        if(user != null){
            id = user.getId();
        }
        Call<ArrayList<Exercise>> call = exerciseApi.getExercisesByUserId(id);
        call.enqueue(new Callback<ArrayList<Exercise>>() {
            @Override
            public void onResponse(Call<ArrayList<Exercise>> call, Response<ArrayList<Exercise>> response) {
                if(response.body()!= null && response.body().size()>0)
                {
                    ArrayList<Exercise> responseBody = response.body();
                    dbList.clear();
                    dbList.addAll(responseBody);
                    exercises = responseBody;
                }
                System.out.println(exercises.size());
                populateListView(exercises);
            }

            @Override
            public void onFailure(Call<ArrayList<Exercise>> call, Throwable t) {
                System.out.println("fail" + t.getMessage());
                showNotice(t.getMessage());
            }
        });
    }

    private void populateListView(ArrayList<Exercise> list)
    {
        ExerciseAdapter adapter = new ExerciseAdapter(list, getActivity());
        recyclerView.setAdapter(adapter);
    }

    public void sendInput(Exercise input) {

        System.out.println("main screen: "+input);
        saveExercise(input);
    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void saveExercise(Exercise exercise)
    {
        Uri uri = null;
        if(exercise.getImg() != null && !exercise.getImg().trim().equals(""))
        {
            uri = Uri.parse(exercise.getImg());
            StorageReference fileReference = mStorageRef.child("exercise/"+
                    System.currentTimeMillis()
                    +"."+getFileExtension(uri));
            fileReference.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            System.out.println("Upload img successfully");
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful());//wait for the task done;
                            Uri downloadUrl = urlTask.getResult();
                            System.out.println(downloadUrl);
                            uploadUrl = downloadUrl.toString();
                            if(!uploadUrl.equals(null) && !uploadUrl.equals(""))
                            {
                                exercise.setImg(uploadUrl);
                                saveExerciseToDB(exercise);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            System.out.println("Upload img fail");
                        }
                    });
        }
        else
        {
            saveExerciseToDB(exercise);
        }
    }

    private void saveExerciseToDB(Exercise exercise)
    {
        System.out.println("1 "+exercise);
        Call<Exercise> saveExerciseCall = exerciseApi.save(exercise);
        saveExerciseCall.enqueue(new Callback<Exercise>() {
            @Override
            public void onResponse(Call<Exercise> call, Response<Exercise> response) {
                if(response.body() != null)
                {
                    dbList.add(response.body());
                    filterExercise();
                }
                showNotice("new exercise added");
            }

            @Override
            public void onFailure(Call<Exercise> call, Throwable t) {
                System.out.println(t.toString());
                showNotice(t.getMessage());
            }
        });
    }

    public void deleteExercise(int position)
    {
        Exercise deletedExercise = exercises.get(position);
        Call<Void> call = exerciseApi.deleteExerciseById(deletedExercise.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                for(int i = 0; i< dbList.size(); i++)
                {
                    if(dbList.get(i).getId() == deletedExercise.getId())
                    {
                        dbList.remove(i);
                    }
                }
                filterExercise();
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
                if(i.getName().contains(key))
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