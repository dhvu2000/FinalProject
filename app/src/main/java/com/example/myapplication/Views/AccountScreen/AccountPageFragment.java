package com.example.myapplication.Views.AccountScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.myapplication.Model.User.Users;
import com.example.myapplication.R;
import com.example.myapplication.Supporter.SharePreferenceManager;
import com.example.myapplication.Views.WelcomeScreen.LogInScreen;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountPageFragment extends Fragment {

    Button btnInfor, btnLogOut;
    CircleImageView img;
    TextView txtUserName;
    public AccountPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_page, container, false);

        btnInfor = view.findViewById(R.id.btnInfor);
        btnLogOut = view.findViewById(R.id.btnLogOut);
        img = view.findViewById(R.id.img);
        txtUserName = view.findViewById(R.id.txtUserName);


        btnInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), InforScreen.class);
                startActivity(intent);
            }
        });

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SharePreferenceManager(getContext()).saveObject("User", null);
                Intent intent = new Intent(getContext(), LogInScreen.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Users user = (Users) new SharePreferenceManager(getContext()).getObject("User", Users.class);
        txtUserName.setText(user.getUsername());
        if(user.getImg()!=null && !user.getImg().isEmpty())
        {
            Picasso.get().load(user.getImg()).error(R.drawable.avatar).into(img);
        }else
        {
            Picasso.get().load(R.drawable.avatar).into(img);
        }
    }
}
