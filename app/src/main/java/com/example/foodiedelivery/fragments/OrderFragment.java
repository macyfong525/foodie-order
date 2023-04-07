package com.example.foodiedelivery.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import com.example.foodiedelivery.adapters.OrderAdapter;
import com.example.foodiedelivery.databinding.FragmentOrderBinding;
import com.example.foodiedelivery.db.FoodieDatabase;
import com.example.foodiedelivery.interfaces.OrderDao;
import com.example.foodiedelivery.models.Order;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class OrderFragment extends Fragment {

    FragmentOrderBinding binding;
    List<Order> orderList;
    SharedPreferences sharedPreferences;
    FoodieDatabase fdb;
    int userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fdb = Room.databaseBuilder(requireActivity().getApplicationContext(), FoodieDatabase.class, "foodie.db").build();
        binding = FragmentOrderBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        OrderDao orderDao = fdb.orderDao();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        userId = sharedPreferences.getInt("userId", -1);
        if (userId == -1) {
            return;
        }

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                orderList = orderDao.getByUserId(userId);
                OrderAdapter orderAdapter = new OrderAdapter(orderList);
                getActivity().runOnUiThread(() -> {
                    binding.recycleViewOrder.setAdapter(orderAdapter);
                    binding.recycleViewOrder.setLayoutManager(new LinearLayoutManager(requireContext()));
                });
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

    }
}