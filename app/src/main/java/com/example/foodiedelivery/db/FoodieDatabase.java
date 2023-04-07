package com.example.foodiedelivery.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.foodiedelivery.interfaces.DishDao;
import com.example.foodiedelivery.interfaces.OrderDao;
import com.example.foodiedelivery.interfaces.RestaurantDao;
import com.example.foodiedelivery.interfaces.UserDao;
import com.example.foodiedelivery.models.Dish;
import com.example.foodiedelivery.models.Order;
import com.example.foodiedelivery.models.Restaurant;
import com.example.foodiedelivery.models.User;

@Database(entities = {Dish.class, Restaurant.class, User.class, Order.class},version=5,exportSchema = false)
public abstract class FoodieDatabase extends RoomDatabase {
    public abstract DishDao menuDao();
    public abstract RestaurantDao RestaurantDao();
    public abstract UserDao userDao();
    public abstract OrderDao orderDao();
}
