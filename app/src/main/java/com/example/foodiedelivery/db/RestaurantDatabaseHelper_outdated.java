package com.example.foodiedelivery.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.foodiedelivery.models.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDatabaseHelper_outdated extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "restaurant.db";
    private static final int DATABASE_VERSION = 1;

    // Table info starts here
    public static final String TABLE_NAME = "restaurant";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_LOCATION = "location";
    public static final String COLUMN_IMAGE_URL = "image_url";
    // Table info stops here

    //SQL statement to create the table
    private static final String CREATE_RESTAURANT_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT NOT NULL, " +
                    COLUMN_LOCATION + " TEXT NOT NULL, " +
                    COLUMN_IMAGE_URL + " TEXT" +
                    ")";



    private static RestaurantDatabaseHelper_outdated instance;

    private static synchronized RestaurantDatabaseHelper_outdated getHelper(Context context){
        if(instance == null)
            instance = new RestaurantDatabaseHelper_outdated(context);
        return instance;
    }

    public RestaurantDatabaseHelper_outdated(Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_RESTAURANT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db){
        super.onOpen(db);
    }

    public long insertRestaurant(Restaurant restaurant){
        ContentValues values = new ContentValues();
        values.put("name", restaurant.getResName());
        values.put("location", restaurant.getLocation());
        values.put("image_url", restaurant.getImageUrl());

        SQLiteDatabase db = getWritableDatabase();
        long id = db.insert("restaurant", null, values);
        db.close();
        return id;
    }

    public List<Restaurant> getAllRestaurants(){
        List<Restaurant> restaurants = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
//        Cursor cursor = db.query(RestaurantDatabaseHelper.TABLE_NAME)
        while (cursor.moveToNext()){
            int id = cursor.getInt(cursor.getColumnIndex("_id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String location = cursor.getString(cursor.getColumnIndex("location"));
            String imageUrl = cursor.getString(cursor.getColumnIndex("image_url"));

            Restaurant restaurant = new Restaurant(name, location, imageUrl);
            restaurants.add(restaurant);
        }
        cursor.close();
        db.close();
        return restaurants;
    }
}
