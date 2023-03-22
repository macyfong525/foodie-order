package com.example.foodiedelivery.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodiedelivery.R;
import com.example.foodiedelivery.databinding.LayoutMenuBinding;
import com.example.foodiedelivery.models.Dish;

import java.util.List;

public class DishListAdapter extends ListAdapter<Dish, DishListAdapter.DishViewHolder> {



    public DishListAdapter() {
        super(Dish.itemCallback);
    }


    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LayoutMenuBinding layoutMenuBinding = LayoutMenuBinding.inflate(layoutInflater, parent, false);

        return new DishViewHolder(layoutMenuBinding);
    }

    class DishViewHolder extends RecyclerView.ViewHolder{
        LayoutMenuBinding layoutMenuBinding;

        public DishViewHolder(LayoutMenuBinding binding) {
            super(binding.getRoot());
            this.layoutMenuBinding = binding;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, int position) {
        Dish dish = getItem(position);
        holder.layoutMenuBinding.setDish(dish);

    }

    public interface DishInterface{
        void addItem(Dish dish);
    }

}
