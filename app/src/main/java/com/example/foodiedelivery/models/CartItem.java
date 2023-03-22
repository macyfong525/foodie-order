package com.example.foodiedelivery.models;

import java.util.Objects;

public class CartItem {

    private int quantity;
    private Dish dish;

    public CartItem(int quantity, Dish dish) {
        this.quantity = quantity;
        this.dish = dish;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return getQuantity() == cartItem.getQuantity() && getDish().equals(cartItem.getDish());
    }

}
