package com.example.myapplication.Views.AccountScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.Model.User.Users;
import com.example.myapplication.R;
import com.example.myapplication.Retrofit.RetrofitApi;
import com.example.myapplication.Retrofit.UsersApi;
import com.example.myapplication.Supporter.SharePreferenceManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditGenderDialog extends DialogFragment {

    RadioButton choiceMale, choiceFemale;
    Button btnEdit;
    Users user;
    RetrofitApi retrofitApi = new RetrofitApi();
    UsersApi usersApi = retrofitApi.getRetrofit().create(UsersApi.class);
    SharePreferenceManager sharePreferenceManager;
    TextView txtNotice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view =  inflater.inflate(R.layout.dialog_edit_gender,container,false);
        choiceFemale = view.findViewById(R.id.choiceFemale);
        choiceMale = view.findViewById(R.id.choiceMale);
        btnEdit = view.findViewById(R.id.btnEdit);
        txtNotice = view.findViewById(R.id.txtNotice);
        sharePreferenceManager = new SharePreferenceManager(getContext());
        user = (Users) sharePreferenceManager.getObject("User", Users.class);

        if(user.getGender().trim().equalsIgnoreCase("nam"))
        {
            choiceMale.setChecked(true);
            choiceFemale.setChecked(false);
        }
        else {
            choiceMale.setChecked(false);
            choiceFemale.setChecked(true);
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String choose = "nam";
                if(choiceMale.isChecked())
                {
                    choose = "nam";
                }
                else
                {
                    choose = "nữ";
                }

                if(!choose.equalsIgnoreCase(user.getGender()))
                {
                    user.setGender(choose);
                    Call<Users> call = usersApi.update(user);
                    call.enqueue(new Callback<Users>() {
                        @Override
                        public void onResponse(Call<Users> call, Response<Users> response) {
                            user = response.body();
                            sharePreferenceManager.saveObject("User", user);
                            if(getContext() instanceof InforScreen)
                            {
                                ((InforScreen) getContext()).setUser();
                            }
                            dismiss();
                        }

                        @Override
                        public void onFailure(Call<Users> call, Throwable t) {
                            showNotice("Network Error!");
                            System.out.println(t.getMessage());
                        }
                    });
                }


            }
        });

        return view;
    }

    private void showNotice(String s)
    {
        txtNotice.setText(s);
    }
}
