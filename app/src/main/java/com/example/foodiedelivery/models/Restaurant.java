package com.example.foodiedelivery.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity(tableName = "restaurants")
public class Restaurant {
    @NonNull
    @PrimaryKey (autoGenerate = true)
    @ColumnInfo(name="id")
    private int id;
    @ColumnInfo(name="resname")
    private String resName;
    @ColumnInfo(name="location")
    private String location;
    @ColumnInfo(name="imageurl")
    private String imageUrl;

    @Ignore
    public Restaurant() {
    }

    public Restaurant(String resName, String location, String imageUrl) {
//        this.id = id;
        this.resName = resName;
        this.location = location;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
