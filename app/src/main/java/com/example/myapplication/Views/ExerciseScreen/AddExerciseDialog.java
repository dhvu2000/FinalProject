package com.example.myapplication.Views.ExerciseScreen;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.Model.User.Users;
import com.example.myapplication.Model.WorkOutUnit.Exercise;
import com.example.myapplication.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;
import com.squareup.picasso.Picasso;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class AddExerciseDialog extends DialogFragment {

    private static final String TAG = "AddExerciseDialog";
    public interface OnInputListener {
        void sendInput(Exercise input);
    }

    private String uploadUrl = "";
    public OnInputListener mOnInputListener;
    private Button btnSave;
    private TextView txtNote;
    private EditText txtName;
    private EditText txtGuide;
    private EditText txtIntroduction;
    private ImageView img;
    int SELECT_PICTURE = 200;
    Uri imageUrl = Uri.parse("");
    Users users;
    Exercise  exercise;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        View addExerciseDialog =  inflater.inflate(R.layout.add_exercise_dialog,container,false);

        btnSave = addExerciseDialog.findViewById(R.id.btnSave);
        txtNote = addExerciseDialog.findViewById(R.id.note);
        img = addExerciseDialog.findViewById(R.id.img);
        users = (Users) getArguments().getSerializable("users");
        txtName = addExerciseDialog.findViewById(R.id.txtName);
        txtIntroduction = addExerciseDialog.findViewById(R.id.txtIntroduction);
        txtGuide = addExerciseDialog.findViewById(R.id.txtGuide);


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
                    System.out.println("dialog: " +exercise );
                    mOnInputListener.sendInput(exercise);
                    getDialog().dismiss();
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
        Exercise res = new Exercise(name,users,imageUrl.toString(),introduction,guide,null);
        if(name == null || name.trim().isEmpty())
        {
            txtNote.setText("please, fill all information");
            return null;
        }
        if(introduction == null || introduction.trim().isEmpty())
        {
            txtNote.setText("please, fill all information");
            return null;
        }
        if(guide == null || guide.trim().isEmpty())
        {
            txtNote.setText("please, fill all information");
            return null;
        }
        if(users == null)
        {
            txtNote.setText("No account, please sign in");
            return null;
        }
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
                    Picasso.get().load(imageUrl.toString()).into(img);
                }
            }
        }
    }


}
