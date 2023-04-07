package com.example.foodiedelivery.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodiedelivery.databinding.LayoutCartBinding;
import com.example.foodiedelivery.models.CartItem;

public class CartAdapter extends ListAdapter<CartItem, CartAdapter.CartViewHolder> {

    private CartInterface cartInterface;
    public CartAdapter(CartInterface cartInterface) {
        super(CartItem.itemCallback);
        this.cartInterface = cartInterface;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        LayoutCartBinding layoutCartBinding = LayoutCartBinding.inflate(layoutInflater, parent, false);
        return new CartViewHolder(layoutCartBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        holder.layoutCartBinding.setCartItem(getItem(position));
        holder.layoutCartBinding.executePendingBindings();
    }

    class CartViewHolder extends RecyclerView.ViewHolder{
        LayoutCartBinding layoutCartBinding;
        public CartViewHolder(@NonNull LayoutCartBinding layoutCartBinding) {
            super(layoutCartBinding.getRoot());
            this.layoutCartBinding = layoutCartBinding;

            layoutCartBinding.deleteDishButton.setOnClickListener((View view) -> {
                  cartInterface.deleteItem(getItem(getAdapterPosition()));
            });

            layoutCartBinding.imageBtnPlus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int quantity = getItem(getAdapterPosition()).getQuantity() +1;
                    cartInterface.changeQuantity(getItem(getAdapterPosition()), quantity);
                }
            });

            layoutCartBinding.imageBtnMins.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int quantity = getItem(getAdapterPosition()).getQuantity() - 1;
                    if(quantity<1){
                        return;
                    }
                    cartInterface.changeQuantity(getItem(getAdapterPosition()), quantity);
                }
            });
//            layoutCartBinding.quantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                    int quantity = i+1;
//                    if (quantity == getItem(getAdapterPosition()).getQuantity()) {
//                        return;
//                    }
//                    cartInterface.changeQuantity(getItem(getAdapterPosition()), quantity);
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> adapterView) {
//
//                }
//            });
        }
    }

    public interface CartInterface {
        void deleteItem(CartItem cartItem);
        void changeQuantity(CartItem cartItem, int quantity);
    }
}
