package com.example.foodiedelivery;

public class Restaurant {
    private String ResName;
    private Integer ResIcon;

    public Restaurant(String resName, Integer resIcon) {
        ResName = resName;
        ResIcon = resIcon;
    }

    public String getResName() {
        return ResName;
    }

    public void setResName(String resName) {
        ResName = resName;
    }

    public Integer getResIcon() {
        return ResIcon;
    }

    public void setResIcon(Integer resIcon) {
        ResIcon = resIcon;
    }
}
