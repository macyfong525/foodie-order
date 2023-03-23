package com.example.foodiedelivery.db;

import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.foodiedelivery.interfaces.UserDao;
import com.example.foodiedelivery.models.User;

@Database(entities = {User.class},version=1,exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}

