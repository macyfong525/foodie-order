package com.example.foodiedelivery.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodiedelivery.databinding.LayoutMenuBinding;
import com.example.foodiedelivery.models.Dish;

public class MenuAdapter extends ListAdapter<Dish, MenuAdapter.DishViewHolder> {


    DishInterface dishInterface;
    public MenuAdapter(DishInterface dishInterface) {
        super(Dish.itemCallback);
        this.dishInterface = dishInterface;
    }


    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LayoutMenuBinding layoutMenuBinding = LayoutMenuBinding.inflate(layoutInflater, parent, false);
        layoutMenuBinding.setDishInterface(dishInterface);
        return new DishViewHolder(layoutMenuBinding);
    }
    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, int position) {
        Dish dish = getItem(position);
        holder.layoutMenuBinding.setDish(dish);
        holder.layoutMenuBinding.executePendingBindings();
    }

    class DishViewHolder extends RecyclerView.ViewHolder{
        LayoutMenuBinding layoutMenuBinding;

        public DishViewHolder(LayoutMenuBinding binding) {
            super(binding.getRoot());
            this.layoutMenuBinding = binding;

        }
    }


    public interface DishInterface{
        void addItem(Dish dish);
    }

}
