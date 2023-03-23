package com.example.foodiedelivery.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.foodiedelivery.repositories.CartRepo;

import java.util.List;

public class CartViewModel extends ViewModel {

    CartRepo cartRepo = new CartRepo();

    public LiveData<List<CartItem>> getCart() {
        return cartRepo.getCart();
    }

    public boolean addItemToCart(Dish dish) {
        return cartRepo.addItemToCart(dish);
    }

    public void removeItemFromCart(CartItem cartItem) {
        cartRepo.removeItemFromCart(cartItem);
    }

    public void changeQuantity(CartItem cartItem, int quantity) {
        cartRepo.changeQuantity(cartItem, quantity);
    }

    public LiveData<Double> getTotalPrice() {
        return cartRepo.getTotalPrice();
    }

    public void resetCart() {
        cartRepo.initCart();
    }

}