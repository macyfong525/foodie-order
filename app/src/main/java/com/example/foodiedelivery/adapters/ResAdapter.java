package com.example.foodiedelivery.adapters;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodiedelivery.R;
import com.example.foodiedelivery.fragments.MenuFragment;
import com.example.foodiedelivery.fragments.OrderFragment;
import com.example.foodiedelivery.models.Restaurant;


import java.util.List;

public class ResAdapter extends RecyclerView.Adapter<ResAdapter.RestaurantViewHolder>{
    private List<Restaurant> restaurantList;
    private Context context;


    public ResAdapter(List<Restaurant> restaurants, Context  conText){
        this.restaurantList = restaurants;
        this.context = conText;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_restaurant_item, parent, false);
        return new RestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        holder.nameTextView.setText(restaurant.getResName());
        holder.locationTextView.setText(restaurant.getLocation());

        Glide.with(context).load(restaurant.getImageUrl())
                .into(holder.imgView);
        holder.itemView.setOnClickListener(v -> {
            Log.d("RecyclerView", "You clicked the restaurant position: " + position + "Restaurant ID: " + restaurant.getId());
            Bundle bundle = new Bundle();
            bundle.putInt("restaurantId", restaurant.getId());

            Fragment menuFrag = new MenuFragment();
            menuFrag.setArguments(bundle);

            FragmentTransaction trans = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
            trans.replace(R.id.container, menuFrag);
            trans.addToBackStack(null);
            trans.commit();
        });
    }

    @Override
    public int getItemCount() {
        return restaurantList.size();
    }

    public class RestaurantViewHolder extends RecyclerView.ViewHolder{
        ImageView imgView;
        TextView nameTextView;
        TextView locationTextView;
        public RestaurantViewHolder(@NonNull View itemView){
            super(itemView);
            imgView = itemView.findViewById(R.id.imageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
        }
    }
    public interface OnItemClickListener{
        public void onItemClick(int i);
    }

}


