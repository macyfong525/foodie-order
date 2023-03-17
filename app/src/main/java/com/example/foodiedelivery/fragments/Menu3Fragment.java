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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Menu3Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Menu3Fragment extends Fragment {
    List<Dish> Dishes = new ArrayList<>();
    //LayoutInflater inflaterThis;
    FragmentRestaurant1ResultBinding binding;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        addData();
        binding =FragmentRestaurant1ResultBinding.inflate(getLayoutInflater());
        DishAdapter dishAdapter = new DishAdapter(getContext(),Dishes);
        ListView listView3 = view.findViewById(R.id.listViewRes3);
        listView3.setAdapter(dishAdapter);
        binding.getRoot();
    }

    private void addData() {
        Dishes.add(new Dish("Cowboy (Baking Required), Our Original Crust topped with Traditional Red Sauce","11.99"));
        Dishes.add(new Dish("Papa's Favorite® (Baking Required)","16.99"));
        Dishes.add(new Dish("Murphy's Combo (Baking Required)","14.99"));
        Dishes.add(new Dish("Chicken Garlic (Baking Required)","14.99"));
        Dishes.add(new Dish("Rancher,Our Original Crust topped with Traditional Red Sauce","16.99"));
        Dishes.add(new Dish("Thai Chicken(Baking Required)","15.99"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu3, container, false);
    }
}