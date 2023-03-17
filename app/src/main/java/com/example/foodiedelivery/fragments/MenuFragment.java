package com.example.foodiedelivery.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.foodiedelivery.R;
import com.example.foodiedelivery.adapters.DishAdapter;
import com.example.foodiedelivery.databinding.FragmentRestaurant1ResultBinding;
import com.example.foodiedelivery.models.Dish;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 *
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment {
    List<Dish> Dishes = new ArrayList<>();
    LayoutInflater inflaterThis;
    FragmentRestaurant1ResultBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addData();
    }
    private void addData() {
        Dishes.add(new Dish("Extra Large Meat Lovers","15.99"));
        Dishes.add(new Dish("Extra Large Supreme","15.99"));
        Dishes.add(new Dish("Extra Large Pepperoni","13.99"));
        Dishes.add(new Dish("Extra Large BBQ Chicken &amp; Bacon.","14.99"));
        Dishes.add(new Dish("Extra Large 5 Cheese.","15.99"));
        Dishes.add(new Dish("Extra Large Pepperoni Slice,Slice.","15.99"));
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       return inflater.inflate(R.layout.fragment_restaurant1_result, container, false);
    }
}