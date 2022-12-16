package com.example.myapplication.Views.AccountScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Model.User.UserSchema;
import com.example.myapplication.Model.User.Users;
import com.example.myapplication.R;
import com.example.myapplication.Retrofit.RetrofitApi;
import com.example.myapplication.Retrofit.UsersApi;
import com.example.myapplication.Supporter.SharePreferenceManager;
import com.example.myapplication.Supporter.TimeFormatter;
import com.example.myapplication.Views.HomeScreen.SchemaSettingDialog;
import com.example.myapplication.Views.WelcomeScreen.CreateAccountScreen;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class InforScreen extends AppCompatActivity {


    LinearLayout areaUserName, areaPassword, areaEmail, areaWeight, areaHeight, areaGender, areaDOB, areaUserAnalyseInfor,
            areaQ1, areaQ2, areaQ3, areaQ4, areaQ5;
    TextView txtUserName, txtPassword, txtEmail, txtWeight, txtHeight, txtDOB, txtGender,
            txtQ1, txtQ2, txtQ3, txtQ4, txtQ5;
    CircleImageView img;
    Button btnUserAnalysedCreate;
    ImageButton btnBack;
    RetrofitApi retrofitApi = new RetrofitApi();
    UsersApi usersApi = retrofitApi.getRetrofit().create(UsersApi.class);

    private Users user;
    private SharePreferenceManager sharePreferenceManager;

    int SELECT_PICTURE = 200;
    private StorageReference mStorageRef;


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
        btnBack = findViewById(R.id.btnBack);
        FirebaseStorage fbs = FirebaseStorage.getInstance();
        fbs.setMaxUploadRetryTimeMillis(3000);
        mStorageRef = fbs.getReference("uploads");
        sharePreferenceManager = new SharePreferenceManager(this);

        setUser();
        setButtons();
    }

    public void setUser() {

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
        }

        if(user.getImg()!=null && !user.getImg().isEmpty())
        {
            Picasso.get().load(user.getImg()).error(R.drawable.avatar).into(img);
        }else
        {
            Picasso.get().load(R.drawable.avatar).into(img);
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
            areaUserAnalyseInfor.setVisibility(View.GONE);
        }
        else
        {
            areaUserAnalyseInfor.setVisibility(View.VISIBLE);

            //fill infor
        }
    }

    private void setButtons()
    {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermission();
            }
        });

        areaWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserSchema schema = null;
                if( user.getSchemas() != null && user.getSchemas().size() > 0)
                {
                    schema = user.getSchemas().get(user.getSchemas().size()-1);
                }
                SchemaSettingDialog schemaSettingDialog = new SchemaSettingDialog();
                if(schema != null)
                {
                    schemaSettingDialog = new SchemaSettingDialog(schema.getHeight(), schema.getWeight());
                }
                schemaSettingDialog.show(getSupportFragmentManager(), "SchemaSettingDialog");
            }
        });

        areaHeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserSchema schema = null;
                if( user.getSchemas() != null && user.getSchemas().size() > 0)
                {
                    schema = user.getSchemas().get(user.getSchemas().size()-1);
                }
                SchemaSettingDialog schemaSettingDialog = new SchemaSettingDialog();
                if(schema != null)
                {
                    schemaSettingDialog = new SchemaSettingDialog(schema.getHeight(), schema.getWeight());
                }
                schemaSettingDialog.show(getSupportFragmentManager(), "SchemaSettingDialog");
            }
        });

        areaUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditTextDialog editTextDialog = new EditTextDialog(R.id.txtUserName);
                editTextDialog.show(getSupportFragmentManager(), "EditTextDialog");
            }
        });

        areaEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditTextDialog editTextDialog = new EditTextDialog(R.id.txtEmail);
                editTextDialog.show(getSupportFragmentManager(), "EditTextDialog");
            }
        });

        areaPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditTextDialog editTextDialog = new EditTextDialog(R.id.txtPassword);
                editTextDialog.show(getSupportFragmentManager(), "EditTextDialog");
            }
        });

        areaGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditGenderDialog editGenderDialog = new EditGenderDialog();
                editGenderDialog.show(getSupportFragmentManager(), "EditGenderDialog");
            }
        });

        areaDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePicker();
            }
        });


    }

    private void openDatePicker()
    {
        Calendar calendar = Calendar.getInstance();
        Date dob = TimeFormatter.convertToDate(user.getDob());
        calendar.setTime(dob);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        txtDOB.setText(d+"/"+(month+1)+"/"+y);
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(y,m,d,0,0,0);
                        String newDOB = TimeFormatter.FormatDateTime(calendar.getTime());
                        user.setDob(newDOB);
                        saveUserToDB();
                    }
                },year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();
    }

    private void requestPermission()
    {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                openImagePicker();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                System.out.println("Image request denied");
            }
        };

        TedPermission.create()
                .setPermissionListener(permissionListener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE)
                .check();

    }

    private void openImagePicker()
    {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    Uri imageUrl = selectedImageUri;
                    uploadImg(imageUrl);
                }
            }
        }
    }

    private void uploadImg(Uri uri) {
        StorageReference fileReference = mStorageRef.child("avatars/"+
                System.currentTimeMillis()
                +"."+getFileExtension(uri));
        fileReference.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        System.out.println("Upload img successfully");
                        Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!urlTask.isSuccessful());//wait for the task done;
                        Uri downloadUrl = urlTask.getResult();
                        System.out.println(downloadUrl);
                        String uploadUrl = downloadUrl.toString();
                        if(!uploadUrl.equals(null) && !uploadUrl.equals(""))
                        {
                            user.setImg(uploadUrl);
                            saveUserToDB();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("Upload img fail");
                        showNotice("upload Image Fail");
                    }
                });
    }

    private void saveUserToDB() {
        Call<Users> call = usersApi.update(user);
        call.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                user = response.body();
                sharePreferenceManager.saveObject("User", user);
                Picasso.get().load(user.getImg()).error(R.drawable.avatar).into(img);
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                showNotice("Network Error!");
                System.out.println("Edit user information fail: "+ t.getMessage());
            }
        });
    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver cR = this.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void showNotice(String s)
    {
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
    }
}