package com.example.foodiedelivery.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.foodiedelivery.R;
import com.example.foodiedelivery.adapters.CartAdapter;
import com.example.foodiedelivery.databinding.FragmentCartBinding;
import com.example.foodiedelivery.models.CartItem;
import com.example.foodiedelivery.models.CartViewModel;

import java.util.List;

public class CartFragment extends Fragment implements CartAdapter.CartInterface{

    CartViewModel cartViewModel;
    FragmentCartBinding fragmentCartBinding;

    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentCartBinding = FragmentCartBinding.inflate(inflater, container, false);
        return fragmentCartBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CartAdapter cartAdapter = new CartAdapter(this);
        fragmentCartBinding.recycleViewCart.setAdapter(cartAdapter);
        fragmentCartBinding.recycleViewCart.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));

        cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);
        cartViewModel.getCart().observe(getViewLifecycleOwner(), (List<CartItem> cartItems) -> {
            cartAdapter.submitList(cartItems);
            fragmentCartBinding.buttonPlaceOrder.setEnabled(cartItems.size()>0);
        });

        cartViewModel.getTotalPrice().observe(getViewLifecycleOwner(), new Observer<Double>() {
            @Override
            public void onChanged(Double aDouble) {
                fragmentCartBinding.textViewOrderTotal.setText(String.format("Total: $%.2f",aDouble));
            }
        });

        fragmentCartBinding.buttonPlaceOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cartViewModel.resetCart();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new OrderFragment()).commit();
            }
        });

    }

    @Override
    public void deleteItem(CartItem cartItem) {
        Log.d("Cart", "deleteItem: "+ cartItem.getDish().getName());
        cartViewModel.removeItemFromCart(cartItem);
    }

    @Override
    public void changeQuantity(CartItem cartItem, int quantity) {
        cartViewModel.changeQuantity(cartItem, quantity);
    }
}