package com.example.foodiedelivery.interfaces;

import androidx.lifecycle.LiveData;
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
    void insertRestaurants(Restaurant...restaurants);

    @Insert(onConflict =OnConflictStrategy.REPLACE)
    Long[] insertRestaurantsFromList(List<Restaurant> restaurants);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertOneRestaurant(Restaurant restaurant);

    @Query("SELECT * FROM restaurants")
    LiveData<List<Restaurant>> GetAllRestaurants();


    @Delete
    int deleteOneRestaurant(Restaurant restaurant);

    @Delete
    int deleteRestaurantsFromList(List<Restaurant> restaurants);

    @Query("DELETE FROM restaurants")
    void deleteAllRestaurants();

    @Query("DELETE FROM restaurants WHERE id = :ResId")
    int deleteRestaurantWithId(int ResId);

}
