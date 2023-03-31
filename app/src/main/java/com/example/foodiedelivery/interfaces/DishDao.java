package com.example.foodiedelivery.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodiedelivery.models.Dish;

import java.util.List;

@Dao
public interface DishDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertDish(Dish...dishes);

    @Insert(onConflict =OnConflictStrategy.REPLACE)
    Long[] insertDishesFromList(List<Dish> dishes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOneDish(Dish dish);

    @Query("SELECT * FROM dishes")
    List<Dish> GetAllDish();

    @Delete
    int deleteOneDish(Dish dish);


    @Query("DELETE FROM dishes")
    void deleteAllDishes();

    @Query("DELETE FROM dishes WHERE id = :dishId")
    int deleteDishById(int dishId);

    @Query("SELECT * FROM dishes WHERE id = :dishId")
    LiveData<Dish> getDishById(int dishId);

    @Query("SELECT * FROM dishes WHERE resid= :restaurantId")
    LiveData<List<Dish>> getDishesByRestaurantId(int restaurantId);

}
