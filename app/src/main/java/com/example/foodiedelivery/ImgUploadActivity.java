package com.example.foodiedelivery;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;

public class ImgUploadActivity extends AppCompatActivity {
    int GET_FROM_GALLERY = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_upload);
        Button btnUploadImg = findViewById(R.id.buttonUpload);

        Intent UploadIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);



        btnUploadImg.setOnClickListener(v ->{
            startActivityForResult(new Intent(Intent.ACTION_PICK
                    , android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                    , GET_FROM_GALLERY);
        });
    }



}