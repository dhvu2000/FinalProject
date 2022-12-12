package com.example.myapplication.Views.HomeScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.Adapter.SchemaListItem.SchemaAdapter;
import com.example.myapplication.Model.User.UserSchema;
import com.example.myapplication.Model.User.Users;
import com.example.myapplication.R;
import com.example.myapplication.Supporter.SharePreferenceManager;

import java.util.ArrayList;
import java.util.Collections;

public class SchemaScreen extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Button btnBack;
    ArrayList<UserSchema> userSchemas;
    private TextView txtMaxHeight, txtMinHeight, txtMaxWeight, txtMinWeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_schema_screen);
        recyclerView  = findViewById(R.id.recyclerView);
        btnBack = findViewById(R.id.btnBack);
        txtMaxHeight = findViewById(R.id.txtMaxHeight);
        txtMinHeight = findViewById(R.id.txtMinHeight);
        txtMaxWeight = findViewById(R.id.txtMaxWeight);
        txtMinWeight = findViewById(R.id.txtMinWeight);

        Users user = (Users) new SharePreferenceManager(this).getObject("User", Users.class);
        userSchemas = (ArrayList<UserSchema>) user.getSchemas();
        if(userSchemas == null || userSchemas.size() == 0)
        {
            userSchemas = new ArrayList<>();
        }
        else
        {
            setStatistic();
        }
        Collections.reverse(userSchemas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SchemaAdapter adapter = new SchemaAdapter(this, userSchemas);
        recyclerView.setAdapter(adapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



    }

    public void setStatistic() {
        if(userSchemas == null || userSchemas.size() == 0)
        {
            txtMaxHeight.setText(0+"");
            txtMinHeight.setText(0+"");
            txtMaxWeight.setText(0+"");
            txtMinWeight.setText(0+"");
            return;
        }
        double max = userSchemas.get(0).getHeight();
        double min = userSchemas.get(0).getHeight();
        for(UserSchema u : userSchemas)
        {
            if(u.getHeight() > max)
            {
                max = u.getHeight();
            }
            if(u.getHeight() < min)
            {
                min = u.getHeight();
            }
        }
        txtMaxHeight.setText(max+"");
        txtMinHeight.setText(min+"");

        max = userSchemas.get(0).getWeight();
        min = userSchemas.get(0).getWeight();
        for(UserSchema u : userSchemas)
        {
            if(u.getWeight() > max)
            {
                max = u.getWeight();
            }
            if(u.getWeight() < min)
            {
                min = u.getWeight();
            }
        }
        txtMaxWeight.setText(max+"");
        txtMinWeight.setText(min+"");
    }
}