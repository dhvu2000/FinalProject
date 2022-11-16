package com.example.myapplication.Views.CollectionsScreen.RoutineCollectionScreen;

import android.content.Intent;
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

import com.example.myapplication.Model.User.Users;
import com.example.myapplication.Model.WorkOutUnit.Routine.Routine;
import com.example.myapplication.R;
import com.example.myapplication.Retrofit.RetrofitApi;
import com.example.myapplication.Retrofit.RoutineApi;
import com.example.myapplication.Retrofit.UsersApi;
import com.example.myapplication.Supporter.SharePreferenceManager;
import com.example.myapplication.Views.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRoutineDialog extends DialogFragment {

    EditText txtName;
    TextView txtNote;
    Button btnAdd;
    RetrofitApi retrofitApi = new RetrofitApi();
    UsersApi usersApi = retrofitApi.getRetrofit().create(UsersApi.class);
    RoutineApi routineApi = retrofitApi.getRetrofit().create(RoutineApi.class);
    Users user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Call<Users> usersCall = usersApi.getUserById(8);
        user = (Users) (new SharePreferenceManager(getActivity()).getObject("User",Users.class));
        System.out.println(user);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
         View  view = inflater.inflate(R.layout.dialog_add_routine,container,false);
         btnAdd = view.findViewById(R.id.btnAdd);
         txtName = view.findViewById(R.id.txtName);
         txtNote = view.findViewById(R.id.note);

         btnAdd.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String name = txtName.getText().toString();
                 Routine routine = new Routine(name,user, MainActivity.getRandomImg(),0,0,null);
                 if(routine.getName() == null || routine.getName().isEmpty())
                 {
                     showNotice("please fill name of routine");
                     return;
                 }
                 addRoutineToDB(routine);
             }
         });
         return  view;
    }

    private void addRoutineToDB(Routine routine)
    {
        Call<Routine> call = routineApi.save(routine);
        call.enqueue(new Callback<Routine>() {
            @Override
            public void onResponse(Call<Routine> call, Response<Routine> response) {
                System.out.println(response.body().getName()+" "+response.body().getId());
                new SharePreferenceManager(getContext()).saveRoutine(response.body());
                Intent intent = new Intent(getActivity(),RoutineDetailScreen.class);
                intent.putExtra("Routine", response.body());
                startActivity(intent);
                dismiss();
            }

            @Override
            public void onFailure(Call<Routine> call, Throwable t) {
                System.out.println(t.getMessage());
                showNotice(t.getMessage());
            }
        });
    }

    private void showNotice(String s)
    {
        txtNote.setText(s);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                if(txtNote != null)
                {
                    txtNote.setText("");
                }
            }
        }, 5000);
    }
}
