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
import com.example.foodiedelivery.adapters.OrderAdapter;


public class OrderFragment extends Fragment {

   ListView listViewOrder;

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