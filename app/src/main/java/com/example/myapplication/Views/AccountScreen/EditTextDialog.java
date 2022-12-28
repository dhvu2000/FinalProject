package com.example.myapplication.Views.AccountScreen;

import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.Model.User.Users;
import com.example.myapplication.R;
import com.example.myapplication.Retrofit.RetrofitApi;
import com.example.myapplication.Retrofit.UsersApi;
import com.example.myapplication.Supporter.SharePreferenceManager;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTextDialog  extends DialogFragment {

    TextView txtName, txtName2, txtName3, txtNotice;
    EditText txtEditText, txtEditText2, txtEditText3;
    Button btnEdit;
    Users user;
    SharePreferenceManager sharePreferenceManager;
    String old;
    RetrofitApi retrofitApi = new RetrofitApi();
    UsersApi usersApi = retrofitApi.getRetrofit().create(UsersApi.class);


    private int EMAIL_EXIST_CODE = -2;
    private int USERNAME_EXIST_CODE = -1;
    private String EMAIL_FORMAT = "^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$";

    private int type;

    public EditTextDialog(int type) {
        this.type = type;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view =  inflater.inflate(R.layout.dialog_edit_information,container,false);
        txtName = view.findViewById(R.id.txtName);
        txtEditText = view.findViewById(R.id.txtEditText);
        txtName2 = view.findViewById(R.id.txtName2);
        txtEditText2 = view.findViewById(R.id.txtEditText2);
        txtName3 = view.findViewById(R.id.txtName3);
        txtEditText3 = view.findViewById(R.id.txtEditText3);
        btnEdit = view.findViewById(R.id.btnEdit);
        txtNotice = view.findViewById(R.id.txtNotice);
        sharePreferenceManager = new SharePreferenceManager(getContext());
        user = (Users) sharePreferenceManager.getObject("User", Users.class);

        if(type == R.id.txtUserName)
        {
            txtName.setText("Tên tài khoản:");
            txtEditText.setText(user.getUsername());
            old = user.getUsername();
        }
        else if(type == R.id.txtEmail)
        {
            txtName.setText("Email: ");
            txtEditText.setText(user.getEmail());
            old = user.getEmail();
        }
        else if(type == R.id.txtPassword)
        {
            txtEditText2.setVisibility(View.VISIBLE);
            txtName2.setVisibility(View.VISIBLE);
            txtEditText3.setVisibility(View.VISIBLE);
            txtName3.setVisibility(View.VISIBLE);
            txtName.setText("Mật khẩu cũ: ");
            txtName2.setText("Mật khẩu mới: ");
            txtName3.setText("Xác nhận lại mật khẩu mới: ");
            txtEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            txtEditText2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            txtEditText3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit();
            }
        });
        return view;
    }

    private void edit()
    {
        if(type == R.id.txtUserName)
        {
            editUserName();
        }
        else if(type == R.id.txtEmail)
        {
            editEmail();
        }
        else if(type == R.id.txtPassword)
        {
            editPassword();
        }
    }

    private void editUserName()
    {
        String newTyping = txtEditText.getText().toString();
        if(newTyping.trim().equals(""))
        {
            txtNotice.setText("Hãy điền thông tin!");
        }
        else if(!old.equals(newTyping))
        {
            user.setUsername(newTyping);
            Call<Users> call = usersApi.updateUserName(user);
            call.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    if(response.body().getId() == USERNAME_EXIST_CODE)
                    {
                        txtEditText.requestFocus();
                        showNotice(response.body().getUsername());
                    }
                    else
                    {
                        user = response.body();
                        sharePreferenceManager.saveObject("User", user);
                        if(getActivity() instanceof InforScreen)
                        {
                            ((InforScreen)getActivity()).setUser();
                            dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {
                    showNotice("Network Error!");
                    System.out.println("Edit user information fail: "+ t.getMessage());
                }
            });
        }
        else
        {
            dismiss();
        }
    }

    private void editEmail()
    {
        String newTyping = txtEditText.getText().toString();
        if(!newTyping.matches(EMAIL_FORMAT))
        {
            txtNotice.setText("Thông tin điền không hợp lệ!");
            txtEditText.requestFocus();
        }
        else if(!old.equals(newTyping))
        {
            user.setEmail(newTyping);
            Call<Users> call = usersApi.updateUserMail(user);
            call.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    if(response.body().getId() == EMAIL_EXIST_CODE)
                    {
                        txtEditText.requestFocus();
                        showNotice(response.body().getUsername());
                    }
                    else
                    {
                        user = response.body();
                        sharePreferenceManager.saveObject("User", user);
                        if(getActivity() instanceof InforScreen)
                        {
                            ((InforScreen)getActivity()).setUser();
                            dismiss();
                        }
                    }
                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {
                    showNotice("Lỗi mạng!");
                    System.out.println("Edit user information fail: "+ t.getMessage());
                }
            });
        }
        else
        {
            dismiss();
        }
    }

    private void editPassword()
    {
        String oldPassword = txtEditText.getText().toString();
        String newPassword = txtEditText2.getText().toString();
        String reNewPassword = txtEditText3.getText().toString();
        if(!oldPassword.equals(user.getPassword()))
        {
            txtNotice.setText("Mật khẩu cũ không đúng");
            txtEditText.requestFocus();
            return;
        }
        else if(newPassword.toString().length() < 8)
        {
            showNotice("Mật khẩu phải gồm hơn 8 ký tự");
            txtEditText2.requestFocus();
            return;
        }
        else if(newPassword.toString().equals(oldPassword.toString()))
        {
            showNotice("Mật khẩu giống mật khẩu trước");
            txtEditText2.requestFocus();
            return;
        }
        else if(!newPassword.toString().equals(reNewPassword.toString()))
        {
            showNotice("Mật khẩu mới hai lần nhập không giống nhau");
            txtEditText2.requestFocus();
            return;
        }
        else
        {
            user.setPassword(newPassword);
            Call<Users> call = usersApi.update(user);
            call.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    user = response.body();
                    sharePreferenceManager.saveObject("User", user);
                    if(getActivity() instanceof InforScreen)
                    {
                        ((InforScreen)getActivity()).setUser();
                        dismiss();
                    }
                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {
                    showNotice("Network Error!");
                    System.out.println("Edit user information fail: "+ t.getMessage());
                }
            });
        }
    }

    private void showNotice(String s)
    {
        txtNotice.setText(s);
    }
}
