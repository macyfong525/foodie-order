package com.example.foodiedelivery.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.foodiedelivery.interfaces.MenuDao;
import com.example.foodiedelivery.models.Dish;


@Database(entities = {Dish.class},version=1,exportSchema = false)
public abstract class MenuDatabase extends RoomDatabase {
    public abstract MenuDao menuDao();
}
