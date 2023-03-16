package com.example.foodiedelivery;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RestaurantAdapter extends BaseAdapter {
    List<Restaurant> RestaurantList = new ArrayList<>();

    public RestaurantAdapter(List<Restaurant> restaurantList) {
        RestaurantList = restaurantList;
    }

    public List<Restaurant> getRestaurantList() {
        return RestaurantList;
    }

    public void setRestaurantList(List<Restaurant> restaurantList) {
        RestaurantList = restaurantList;
    }

    @Override
    public int getCount() {
        return RestaurantList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {

        if(view == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_restaurant_helper,parent,false);
        }
        TextView txtViewName = view.findViewById(R.id.txtViewNameLayout);
        txtViewName.setText(RestaurantList.get(i).getResName());
        ImageView imgViewIcon = view.findViewById(R.id.imgViewLayout);
        imgViewIcon.setImageResource(RestaurantList.get(i).getResIcon());
        txtViewName.setGravity(Gravity.CENTER_VERTICAL);
        return view;
    }
}
