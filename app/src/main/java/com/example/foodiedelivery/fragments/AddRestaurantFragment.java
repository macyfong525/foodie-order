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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import com.example.foodiedelivery.R;
import com.example.foodiedelivery.db.FoodieDatabase;
import com.example.foodiedelivery.db.RestaurantDatabaseHelper;
import com.example.foodiedelivery.interfaces.DishDao;
import com.example.foodiedelivery.interfaces.RestaurantDao;
import com.example.foodiedelivery.models.Dish;
import com.example.foodiedelivery.models.Restaurant;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class AddRestaurantFragment extends Fragment {
    Uri imgUri;
    String resImageUrl;
    StorageReference storageRef;
    //   ProgressDialog processDialog; (deprecated)
    ProgressBar progressBar;
    Button btnAddRes;
    ImageView previewImg;
    FoodieDatabase fdb;
    RestaurantDao restaurantDao;
    DishDao dishDao;
    LinearLayout lLdishes;
//    private RestaurantDatabaseHelper helper;
    private Button dishBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_addnewrestaurant, container, false);
        fdb = Room.databaseBuilder(requireContext(), FoodieDatabase.class, "foodie.db").build();
        restaurantDao = fdb.RestaurantDao();
        dishDao = fdb.menuDao();

        EditText editTxtResName = rootView.findViewById(R.id.editTxtResName);
        EditText editTxtLocation = rootView.findViewById(R.id.editTxtLocation);

        btnAddRes = rootView.findViewById(R.id.btnAddRes);
        btnAddRes.setEnabled(false);
        Button btnSelectImg = rootView.findViewById(R.id.btnSelectImage);

        btnSelectImg.setOnClickListener(v -> selectImage());
        btnAddRes.setOnClickListener(v -> addNewRestaurant(editTxtResName, editTxtLocation));
        progressBar= rootView.findViewById(R.id.progressBarAddRes);

        previewImg = rootView.findViewById(R.id.imageViewPreview);

        dishBtn = rootView.findViewById(R.id.btnAddDish);
        dishBtn.setOnClickListener(v-> addDishFields());
        lLdishes=rootView.findViewById(R.id.llDishes);


        return rootView;
    }

    private void addDishFields() {
        View dishFields = LayoutInflater.from(getContext()).inflate(R.layout.layout_dish_fields, (ViewGroup) getView(), false);
        lLdishes.addView(dishFields);
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
                        Log.d("Restaurant", "addNewRestaurant: " + resImageUrl);
                        //begin the db part
                        Executor executor = Executors.newSingleThreadExecutor();
                        executor.execute(()->{
                            long restaurantId = restaurantDao.insertOneRestaurant(new Restaurant(editTxtResName.getText().toString(), editTxtLocation.getText().toString(), resImageUrl));
                            if (restaurantId < 0){
                                getActivity().runOnUiThread(()-> Toast.makeText(getContext(), "Error inserting data into table", Toast.LENGTH_SHORT).show());
                            }
                            addDishToRestaurant(restaurantId);
                            getActivity().runOnUiThread(()->{
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Successfully uploaded", Toast.LENGTH_SHORT).show();
                                btnAddRes.setEnabled(false);

                                editTxtResName.setText("");
                                editTxtLocation.setText("");
                                lLdishes.removeAllViews();
                            });
                        });
                    }).addOnFailureListener(e->{
                        btnAddRes.setEnabled(false);
                        Toast.makeText(getContext(), "Error when selecting an image", Toast.LENGTH_SHORT).show();
                    });
                }).addOnFailureListener(e -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getContext(), "Failed to upload", Toast.LENGTH_SHORT).show();
                });
    }
    private void addDishToRestaurant(long restaurantId) {
        Log.d("addDishToRestaurant", "addDishToRestaurant: " + restaurantId);
        int childCount = lLdishes.getChildCount();
        for (int i =0; i< childCount; i++){
            View child = lLdishes.getChildAt(i);
            EditText nameField = (EditText) child.findViewById(R.id.editTxtDishName);
            EditText priceField = (EditText) child.findViewById(R.id.editTxtDishPrice);
            String dishName = nameField.getText().toString();
            double price = Double.parseDouble(priceField.getText().toString());
            Log.d("Add Restaurant Fragment", "Dish name and price " + dishName + price);
            dishDao.insertDish(new Dish(restaurantId,dishName, price));

        }
    }
}