package com.example.chatlayoytex.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.example.chatlayoytex.R;
import com.example.chatlayoytex.utils.Loader;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.yalantis.ucrop.UCrop;

import java.io.File;
import java.io.IOException;

public class TestActivity extends AppCompatActivity {

    Button upload;
    ImageView image;
    Loader loader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        upload = findViewById(R.id.upload);
        image = findViewById(R.id.image);
        loader = new Loader(this);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.create(TestActivity.this)
                        .returnMode(ReturnMode.ALL) // set whether pick and / or camera action should return immediate result or not.
                        .folderMode(true) // folder mode (false by default)
                        .toolbarFolderTitle("Folder") // folder selection title
                        .toolbarImageTitle("Tap to select") // image selection title
                        .toolbarArrowColor(Color.BLACK) // Toolbar 'up' arrow color
                        .includeVideo(false) // Show video on image picker
                        .single() // single mode
                        .showCamera(true) // show camera or not (true by default)
                        .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                        .start();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            // or get a single image only
            Image image = ImagePicker.getFirstImageOrNull(data);
            crop(Uri.fromFile(new File(image.getPath())));
        } else if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            final Uri resultUri = UCrop.getOutput(data);
            uploadImage(resultUri);
        } else if (resultCode == UCrop.RESULT_ERROR) {
            final Throwable cropError = UCrop.getError(data);
        }

    }

    private void uploadImage(Uri uri) {
        loader.show();

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profile").child(FirebaseAuth.getInstance().getUid()+".png");
// Register observers to listen for when the download is done or if it fails
        storageReference.putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                // Continue with the task to get the download URL
                return storageReference.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    Log.i("djshvfhsd", "onComplete: "+downloadUri);
                    Picasso.get().load(downloadUri).into(image);
                    loader.dismiss();

                } else {
                    // Handle failures
                    // ...
                }
            }
        });

    }

    private void crop(Uri fromFile) {


        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + getResources().getString(R.string.app_name));
        File file = null;

        Log.i("bjbdjbdfkvb", "crop: 1" + dir.exists());
        if (!dir.exists()) {
            dir.mkdir();
            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + getResources().getString(R.string.app_name) + "/temp.png");
            Log.i("bjbdjbdfkvb", "crop: 2" + file.exists());

            if (!file.exists()) {

                try {
                    file.createNewFile();
                    Log.i("bjbdjbdfkvb", "crop: 2 createNewFile");
                } catch (IOException e) {
                    Log.i("bjbdjbdfkvb", "IOException " + e.toString());
                }
            }
        } else {

            file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + getResources().getString(R.string.app_name) + "/temp.png");
            Log.i("bjbdjbdfkvb", "crop: 3" + file.exists());

            if (!file.exists()) {
                try {
                    file.createNewFile();
                    Log.i("bjbdjbdfkvb", "crop: 3 createNewFile");

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.i("bjbdjbdfkvb", "IOException " + e.toString());
                }
            }
        }


        UCrop.of(fromFile, Uri.fromFile(file))
                .withAspectRatio(1, 1)
                .start(TestActivity.this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}
