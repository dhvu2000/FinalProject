package com.example.myapplication.Views.HomeScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.Model.User.UserSchema;
import com.example.myapplication.Model.User.Users;
import com.example.myapplication.R;
import com.example.myapplication.Retrofit.RetrofitApi;
import com.example.myapplication.Retrofit.UserSchemaApi;
import com.example.myapplication.Supporter.SharePreferenceManager;
import com.example.myapplication.Supporter.TimeFormatter;
import com.example.myapplication.Views.AccountScreen.InforScreen;
import com.example.myapplication.Views.MainActivity;

import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SchemaSettingDialog extends DialogFragment {


    double height;
    double weight;
    EditText txtHeight, txtWeight;
    TextView txtNotice;
    Button btnOk;
    RetrofitApi retrofitApi = new RetrofitApi();
    UserSchemaApi userSchemaApi = retrofitApi.getRetrofit().create(UserSchemaApi.class);

    public SchemaSettingDialog()
    {

    }

    public SchemaSettingDialog(double height, double weight) {
        this.height = height;
        this.weight = weight;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view =  inflater.inflate(R.layout.dialog_schema_setting,container,false);

        txtWeight = view.findViewById(R.id.txtWeight);
        txtHeight = view.findViewById(R.id.txtHeight);
        btnOk = view.findViewById(R.id.btnOk);
        txtNotice = view.findViewById(R.id.txtNotice);

        if(height > 0 && weight > 0)
        {
            txtHeight.setText(height+"");
            txtWeight.setText(weight+"");
        }

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check())
                {
                    String strHeight = txtHeight.getText().toString();
                    String strWeight = txtWeight.getText().toString();
                    height = Double.parseDouble(strHeight);
                    weight = Double.parseDouble(strWeight);
                    saveSchemaToDB();


                }
            }
        });

        return view;
    }

    private boolean check() {

        String strHeight = txtHeight.getText().toString();
        String strWeight = txtWeight.getText().toString();

        if(strWeight.trim().isEmpty() || Double.parseDouble(strWeight) <= 0.0)
        {
            showNotice("Ch??? s??? c??n n???ng kh??ng h???p l??? !");
            txtWeight.requestFocus();
            return false;
        }

        if(strHeight.trim().isEmpty() || Double.parseDouble(strHeight) <= 0.0)
        {
            showNotice("Ch??? s??? chi???u cao kh??ng h???p l??? !");
            txtHeight.requestFocus();
            return false;
        }
        return true;
    }

    private void saveSchemaToDB()
    {
        String date = TimeFormatter.FormatDateTime(new Date());
        SharePreferenceManager sharePreferenceManager = new SharePreferenceManager(getContext());
        UserSchema userSchema = new UserSchema(height, weight, date);
        Users u = (Users) new SharePreferenceManager(getContext()).getObject("User", Users.class);
        userSchema.setUser(u);
        Call<UserSchema> call = userSchemaApi.saveUserSchema(userSchema);
        call.enqueue(new Callback<UserSchema>() {
            @Override
            public void onResponse(Call<UserSchema> call, Response<UserSchema> response) {
                UserSchema schema = response.body();
                if(u.getSchemas() ==  null)
                {
                    u.setSchemas(new ArrayList<>());
                }
                u.getSchemas().add(schema);
                sharePreferenceManager.saveObject("User",u);
                if(getActivity() instanceof MainActivity)
                {
                    ((MainActivity)getActivity()).updateSchema();
                }
                else if (getActivity() instanceof InforScreen)
                {
                    ((InforScreen)getActivity()).setUser();
                }
                dismiss();
            }

            @Override
            public void onFailure(Call<UserSchema> call, Throwable t) {
                showNotice("Update fail! Network Error");
            }
        });
    }

    private void showNotice(String s)
    {
        if(txtNotice != null)
        {
            txtNotice.setText(s);
        }
    }
}
