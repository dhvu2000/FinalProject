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

import com.example.myapplication.Model.WorkOutUnit.Exercise;
import com.example.myapplication.R;
import com.example.myapplication.Views.CollectionsScreen.CollectionsPageFragment;
import com.example.myapplication.Views.ExerciseScreen.AddExerciseDialog;
import com.example.myapplication.Views.ExerciseScreen.ExercisePageFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity
        implements  AddExerciseDialog.OnInputListener{

    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    Fragment fragment;
    int chosenItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        frameLayout = findViewById(R.id.frameLayout);

//        frameLayout.setLayoutParams(new FrameLayout.LayoutParams(,-60));
        chosenItem = R.id.btnExercises;
        replaceFragment(new ExercisePageFragment());
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

    }

    @Override
    protected void onStart() {
        super.onStart();
        int width = frameLayout.getWidth();
        int height = frameLayout.getHeight();
        System.out.println(width+ " "+height);
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

}