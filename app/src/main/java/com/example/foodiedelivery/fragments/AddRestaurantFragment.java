package com.example.foodiedelivery.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.example.foodiedelivery.R;
import com.example.foodiedelivery.db.RestaurantDatabaseHelper;
import com.example.foodiedelivery.models.Restaurant;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class AddRestaurantFragment extends Fragment {
    Uri imgUri;
    String resImageUrl;
    StorageReference storageRef;
    //   ProgressDialog processDialog; (deprecated)
    ProgressBar progressBar;
    Button btnAddRes;
    ImageView previewImg;

    private RestaurantDatabaseHelper helper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_addnewrestaurant, container, false);

        EditText editTxtResName = rootView.findViewById(R.id.editTxtResName);
        EditText editTxtLocation = rootView.findViewById(R.id.editTxtLocation);

        btnAddRes = rootView.findViewById(R.id.btnAddRes);
        btnAddRes.setEnabled(false);
        Button btnSelectImg = rootView.findViewById(R.id.btnSelectImage);

        btnSelectImg.setOnClickListener(v -> selectImage());
        btnAddRes.setOnClickListener(v -> addNewRestaurant(editTxtResName, editTxtLocation));
        progressBar= rootView.findViewById(R.id.progressBarAddRes);

        previewImg = rootView.findViewById(R.id.imageViewPreview);


        return rootView;
    }

    private void selectImage() {
        selectImageLauncher.launch("image/*");
    }

    private final ActivityResultLauncher<String> selectImageLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(),
                    result -> {
                        if (result != null) {
                            imgUri = result;
                            btnAddRes.setEnabled(true);
                            previewImg.setImageURI(imgUri);
                        }
                    });


    private void addNewRestaurant(EditText editTxtResName, EditText editTxtLocation) {

        Log.d("Tag", "Inside addNewRestaurant method now");
        progressBar.setVisibility(View.VISIBLE);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM-dd_HH_mm_ss", Locale.CANADA);
        Date now = new Date();
        String fileName = editTxtResName.getText().toString() + "_" + formatter.format(now);

        storageRef = FirebaseStorage.getInstance().getReference("images/" + fileName);
        storageRef.putFile(imgUri)
                .addOnSuccessListener(taskSnapshot -> {
                    previewImg.setImageURI(null);

                    //get url inside of OnSuccessListener
                    storageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        resImageUrl = uri.toString();
                    }).addOnFailureListener(e->{
                        btnAddRes.setEnabled(false);
                        Toast.makeText(getContext(), "Error when selecting an image", Toast.LENGTH_SHORT).show();

                    });

                    //begin the db part
                    helper = new RestaurantDatabaseHelper(getContext());
                    Restaurant res = new Restaurant(editTxtResName.getText().toString(), editTxtLocation.getText().toString(), resImageUrl);
                    long resId = helper.insertRestaurant(res);
                    if (resId == -1){
                        Toast.makeText(getContext(), "Error inserting data into table", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Successfully uploaded", Toast.LENGTH_SHORT).show();
                    btnAddRes.setEnabled(false);
                    editTxtResName.setText("");
                    editTxtLocation.setText("");

                }).addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Failed to upload", Toast.LENGTH_SHORT).show();
                });


    }
}