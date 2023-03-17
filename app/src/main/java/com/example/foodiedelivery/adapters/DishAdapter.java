package com.example.foodiedelivery.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foodiedelivery.R;
import com.example.foodiedelivery.models.Dish;

import java.util.List;

public class DishAdapter extends BaseAdapter {
    List<Dish> DishList;
    public DishAdapter(Context context,List<Dish> dishList ) {
        DishList = dishList;
    }
    public List<Dish> getDishList() {
        return DishList;
    }

    public void setDishList(List<Dish> dishList) {
        DishList = dishList;
    }

    @Override
    public int getCount() {
        return DishList.size();
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
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view ==null){
            view=LayoutInflater.from(viewGroup).inflate(R.layout.layout_restaurant1_custom_layout_grid,viewGroup,false);
        }
        ImageView imgViewLayout= view.findViewById(R.id.imgGridLayout);
        TextView txtName=view.findViewById(R.id.txtNameLayout);
        TextView txtPrice=view.findViewById(R.id.txtPriceLayout);

        txtName.setText(DishList.get(i).getName());
        txtPrice.setText(DishList.get(i).getPrice());
        txtName.setGravity(Gravity.CENTER_VERTICAL);
        txtPrice.setGravity(Gravity.CENTER_VERTICAL);
        return view;
    }
}
