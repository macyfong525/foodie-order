package com.example.foodiedelivery.models;

import java.util.ArrayList;

public class Restaurant {

    private int id;
    private String resName;
    private String location;
    private String imageUrl;

    public Restaurant(int id, String resName, String location, String imageUrl) {
        this.id = id;
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
        resName = resName;
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
