package com.example.foodiedelivery.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.example.foodiedelivery.adapters.MenuAdapter;
import com.example.foodiedelivery.databinding.FragmentMenuBinding;
import com.example.foodiedelivery.models.CartViewModel;
import com.example.foodiedelivery.models.Dish;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * <p>
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment implements MenuAdapter.DishInterface {


    private static final String TAG = "MenuFragment";
    List<Dish> Dishes = new ArrayList<>();
    FragmentMenuBinding fragmentMenuBinding;
    private MenuAdapter menuAdapter;
    private CartViewModel cartViewModel;

    public MenuFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentMenuBinding = FragmentMenuBinding.inflate(inflater, container, false);
        return fragmentMenuBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        menuAdapter = new MenuAdapter(this);
        fragmentMenuBinding.recycleViewMenu.setAdapter(menuAdapter);
        fragmentMenuBinding.recycleViewMenu.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        fragmentMenuBinding.recycleViewMenu.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL));

        addData();
        menuAdapter.submitList(Dishes);

        Log.d("MenuFragment", "Start");
        cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);

    }


    private void addData() {
        Dishes.add(new Dish(1, 1, "Extra Large Meat Lovers", 15.99));
        Dishes.add(new Dish(2, 1, "Extra Large Supreme", 15.99));
        Dishes.add(new Dish(3, 1, "Extra Large Pepperoni", 13.99));
        Dishes.add(new Dish(4, 1, "Extra Large BBQ Chicken &amp; Bacon.", 14.99));
        Dishes.add(new Dish(5, 1, "Extra Large 5 Cheese.", 15.99));
        Dishes.add(new Dish(6, 1, "Extra Large Pepperoni Slice,Slice.", 15.99));
    }


    @Override
    public void addItem(Dish dish) {
        boolean isAdded = cartViewModel.addItemToCart(dish);
        if (isAdded) {
            Snackbar.make(requireView(), dish.getName() + " added to cart", Snackbar.LENGTH_LONG)
                    .show();
        } else {
            Snackbar.make(requireView(), dish.getName() + " exceeds the max quantity in cart",
                    Snackbar.LENGTH_LONG).show();
        }

    }
}