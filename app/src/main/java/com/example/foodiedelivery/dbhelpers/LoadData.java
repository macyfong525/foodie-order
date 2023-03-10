package com.example.foodiedelivery.dbhelpers;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foodiedelivery.R;

import java.io.InputStream;

public class LoadData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            // open the sample data file as an InputStream
            InputStream inputStream = getResources().openRawResource(R.raw.restaurants);
            InputStream inputStreamMenu = getResources().openRawResource(R.raw.restaurant_menus);
            DbHelper dbHelper = new DbHelper(getApplicationContext());
            dbHelper.importResDataFromCSV(inputStream);
            dbHelper.importMenuDataFromCSV(inputStreamMenu);
            Log.d("LOADDATA", "insertRestaurant: finish");
        } catch (Exception e) {
            // handle the error
            e.printStackTrace();
        }
    }
}
