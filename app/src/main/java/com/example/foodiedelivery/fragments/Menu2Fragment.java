package com.example.foodiedelivery.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.foodiedelivery.R;
import com.example.foodiedelivery.adapters.DishAdapter;
import com.example.foodiedelivery.databinding.FragmentRestaurant1ResultBinding;
import com.example.foodiedelivery.models.Dish;

import java.util.ArrayList;
import java.util.List;


public class Menu2Fragment extends Fragment {
    List<Dish> Dishes = new ArrayList<>();    
    FragmentRestaurant1ResultBinding binding;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addData();
        binding =FragmentRestaurant1ResultBinding.inflate(getLayoutInflater());
        DishAdapter dishAdapter = new DishAdapter(getContext(),Dishes);
        ListView listView2 = view.findViewById(R.id.listViewRes2);
        listView2.setAdapter(dishAdapter);
        binding.getRoot();
    }
    private void addData() {

        Dishes.add(new Dish("Double Hamburger,Grilled or fried patty on a bun.","2.25"));
        Dishes.add(new Dish("Turkey with Cheese Deluxe Sandwich","3.0"));
        Dishes.add(new Dish("Ham Deluxe Sandwich","3.25"));
        Dishes.add(new Dish("Salmon Sandwich,Subtle rich fish sandwich.","3.25"));
        Dishes.add(new Dish("Pork Chop Sandwich,Thick cut of meat from a pig typically cut from the spine.","4.50"));
        Dishes.add(new Dish("Liver Sandwich,Sandwich with liver meat typically made as a spread or a patty.","3.75"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu2, container, false);
    }
}