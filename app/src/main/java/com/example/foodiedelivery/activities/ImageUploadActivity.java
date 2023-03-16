package com.example.foodiedelivery.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.foodiedelivery.databinding.ActivityImageUploadBinding;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ImageUploadActivity extends AppCompatActivity {
    // KENNNNNNNN
    ActivityImageUploadBinding binding;
    Uri imgUri;
    StorageReference storageRef;
    ProgressDialog processDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityImageUploadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.selectImgBtn.setOnClickListener(v -> selectImage());



    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data!= null && data.getData() != null){
            imgUri = data.getData();
            binding.uploadImgView.setImageURI(imgUri);
            binding.uploadImgBtn.setOnClickListener(v -> uploadImg());
        }
    }

    private void uploadImg() {
        Log.d("tag", "Inside uploadImg");

        processDialog = new ProgressDialog(this);
        processDialog.setTitle("Uploading File...");
        processDialog.show();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM-dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        String fileName = formatter.format(now);
        storageRef = FirebaseStorage.getInstance().getReference("images/" + fileName);
        storageRef.putFile(imgUri)
                .addOnSuccessListener(taskSnapshot -> {
                    binding.uploadImgView.setImageURI(null);
                    Toast.makeText(this, "Successfully uploaded", Toast.LENGTH_SHORT).show();
                    if(processDialog.isShowing())
                        processDialog.dismiss();
                    Log.d("tag", "Inside addOnSuccessListener");
                }).addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to upload", Toast.LENGTH_SHORT).show();
                    processDialog.dismiss();
                    Log.d("tag", "Inside addOnFailureListener");
                });

    }
}
