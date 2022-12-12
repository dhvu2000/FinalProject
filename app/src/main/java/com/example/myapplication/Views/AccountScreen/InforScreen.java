package com.example.myapplication.Views.AccountScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.Model.User.UserSchema;
import com.example.myapplication.Model.User.Users;
import com.example.myapplication.R;
import com.example.myapplication.Supporter.SharePreferenceManager;
import com.example.myapplication.Supporter.TimeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class InforScreen extends AppCompatActivity {


    LinearLayout areaUserName, areaPassword, areaEmail, areaWeight, areaHeight, areaGender, areaDOB, areaUserAnalyseInfor,
            areaQ1, areaQ2, areaQ3, areaQ4, areaQ5;
    TextView txtUserName, txtPassword, txtEmail, txtWeight, txtHeight, txtDOB, txtGender,
            txtQ1, txtQ2, txtQ3, txtQ4, txtQ5;
    CircleImageView img;
    Button btnUserAnalysedCreate;

    private Users user;
    private SharePreferenceManager sharePreferenceManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_infor_screen);
        areaUserName = findViewById(R.id.areaUserName);
        areaDOB = findViewById(R.id.areaDOB);
        areaEmail = findViewById(R.id.areaEmail);
        areaGender = findViewById(R.id.areaGender);
        areaHeight = findViewById(R.id.areaHeight);
        areaPassword = findViewById(R.id.areaPassword);
        areaWeight = findViewById(R.id.areaWeight);
        areaUserAnalyseInfor = findViewById(R.id.areaAnalysedInfor);
        areaQ1 = findViewById(R.id.areaQ1);
        areaQ2 = findViewById(R.id.areaQ2);
        areaQ3 = findViewById(R.id.areaQ3);
        areaQ4 = findViewById(R.id.areaQ4);
//        areaQ5 = findViewById(R.id.areaQ5);
        txtDOB = findViewById(R.id.txtDOB);
        txtEmail = findViewById(R.id.txtEmail);
        txtGender = findViewById(R.id.txtSex);
        txtHeight = findViewById(R.id.txtHeight);
        txtPassword = findViewById(R.id.txtPassword);
        txtUserName = findViewById(R.id.txtUserName);
        txtWeight = findViewById(R.id.txtWeight);
        txtQ1 = findViewById(R.id.txtQ1);
        txtQ2 = findViewById(R.id.txtQ2);
        txtQ3 = findViewById(R.id.txtQ3);
        txtQ4 = findViewById(R.id.txtQ4);
//        txtQ5 = findViewById(R.id.txtQ5);
        img = findViewById(R.id.img);
        btnUserAnalysedCreate = findViewById(R.id.btnUserAnalysedCreate);

        sharePreferenceManager = new SharePreferenceManager(this);

        setUser();
    }

    private void setUser() {

        user = (Users)  sharePreferenceManager.getObject("User", Users.class);
        if( user.getSchemas() != null && user.getSchemas().size() > 0){
            UserSchema userSchema = user.getSchemas().get(user.getSchemas().size()-1);
            String result = String.format("%.1f", userSchema.getWeight());
            txtWeight.setText(result + "");
            result = String.format("%.1f", userSchema.getHeight());
            txtHeight.setText(result + "");
        }
        else
        {
            txtWeight.setText( "-.-");
            txtHeight.setText( "-.-");
            return;
        }
        txtUserName.setText(user.getUsername());
        txtEmail. setText(user.getEmail());
        txtPassword.setText("2222222");
        txtGender.setText(user.getGender());
        Date d = TimeFormatter.convertToDate(user.getDob());
        SimpleDateFormat spd = new SimpleDateFormat("dd/MM/yyyy");
        txtDOB. setText(spd.format(d));

        setUserAnalysedInfor();
    }

    private void setUserAnalysedInfor() {
        if(user.getInfor() == null)
        {
            btnUserAnalysedCreate.setVisibility(View.VISIBLE);
            areaUserAnalyseInfor.setVisibility(View.GONE);
        }
        else
        {
            btnUserAnalysedCreate.setVisibility(View.GONE);
            areaUserAnalyseInfor.setVisibility(View.VISIBLE);

            //fill infor
        }
    }
}