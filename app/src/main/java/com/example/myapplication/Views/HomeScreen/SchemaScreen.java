package com.example.myapplication.Views.HomeScreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_schema_screen);
        recyclerView  = findViewById(R.id.recyclerView);
        btnBack = findViewById(R.id.btnBack);

        Users user = (Users) new SharePreferenceManager(this).getObject("User", Users.class);
        ArrayList<UserSchema> userSchemas = (ArrayList<UserSchema>) user.getSchemas();
        if(userSchemas == null)
        {
            userSchemas = new ArrayList<>();
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
}