package com.example.myapplication.Adapter.SchemaListItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Model.User.UserSchema;
import com.example.myapplication.R;
import com.example.myapplication.Retrofit.RetrofitApi;
import com.example.myapplication.Retrofit.UserSchemaApi;
import com.example.myapplication.Supporter.SharePreferenceManager;
import com.example.myapplication.Supporter.TimeFormatter;
import com.example.myapplication.Views.HomeScreen.SchemaScreen;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SchemaAdapter extends RecyclerView.Adapter<SchemaHolder> {

    Context context;
    ArrayList<UserSchema> userSchemas;
    RetrofitApi retrofitApi = new RetrofitApi();
    UserSchemaApi userSchemaApi = retrofitApi.getRetrofit().create(UserSchemaApi.class);

    public SchemaAdapter(Context context, ArrayList<UserSchema> userSchemas) {
        this.context = context;
        this.userSchemas = userSchemas;
    }

    @NonNull
    @Override
    public SchemaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_schema_item,parent,false);
        return new SchemaHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SchemaHolder holder, int position) {
        UserSchema  userSchema = userSchemas.get(position);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = TimeFormatter.convertToDate(userSchema.getUpdatedDate());
        holder.txtDate.setText(simpleDateFormat.format(date));
        holder.txtWeight.setText(userSchema.getWeight()+ " kg");
        holder.txtHeight.setText(userSchema.getHeight()+" cm");
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteSchema(holder.getAdapterPosition());
            }
        });
    }

    private void deleteSchema(int position)
    {
        UserSchema  userSchema = userSchemas.get(position);
        Call<Void> call = userSchemaApi.deleteSchema(userSchema.getId());
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                new SharePreferenceManager(context).deleteSchema(userSchema.getId());
                userSchemas.remove(position);
                if(context instanceof SchemaScreen)
                {
                    ((SchemaScreen)context).setStatistic();
                }
                notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(context, "Delete Fail! Network Error", Toast.LENGTH_LONG).show();
                System.out.println(t.getMessage());

            }
        });
    }

    @Override
    public int getItemCount() {
        return userSchemas.size();
    }

}
