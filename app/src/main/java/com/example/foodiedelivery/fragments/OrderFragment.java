package com.example.foodiedelivery.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.room.Room;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.foodiedelivery.R;
import com.example.foodiedelivery.adapters.OrderAdapter;
import com.example.foodiedelivery.db.FoodieDatabase;
import com.example.foodiedelivery.interfaces.DishDao;
import com.example.foodiedelivery.interfaces.RestaurantDao;
import com.example.foodiedelivery.models.Dish;
import com.example.foodiedelivery.repositories.RestaurantRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class OrderFragment extends Fragment {

   ListView listViewOrder;

   FoodieDatabase fdb;
   RestaurantDao restaurantDao;
   DishDao dishDao;
   RestaurantRepository restaurantRepository;

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      // Inflate the layout for this fragment
      return inflater.inflate(R.layout.fragment_order, container, false);
   }

   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      listViewOrder = view.findViewById(R.id.listViewOrder);
      OrderAdapter orderAdapter = new OrderAdapter();



   }
}