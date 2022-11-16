package com.example.myapplication.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.myapplication.Model.User.Users;
import com.example.myapplication.Model.WorkOutUnit.Exercise;
import com.example.myapplication.Model.WorkOutUnit.Routine.Routine;
import com.example.myapplication.R;
import com.example.myapplication.Retrofit.ExerciseApi;
import com.example.myapplication.Retrofit.RetrofitApi;
import com.example.myapplication.Retrofit.RoutineApi;
import com.example.myapplication.Retrofit.UsersApi;
import com.example.myapplication.Supporter.SharePreferenceManager;
import com.example.myapplication.Views.CollectionsScreen.CollectionsPageFragment;
import com.example.myapplication.Views.ExerciseScreen.AddExerciseDialog;
import com.example.myapplication.Views.ExerciseScreen.ExercisePageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements  AddExerciseDialog.OnInputListener{

    public static String RANDOM_IMG_1 = "https://firebasestorage.googleapis.com/v0/b/gymlife-22ff7.appspot.com/o/uploads%2Fuploads%2FrandomImg%2Frandom.png?alt=media&token=bc61f81e-9582-48fe-86d4-e3074bc77273";
    public static String RANDOM_IMG_2 = "https://firebasestorage.googleapis.com/v0/b/gymlife-22ff7.appspot.com/o/uploads%2Fuploads%2FrandomImg%2Frandom1.png?alt=media&token=a29d0834-5652-4833-9f82-ad492de40df4";
    public static String RANDOM_IMG_3 = "https://firebasestorage.googleapis.com/v0/b/gymlife-22ff7.appspot.com/o/uploads%2Fuploads%2FrandomImg%2Frandom2.png?alt=media&token=5d990c5f-c77e-45f5-ab96-292ee660040e";

    public static String getRandomImg()
    {
        Random rn = new Random();
        int i = rn.nextInt() % 3 + 1;
        if(i == 1)
        {
            return RANDOM_IMG_1;
        }else if(i==2)
        {
            return RANDOM_IMG_2;
        }else
        {
            return RANDOM_IMG_3;
        }

    }
    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    Fragment fragment;
    int chosenItem;
    RetrofitApi retrofitApi = new RetrofitApi();
    Users user;
    UsersApi usersApi = retrofitApi.getRetrofit().create(UsersApi.class);
    ExerciseApi exerciseApi = retrofitApi.getRetrofit().create((ExerciseApi.class));
    RoutineApi routineApi = retrofitApi.getRetrofit().create(RoutineApi.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        frameLayout = findViewById(R.id.frameLayout);

//        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(,-60));
        chosenItem = R.id.btnExercises;
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.btnExercises:
                        if(chosenItem != R.id.btnExercises)
                        {
                            chosenItem = R.id.btnExercises;
                            replaceFragment(new ExercisePageFragment());
                        }
                        break;
                    case R.id.btnHome:
                        break;
                    case R.id.btnTimer:
                        break;
                    case R.id.btnCollections:
                        if(chosenItem != R.id.btnCollections)
                        {
                            chosenItem = R.id.btnCollections;
                            replaceFragment(new CollectionsPageFragment());
                        }
                        break;
                    case R.id.btnAccount:
                        break;
                }

                return true;
            }
        });

        getUser();

    }


    public void getUser()
    {
        Call<Users> usersCall = usersApi.getUserById(8);
        usersCall.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                
                user = response.body();
                boolean isSaveDone = (new SharePreferenceManager(MainActivity.this)).saveObject("User",user);
                if(!isSaveDone)
                {
                    showNotice("Error: Store User Error!!!");
                }
                else
                {
                    getExercises();
                }

            }
            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                showNotice("error:" + t.getMessage());
                System.out.println("error:" + t.getMessage());
            }
        });
    }

    public void getExercises()
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
                    boolean isSaveDone = (new SharePreferenceManager(MainActivity.this)).saveObject("Exercises",responseBody.toArray());
                    if(!isSaveDone)
                    {
                        showNotice("Error: Store Exercises Error!!!");
                    }
                    else {
                        getRoutines();
                    }

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Exercise>> call, Throwable t) {
                System.out.println("fail get Exercises" + t.getMessage());
                showNotice(t.getMessage());
            }
        });
    }

    public void getRoutines(){
        int id = 0;
        if(user != null){
            id = user.getId();
        }
        Call<ArrayList<Routine>> call = routineApi.getRoutineByUserId(id);
        call.enqueue(new Callback<ArrayList<Routine>>() {
            @Override
            public void onResponse(Call<ArrayList<Routine>> call, Response<ArrayList<Routine>> response) {
                ArrayList<Routine> responseBody = response.body();
                boolean isSaveDone = (new SharePreferenceManager(MainActivity.this)).saveObject("Routines",responseBody.toArray());
                if(!isSaveDone)
                {
                    showNotice("Error: Store Routines Error!!!");
                }
                loadAllSuccess();
            }

            @Override
            public void onFailure(Call<ArrayList<Routine>> call, Throwable t) {
                System.out.println("fail get Routines" + t.getMessage());
                showNotice(t.getMessage());
            }
        });
    }

    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout,fragment);
        ft.commit();
    }

    @Override
    public void sendInput(Exercise input) {
        FragmentManager manager = getSupportFragmentManager();
        fragment = manager.findFragmentById(R.id.frameLayout);
        ((ExercisePageFragment) fragment).sendInput(input);
    }

    public void sendDeleteExercisesInExercisePage(int position)
    {
        FragmentManager manager = getSupportFragmentManager();
        fragment = manager.findFragmentById(R.id.frameLayout);
        ((ExercisePageFragment) fragment).deleteExercise(position);
    }

    public void sendDeleteRoutineInCollectionPage(int position)
    {
        FragmentManager manager = getSupportFragmentManager();
        fragment = manager.findFragmentById(R.id.frameLayout);
        ((CollectionsPageFragment) fragment).deleteRoutine(position);
    }

    public void showNotice(String s)
    {
        Toast.makeText(MainActivity.this,s, Toast.LENGTH_LONG).show();
    }

    public void loadAllSuccess()
    {
        replaceFragment(new ExercisePageFragment());
    }

}