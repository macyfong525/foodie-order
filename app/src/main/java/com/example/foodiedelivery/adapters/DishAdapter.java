package com.example.foodiedelivery;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class DishAdapter extends BaseAdapter {
    List<Dish> DishList;
    Context context;
    LayoutInflater inflater;
    public DishAdapter(Context context,List<Dish> dishList ) {
        DishList = dishList;
        this.context = context;
        inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public List<Dish> getDishList() {
        return DishList;
    }

    public void setDishList(List<Dish> dishList) {
        DishList = dishList;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
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

        if(inflater ==null){
            inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(view ==null){
            view=inflater.inflate(R.layout.layout_restaurant1_custom_layout_grid,null);
        }
        ImageView imgViewLayout= view.findViewById(R.id.imgGridLayout);
        TextView txtName=view.findViewById(R.id.txtNameLayout);
        TextView txtPrice=view.findViewById(R.id.txtPriceLayout);
        imgViewLayout.setImageResource(DishList.get(i).getPic());
        txtName.setText(DishList.get(i).getName());
        txtPrice.setText(DishList.get(i).getPrice());
        txtName.setGravity(Gravity.CENTER_VERTICAL);
        txtPrice.setGravity(Gravity.CENTER_VERTICAL);
        return view;
    }
}
