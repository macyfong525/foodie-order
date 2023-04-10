package com.example.foodiedelivery.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodiedelivery.databinding.LayoutMenuBinding;
import com.example.foodiedelivery.models.Dish;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.DishViewHolder> {

    List<Dish> dishList;
    DishInterface dishInterface;

    public MenuAdapter(List<Dish> dishList) {
        this.dishList = dishList;
    }

    public MenuAdapter(List<Dish> dishList, DishInterface dishInterface) {
        this.dishList = dishList;
        this.dishInterface = dishInterface;
    }


    @NonNull
    @Override
    public DishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutMenuBinding binding = LayoutMenuBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        DishViewHolder holder = new DishViewHolder(binding);
        binding.setDishInterface(dishInterface);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull DishViewHolder holder, int position) {
        Dish dish = dishList.get(position);
        holder.binding.setDish(dish);
        holder.binding.executePendingBindings();
    }


    @Override
    public int getItemCount() {
        return dishList.size();
    }


    public interface DishInterface {
        void addItem(Dish dish);
    }

    class DishViewHolder extends RecyclerView.ViewHolder {
        LayoutMenuBinding binding;

        public DishViewHolder(LayoutMenuBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;

        }
    }

}
