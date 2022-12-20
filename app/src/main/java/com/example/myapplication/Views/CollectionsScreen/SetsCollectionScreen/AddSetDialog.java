package com.example.myapplication.Views.CollectionsScreen.SetsCollectionScreen;

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
import com.example.myapplication.Model.WorkOutUnit.WorkOutSet.WorkOutSet;
import com.example.myapplication.R;
import com.example.myapplication.Retrofit.RetrofitApi;
import com.example.myapplication.Retrofit.RoutineApi;
import com.example.myapplication.Retrofit.UsersApi;
import com.example.myapplication.Retrofit.WorkOutSetApi;
import com.example.myapplication.Supporter.SharePreferenceManager;
import com.example.myapplication.Views.CollectionsScreen.BothUseScreen.DetailCollectionScreen;
import com.example.myapplication.Views.CollectionsScreen.BothUseScreen.UpdateSaveCollectionScreen;
import com.example.myapplication.Views.CollectionsScreen.RoutineCollectionScreen.RoutineDetailScreen;
import com.example.myapplication.Views.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSetDialog extends DialogFragment {

    EditText txtName;
    TextView txtNote, txtHead;
    Button btnAdd;
    RetrofitApi retrofitApi = new RetrofitApi();
    WorkOutSetApi workOutSetApi = retrofitApi.getRetrofit().create(WorkOutSetApi.class);
    Users user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        txtHead = view.findViewById(R.id.txtHead);

        txtHead.setText("Tên bài tập:");



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = txtName.getText().toString();
                if(name.trim() == null || name.trim().isEmpty())
                {
                    showNotice("Xin vui lòng nhập tên");
                    return;
                }

                WorkOutSet workOutSet = new WorkOutSet();
                workOutSet.setName(name);
                workOutSet.setType("single");
                workOutSet.setImg(MainActivity.getRandomImg());
                workOutSet.setCreatedBy(user);
                Intent intent = new Intent(getActivity(), UpdateSaveCollectionScreen.class);
                intent.putExtra("type","single");
                //remember to put Routine inside Routine Day
                intent.putExtra("set",workOutSet);
                startActivity(intent);
                dismiss();
            }
        });
        return  view;
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
