package com.example.foodiedelivery.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CartViewModel extends ViewModel {
    private MutableLiveData<CartItem> cartItem = new MutableLiveData<>();

    public void setCartItem(CartItem cartItem) {
        this.cartItem.setValue(cartItem);
    }

    public LiveData<CartItem> getCartItem() {
        return cartItem;
    }

}
