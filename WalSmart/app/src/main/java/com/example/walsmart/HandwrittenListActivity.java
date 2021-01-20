package com.example.walsmart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

public class HandwrittenListActivity extends AppCompatActivity {
    public static final int CAMERA_PERMISSION_CODE = 800;
    public static final int CAMERA_REQUEST_CODE = 801;
    ImageView mImageView;
    Button cameraBtn;
    Button detectBtn;
    Bitmap imageBitmap;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handwritten_list);

        mImageView = findViewById(R.id.mImageView);
        cameraBtn = findViewById(R.id.cameraButton);
        detectBtn = findViewById(R.id.detectButton);
        textView = findViewById(R.id.textView);

        cameraBtn.setOnClickListener(view -> {
            //openCamera();
            askPermission();
        });
        detectBtn.setOnClickListener(view -> detectImg());

    }

    private void detectImg() {
        Log.d("Debug", "Msg: recognized image bitmap " + imageBitmap);
        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(imageBitmap);
        Log.d("Debug", "Msg: recognized image " + image);
        FirebaseVisionTextRecognizer textRecognizer =
                FirebaseVision.getInstance().getOnDeviceTextRecognizer();
        Log.d("Debug", "Msg: recognized textRecognizer " + textRecognizer);
        textRecognizer.processImage(image).addOnSuccessListener(new OnSuccessListener<FirebaseVisionText>() {
            @Override
            public void onSuccess(FirebaseVisionText firebaseVisionText) {
                //processTxt(firebaseVisionText);
                Log.d("Debug", "Msg: recognized vision text " + firebaseVisionText);
                Log.d("Debug", "Msg: recognized text " + firebaseVisionText.getText());
                textView.setText(firebaseVisionText.getText());
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("Debug", "Msg: failure ");

            }
        });


    }

    private void askPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        } else {
            launchCamera();
        }
    }

    private void launchCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            imageBitmap = (Bitmap) data.getExtras().get("data");
            mImageView.setImageBitmap(imageBitmap);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length < 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchCamera();
            } else {
                Toast.makeText(this, "You need to allow for camera usage", Toast.LENGTH_SHORT).show();
            }
        }
    }
}