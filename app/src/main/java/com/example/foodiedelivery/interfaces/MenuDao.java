package com.example.foodiedelivery.interfaces;

import android.view.Menu;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodiedelivery.models.Dish;
import com.example.foodiedelivery.models.User;

import java.util.List;

@Dao
public interface MenuDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMenus(Dish...dishes);

    @Insert(onConflict =OnConflictStrategy.REPLACE)
    Long[] insertMenusFromList(List<Dish> dishes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOneMenu(Dish dish);

    @Query("SELECT * FROM dishes")
    List<Dish> GetAllMenus();

    @Delete
    int deleteOneMenu(Dish dish);

    @Delete
    int deleteMenusFromList(List<Dish> dishes);

    @Query("DELETE FROM dishes")
    void deleteAllMenus();

    @Query("DELETE FROM dishes WHERE id = :MenuId")
    int deleteWithId(int MenuId);

}
