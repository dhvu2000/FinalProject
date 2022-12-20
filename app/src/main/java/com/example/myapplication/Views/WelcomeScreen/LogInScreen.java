package com.example.myapplication.Views.WelcomeScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Model.User.Users;
import com.example.myapplication.R;
import com.example.myapplication.Retrofit.RetrofitApi;
import com.example.myapplication.Retrofit.UsersApi;
import com.example.myapplication.Supporter.SharePreferenceManager;
import com.example.myapplication.Views.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogInScreen extends AppCompatActivity {

    private TextView txtUserName, txtPassword;
    private Button btnLogIn, btnCreateAccount;
    RetrofitApi retrofitApi = new RetrofitApi();
    UsersApi usersApi = retrofitApi.getRetrofit().create(UsersApi.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_log_in_screen);
        txtUserName = findViewById(R.id.txtUserName);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogIn = findViewById(R.id.btnLogIn);
        btnCreateAccount = findViewById(R.id.btnCreateAccount);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = txtUserName.getText().toString();
                String pass = txtPassword.getText().toString();
                checkLogin(username, pass);
            }
        });

        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogInScreen.this, CreateAccountScreen.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void checkLogin(String username, String pass) {
        Users u = new Users(username,"",pass,"null","","", null);
        System.out.println(u);
        Call<Users> call = usersApi.logInUser(u);
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                Users users = response.body();
                if(users.getId() == 0)
                {
                    showNotice("Thông tin đăng nhập sai");
                }
                else
                {
                    new SharePreferenceManager(LogInScreen.this).saveObject("User", users);
                    Intent i = new Intent(LogInScreen.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }

            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                System.out.println("Log in function fail: " + t.getMessage());
                showNotice("Network Error");
            }
        });
    }

    private void showNotice(String s)
    {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}