package com.example.myapplication.Views.ExerciseScreen;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.Model.User.Users;
import com.example.myapplication.Model.WorkOutUnit.Exercise;
import com.example.myapplication.R;
import com.example.myapplication.Retrofit.ExerciseApi;
import com.example.myapplication.Retrofit.RetrofitApi;
import com.example.myapplication.Supporter.SharePreferenceManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddExerciseDialog extends DialogFragment {

    private static final String TAG = "AddExerciseDialog";
    public interface OnInputListener {
        void sendChangedNotify();
    }

    private String uploadUrl = "";
    public OnInputListener mOnInputListener;
    private Button btnSave;
    private TextView txtNote;
    private EditText txtName;
    private EditText txtGuide;
    private EditText txtIntroduction;
    private EditText txtCalories;
    private ImageView img;
    private int UploadRunOutTimer;
    int SELECT_PICTURE = 200;
    private StorageReference mStorageRef;
    RetrofitApi retrofitApi = new RetrofitApi();
    ExerciseApi exerciseApi = retrofitApi.getRetrofit().create(ExerciseApi.class);
    Uri imageUrl = Uri.parse("");
    boolean imageAvailabe = false;
    Users users;

    private Exercise  exercise;

    public AddExerciseDialog()
    {

    }

    public AddExerciseDialog(Exercise exercise)
    {
        this.exercise = exercise;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        View addExerciseDialog =  inflater.inflate(R.layout.dialog_add_exercise,container,false);

        btnSave = addExerciseDialog.findViewById(R.id.btnSave);
        txtNote = addExerciseDialog.findViewById(R.id.note);
        img = addExerciseDialog.findViewById(R.id.img);
        FirebaseStorage fbs = FirebaseStorage.getInstance();
        fbs.setMaxUploadRetryTimeMillis(3000);
        mStorageRef = fbs.getReference("uploads");
        users = (Users) new SharePreferenceManager(getActivity()).getObject("User", Users.class);
        txtName = addExerciseDialog.findViewById(R.id.txtName);
        txtIntroduction = addExerciseDialog.findViewById(R.id.txtIntroduction);
        txtGuide = addExerciseDialog.findViewById(R.id.txtGuide);
        txtCalories = addExerciseDialog.findViewById(R.id.txtCalories);



        if(exercise != null)
        {
            if(exercise.getImg()!=null && !exercise.getImg().isEmpty())
            {
                Picasso.get().load(exercise.getImg()).into(img);
                imageAvailabe = true;
            }else
            {
                Picasso.get().load(R.drawable.add_image).into(img);
            }
            if (exercise.getName()!=null)  txtName.setText(exercise.getName());
            if (exercise.getIntroduction()!=null)  txtIntroduction.setText(exercise.getIntroduction());
            if (exercise.getGuideline()!=null)  txtGuide.setText(exercise.getGuideline());
            txtCalories.setText(exercise.getCalories()+"");

        }

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermission();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exercise = getExercise();
                if(exercise != null)
                {
                    saveExercise();
//                    mOnInputListener.sendInput(exercise);
//                    getDialog().dismiss();
                }
            }
        });
         return addExerciseDialog;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mOnInputListener
                    = (OnInputListener)getActivity();
        }
        catch (ClassCastException e) {
            Log.e(TAG, "onAttach: ClassCastException: "
                    + e.getMessage());
        }
    }


    private Exercise getExercise()
    {
        String name = txtName.getText().toString();
        String introduction = txtIntroduction.getText().toString();
        String guide = txtGuide.getText().toString();
        String caloriesString = txtCalories.getText().toString();
        double calories;
        if(name == null || name.trim().isEmpty())
        {
            txtNote.setText("please, fill name");
            return null;
        }
        if(caloriesString == null || caloriesString.trim().isEmpty())
        {
            calories = 0;
        }
        else
        {
            try {
                calories = Double.parseDouble(caloriesString);
            }
            catch (Exception ex)
            {
                System.out.println(ex);
                calories = 0;
            }
        }
        if(introduction == null || introduction.trim().isEmpty())
        {
            introduction = "";
        }
        if(guide == null || guide.trim().isEmpty())
        {
            guide="";
        }
        if(users == null)
        {
            txtNote.setText("No account, please sign in");
            return null;
        }
        String imgString = imageUrl.toString();
        if(exercise != null && exercise.getImg()!=null && !exercise.getImg().isEmpty() && imageAvailabe == true)
        {
            imgString = exercise.getImg();
        }
        Exercise res = new Exercise(name,users,imgString,introduction,guide,null);
        res.setCalories(calories);
        if(exercise != null) res.setId(exercise.getId());

        return res;
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
                    imageUrl = selectedImageUri;
                    imageAvailabe = false;
                    Picasso.get().load(imageUrl.toString()).into(img);
                }
            }
        }
    }

    private void saveExercise()
    {
        Uri uri = null;
        if(exercise.getImg() != null && !exercise.getImg().trim().equals("") && imageAvailabe == false)
        {
            uri = Uri.parse(exercise.getImg());
            StorageReference fileReference = mStorageRef.child("exercise/"+
                    System.currentTimeMillis()
                    +"."+getFileExtension(uri));
            fileReference.putFile(uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            System.out.println("Upload img successfully");
                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();;//wait for the task done;
                            while (!urlTask.isSuccessful());//wait for the task done;
                            Uri downloadUrl = urlTask.getResult();
                            System.out.println(downloadUrl);
                            uploadUrl = downloadUrl.toString();
                            if(!uploadUrl.equals(null) && !uploadUrl.equals(""))
                            if(urlTask.isSuccessful())
                            {
                                exercise.setImg(uploadUrl);
                                saveExerciseToDB();
                                System.out.println(downloadUrl);
                                uploadUrl = downloadUrl.toString();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            System.out.println("Upload img fail");
                            txtNote.setText("Upload image fail");
                        }
                    });
        }
        else
        {
            saveExerciseToDB();
        }
    }

    private String getFileExtension(Uri uri)
    {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void saveExerciseToDB()
    {
        Call<Exercise> saveExerciseCall = exerciseApi.save(exercise);
        saveExerciseCall.enqueue(new Callback<Exercise>() {
            @Override
            public void onResponse(Call<Exercise> call, Response<Exercise> response) {
                if(response.body() != null)
                {
                    exercise = response.body();
                    updateSharedPreference();
                    dismiss();
                }
                txtNote.setText("new exercise added");
            }

            @Override
            public void onFailure(Call<Exercise> call, Throwable t) {
                System.out.println(t.toString());
                txtNote.setText("Save Fail");
            }
        });
    }

    private void updateSharedPreference() {
        new SharePreferenceManager(getActivity()).saveExercise(exercise);
        mOnInputListener.sendChangedNotify();
    }


}
