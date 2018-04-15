package com.example.aaronverones.patients;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class PatientProfileActivity extends AppCompatActivity {

    public static final int REQUEST_CAPTURE = 1;
    public static final int REQUEST_PERMISSIONS = 2;
    public static final String photo_suffix = "_profile_picture.jpg";
    ImageView profilePicture;
    String name;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile);

        Intent intent = getIntent();
        name = intent.getStringExtra(PatientsListAdapter.EXTRA_NAME);
        id = intent.getStringExtra(PatientsListAdapter.EXTRA_ID);

        TextView nameTextView = findViewById(R.id.nameTextView);
        TextView idTextView = findViewById(R.id.idTextView);
        nameTextView.setText(name);
        idTextView.setText(id);

        Button takePhoto = findViewById(R.id.takePhotoButton);
        profilePicture = findViewById(R.id.profilePictureImageView);
        loadProfilePicture();

        if (!hasCamera()) {
            takePhoto.setEnabled(false);
        }
    }

    private boolean hasCamera() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    public void onTakePhotoClicked(View v) {
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            takeProfilePicture();
        } else {
            String[] permissionRequest = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            requestPermissions(permissionRequest, REQUEST_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                takeProfilePicture();
            } else {
                Toast.makeText(this, R.string.cannot_open_camera, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void takeProfilePicture() {
        Uri pictureUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName()+".provider", createImageFile());
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
        intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        startActivityForResult(intent, REQUEST_CAPTURE);
    }

    private File createImageFile() {
        File picturesDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return new File(picturesDirectory, id+photo_suffix);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAPTURE && resultCode == RESULT_OK) {
            loadProfilePicture();
        }
    }

    private void loadProfilePicture() {
        InputStream inputStream;
        try {
            inputStream = getContentResolver().openInputStream(Uri.fromFile(createImageFile()));
            Bitmap profileBitmap = BitmapFactory.decodeStream(inputStream);
            profilePicture.setImageBitmap(profileBitmap);
        } catch (FileNotFoundException e) {
            // when a user has not set a photo, just return.
        }
    }
}
