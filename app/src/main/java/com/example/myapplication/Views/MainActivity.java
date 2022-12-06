package com.example.myapplication.Views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlarmManager;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.myapplication.Model.User.UserSchema;
import com.example.myapplication.Model.User.Users;
import com.example.myapplication.Model.WorkOutUnit.Exercise;
import com.example.myapplication.Model.WorkOutUnit.Routine.Routine;
import com.example.myapplication.R;
import com.example.myapplication.Retrofit.ExerciseApi;
import com.example.myapplication.Retrofit.NotificationApi;
import com.example.myapplication.Retrofit.RetrofitApi;
import com.example.myapplication.Retrofit.RoutineApi;
import com.example.myapplication.Retrofit.UsersApi;
import com.example.myapplication.Supporter.NotificationCreator;
import com.example.myapplication.Supporter.SharePreferenceManager;
import com.example.myapplication.Views.AccountScreen.AccountPageFragment;
import com.example.myapplication.Views.CollectionsScreen.CollectionsPageFragment;
import com.example.myapplication.Views.ExerciseScreen.AddExerciseDialog;
import com.example.myapplication.Views.ExerciseScreen.ExercisePageFragment;
import com.example.myapplication.Views.HomeScreen.HomePageFragment;
import com.example.myapplication.Views.WelcomeScreen.WelcomeScreen;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity
        implements  AddExerciseDialog.OnInputListener{

    public static String RANDOM_IMG_1 = "https://firebasestorage.googleapis.com/v0/b/gymlife-22ff7.appspot.com/o/uploads%2Fuploads%2FrandomImg%2Frandom.png?alt=media&token=bc61f81e-9582-48fe-86d4-e3074bc77273";
    public static String RANDOM_IMG_2 = "https://firebasestorage.googleapis.com/v0/b/gymlife-22ff7.appspot.com/o/uploads%2Fuploads%2FrandomImg%2Frandom1.png?alt=media&token=a29d0834-5652-4833-9f82-ad492de40df4";
    public static String RANDOM_IMG_3 = "https://firebasestorage.googleapis.com/v0/b/gymlife-22ff7.appspot.com/o/uploads%2Fuploads%2FrandomImg%2Frandom2.png?alt=media&token=5d990c5f-c77e-45f5-ab96-292ee660040e";

    public static int DELETE_EXERCISE_REQUEST = 1;

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

    private void loadDefaultFragment()
    {
        chosenItem = R.id.btnHome;
        replaceFragment(new HomePageFragment());
    }

    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    Fragment fragment;
    int chosenItem;
    RetrofitApi retrofitApi = new RetrofitApi();
    Users user;
    ArrayList<Routine> routines = new ArrayList<>();
    ArrayList<Exercise> exercises = new ArrayList<>();
    UsersApi usersApi = retrofitApi.getRetrofit().create(UsersApi.class);
    ExerciseApi exerciseApi = retrofitApi.getRetrofit().create((ExerciseApi.class));
    RoutineApi routineApi = retrofitApi.getRetrofit().create(RoutineApi.class);
    SharePreferenceManager sharePreferenceManager;
    private boolean onMission = false;
    private int request;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        frameLayout = findViewById(R.id.frameLayout);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        sharePreferenceManager = new SharePreferenceManager(this);
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
                        if(chosenItem != R.id.btnHome)
                        {
                            chosenItem = R.id.btnHome;
                            replaceFragment(new HomePageFragment());
                        }
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
                        if(chosenItem != R.id.btnAccount)
                        {
                            chosenItem = R.id.btnAccount;
                            replaceFragment(new AccountPageFragment());
                        }
                        break;
                }

                return true;
            }
        });

        getUser();


        //----Notification
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        c.setTimeInMillis(SystemClock.elapsedRealtime());

        date  = c.getTime();
                new NotificationCreator().sendNotification("Tập cho 1 ngày đẹp", "Có vẻ lâu rồi không thấy bạn tập",
                        c.getTimeInMillis(), AlarmManager.INTERVAL_DAY,"remindNotify");
//        new NotificationCreator().deleteNotification("remindNotify");
    }

    private void sendNotification() {

    }


    @Override
    protected void onStart() {
        super.onStart();;
    }

    public void getUser()
    {
        user = (Users) sharePreferenceManager.getObject("User", Users.class);
        if(user != null)
        {
            getExercises();
            return;
        }
        else
        {
            Intent intent = new Intent(MainActivity.this, WelcomeScreen.class);
            startActivity(intent);
            finish();
        }
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
                    exercises = response.body();
                    getRoutines();

                }
            }

            @Override
            public void onFailure(Call<ArrayList<Exercise>> call, Throwable t) {
                System.out.println("fail get Exercises" + t.getMessage());
                loadDefault();
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
                routines = response.body();
                loadAllSuccess();
            }

            @Override
            public void onFailure(Call<ArrayList<Routine>> call, Throwable t) {
                System.out.println("fail get Routines" + t.getMessage());
                loadDefault();
            }
        });
    }

    private void loadDefault()
    {
        Exercise[] data1 = (Exercise[]) sharePreferenceManager.getObject("Exercises", Exercise[].class);
        Routine[] data2 = (Routine[]) sharePreferenceManager.getObject("Routines", Routine[].class);
        if(data1 != null || data2 != null)
        {
            if(onMission == false)
            {
                loadDefaultFragment();
            }
            onMission = true;
        }
    }


    private void replaceFragment(Fragment fragment)
    {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout,fragment);
        ft.commit();
    }

    @Override
    public void sendChangedNotify() {
        FragmentManager manager = getSupportFragmentManager();
        fragment = manager.findFragmentById(R.id.frameLayout);
        ((ExercisePageFragment) fragment).sendChangedNotify();
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

    public void deleteExercise()
    {
        request = DELETE_EXERCISE_REQUEST;
        getExercises();
    }


    private void loadAllSuccess()
    {
        sharePreferenceManager.saveObject("User",user);
        sharePreferenceManager.saveObject("Exercises",exercises.toArray());
        sharePreferenceManager.saveObject("Routines",routines.toArray());
        if(onMission == false)
        {
            loadDefaultFragment();

        }else if(request == DELETE_EXERCISE_REQUEST)
        {
            FragmentManager manager = getSupportFragmentManager();
            fragment = manager.findFragmentById(R.id.frameLayout);
            ((ExercisePageFragment) fragment).sendChangedNotify();
            request = 0;
        }
        onMission = true;
    }

    public void updateSchema(UserSchema userSchema)
    {
        Users u = (Users) sharePreferenceManager.getObject("User", Users.class);
        if(u.getSchemas() ==  null)
        {
            u.setSchemas(new ArrayList<>());
        }
        u.getSchemas().add(userSchema);
        sharePreferenceManager.saveObject("User",u);
        FragmentManager manager = getSupportFragmentManager();
        fragment = manager.findFragmentById(R.id.frameLayout);
        ((HomePageFragment) fragment).updateSchema();
    }



}