package com.example.myapplication.Views.WelcomeScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Model.User.Users;
import com.example.myapplication.R;
import com.example.myapplication.Retrofit.RetrofitApi;
import com.example.myapplication.Retrofit.UsersApi;
import com.example.myapplication.Supporter.SharePreferenceManager;
import com.example.myapplication.Supporter.TimeFormatter;
import com.example.myapplication.Views.MainActivity;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAccountScreen extends AppCompatActivity {

    //CONSTANT
    private String EMAIL_EXIST = "Email has already existed";
    private String USERNAME_EXIST = "Username has already existed";
    private int EMAIL_EXIST_CODE = -2;
    private int USERNAME_EXIST_CODE = -1;
    private String EMAIL_FORMAT = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";

    private Button btnBack, btnDone;
    private EditText txtUserName, txtEmail, txtPassword, txtRePassWord;
    private TextView txtDOB;
    private RadioButton choiceMale, choiceFemale;
    private Date pickedDate = null;

    //API
    RetrofitApi retrofitApi = new RetrofitApi();
    UsersApi usersApi = retrofitApi.getRetrofit().create(UsersApi.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_create_account_screen);
        btnBack = findViewById(R.id.btnBack);
        btnDone = findViewById(R.id.btnDone);
        txtUserName = findViewById(R.id.txtUserName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtRePassWord = findViewById(R.id.txtRePassword);
        txtDOB = findViewById(R.id.txtDOB);
        choiceMale = findViewById(R.id.choiceMale);
        choiceFemale = findViewById(R.id.choiceFemale);

        txtDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                if(pickedDate != null)
                {
                    calendar.setTime(pickedDate);
                }
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateAccountScreen.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                                txtDOB.setText(d+"/"+(month+1)+"/"+y);
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(y,m,d,0,0,0);
                                pickedDate = calendar.getTime();
//                                txtDOB.setText(TimeFormatter.FormatDateTime(pickedDate));
                            }
                        },year, month, dayOfMonth);
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                datePickerDialog.show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CreateAccountScreen.this, LogInScreen.class);
                startActivity(intent);
                finish();
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkFormat())
                {
                    String username = txtUserName.getText().toString();
                    String email = txtEmail.getText().toString();
                    String password = txtPassword.getText().toString();
                    String dob = TimeFormatter.FormatDateTime(pickedDate);
                    String sex = "nam";
                    if(choiceFemale.isChecked())
                    {
                        sex = "nữ";
                    }
                    Users u = new Users(username,dob,password,email,sex,"member",null, new ArrayList<>());
                    System.out.println("Created User:" + u);
                    saveUserToDB(u);
                };
            }
        });
    }

    private void saveUserToDB(Users u)
    {
        Call<Users> call = usersApi.save(u);
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                Users u = response.body();
                if(u.getId() == EMAIL_EXIST_CODE)
                {
                    txtEmail.requestFocus();
                    showNotice(u.getUsername());
                }
                else if(u.getId() == USERNAME_EXIST_CODE)
                {
                    txtUserName.requestFocus();
                    showNotice(u.getUsername());
                }
                else
                {
                    System.out.println(u.getId() + "user saved successfully: "+ u);
                    new SharePreferenceManager(CreateAccountScreen.this).saveObject("User", u);
                    Intent intent = new Intent(CreateAccountScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                showNotice("Network error");
                System.out.println("Save User Error: " + t.getMessage());
            }
        });
    }

    private boolean checkFormat() {

        if(txtUserName.getText().toString().trim().isEmpty())
        {
            showNotice("Xin hãy kiểm tra tên tài khoản");
            txtUserName.requestFocus();
            return false;
        }
        else if (txtEmail.getText().toString().trim().isEmpty() || !txtEmail.getText().toString().matches(EMAIL_FORMAT))
        {
            showNotice("email không hợp lệ");
            txtEmail.requestFocus();
            return false;
        }
        else if(txtDOB.getText().toString().trim().isEmpty())
        {
            showNotice("Xin hãy kiểm tra ngày tháng năm sinh");
            return false;
        }
        else if(txtPassword.getText().toString().length() < 8)
        {
            showNotice("Mật khẩu phải gồm hơn 8 ký tự");
            txtPassword.requestFocus();
            return false;
        }
        else if(!txtPassword.getText().toString().equals(txtRePassWord.getText().toString()))
        {
            showNotice("Mật khẩu hai lần nhập không giống nhau");
            txtPassword.requestFocus();
            return false;
        }


        return true;
    }

    private void showNotice(String s)
    {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}