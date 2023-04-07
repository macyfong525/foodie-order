package com.example.foodiedelivery.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodiedelivery.databinding.LayoutOrderItemBinding;
import com.example.foodiedelivery.models.Order;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    List<Order> orderList;

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutOrderItemBinding binding = LayoutOrderItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        OrderViewHolder holder = new OrderViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.OrderViewHolder holder, int position) {
        if (position == 0) {
            holder.binding.orderid.setText("Order id");
            holder.binding.orderdate.setText("Order datetime");
            holder.binding.ordertotal.setText("Order amount");

        } else {
            holder.binding.orderid.setText(
                    String.valueOf(orderList.get(position-1).getId())
            );

            // create an instant object from the current time
            Instant instant = Instant.ofEpochMilli(orderList.get(position-1).getOrderDate());
            // create a date/time object for Vancouver time zone
            ZonedDateTime vancouverTime = instant.atZone(ZoneId.of("America/Vancouver"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
            String formattedTime = vancouverTime.format(formatter);
            holder.binding.orderdate.setText(formattedTime);
            holder.binding.ordertotal.setText(String.format("$%.2f",orderList.get(position-1).getOrderTotal()));
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size() + 1;
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        LayoutOrderItemBinding binding;

        public OrderViewHolder(LayoutOrderItemBinding orderBinding) {
            super(orderBinding.getRoot());
            binding = orderBinding;
        }
    }
}
