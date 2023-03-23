package com.example.foodiedelivery.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.foodiedelivery.interfaces.RestaurantDao;
import com.example.foodiedelivery.models.Restaurant;


@Database(entities = {Restaurant.class},version=1,exportSchema = false)
public abstract class RestaurantDatabase extends RoomDatabase {
    public abstract RestaurantDao restaurantDao();
}
