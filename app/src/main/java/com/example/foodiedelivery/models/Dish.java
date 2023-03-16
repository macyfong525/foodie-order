package com.example.foodiedelivery;

public class Dish {
    private String name;
    private String price;
    private Integer pic;

    public Dish(String name, String price, Integer pic) {
        this.name = name;
        this.price = price;
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Integer getPic() {
        return pic;
    }

    public void setPic(Integer pic) {
        this.pic = pic;
    }
}
