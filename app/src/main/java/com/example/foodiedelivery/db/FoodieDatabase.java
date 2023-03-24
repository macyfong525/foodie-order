package com.example.foodiedelivery.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.foodiedelivery.interfaces.MenuDao;
import com.example.foodiedelivery.interfaces.RestaurantDao;
import com.example.foodiedelivery.interfaces.UserDao;
import com.example.foodiedelivery.models.Dish;
import com.example.foodiedelivery.models.Restaurant;
import com.example.foodiedelivery.models.User;

@Database(entities = {Dish.class, Restaurant.class, User.class},version=1,exportSchema = false)
public abstract class FoodieDatabase extends RoomDatabase {
    public abstract MenuDao menuDao();
    public abstract RestaurantDao RestaurantDao();
    public abstract UserDao userDao();
}
