package com.example.myapplication.Views.AccountScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.example.myapplication.Supporter.SharePreferenceManager;
import com.example.myapplication.Views.WelcomeScreen.LogInScreen;

public class AccountPageFragment extends Fragment {

    Button btnInfor, btnLogOut;
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

        btnInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
}
