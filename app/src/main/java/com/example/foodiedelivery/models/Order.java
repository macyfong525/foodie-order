package com.example.foodiedelivery.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "orders",
        foreignKeys = @ForeignKey(entity = User.class,
                parentColumns = "id",
                childColumns = "userId"))
public class Order {
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "userId")
    private int userId;

    @ColumnInfo(name = "order_total")
    private double orderTotal;

    @ColumnInfo(name = "order_date")
    private long orderDate;

    public Order() {
    }

    public Order(int userId, double orderTotal) {
        this.orderDate = System.currentTimeMillis();
        this.userId = userId;
        this.orderTotal = orderTotal;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public long getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(long orderDate) {
        this.orderDate = orderDate;
    }
}
