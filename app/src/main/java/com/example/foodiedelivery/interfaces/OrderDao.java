package com.example.foodiedelivery.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.foodiedelivery.models.Dish;
import com.example.foodiedelivery.models.Order;

import java.util.List;

@Dao
public interface OrderDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertOrder(Order...order);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertOneOrder(Order order);

    @Query("SELECT * FROM orders WHERE id = :id")
    Order getById(long id);

    @Query("SELECT * FROM orders WHERE userId = :userId")
    List<Order> getByUserId(int userId);

    @Query("SELECT * FROM orders")
    List<Order> getAll();

    @Delete
    void delete(Order order);

    @Update
    void update(Order order);
}
