package com.example.foodiedelivery.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodiedelivery.models.Restaurant;
import com.example.foodiedelivery.models.User;

import java.util.List;

@Dao
public interface RestaurantDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertStudents(Restaurant...restaurants);

    @Insert(onConflict =OnConflictStrategy.REPLACE)
    Long[] insertStudentsFromList(List<Restaurant> restaurants);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOneRestaurant(Restaurant restaurant);

    @Query("SELECT * FROM restaurants")
    List<Restaurant> GetAllRestaurants();

    @Delete
    int deleteOneRestaurant(Restaurant restaurant);

    @Delete
    int deleteRestaurantsFromList(List<Restaurant> restaurants);

    @Query("DELETE FROM restaurants")
    void deleteAllRestaurants();

    @Query("DELETE FROM restaurants WHERE id = :ResId")
    int deleteRestaurantWithId(int ResId);
}
