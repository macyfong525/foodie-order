package com.example.foodiedelivery.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.room.Room;

import com.example.foodiedelivery.adapters.MenuAdapter;
import com.example.foodiedelivery.databinding.FragmentMenuBinding;
import com.example.foodiedelivery.db.FoodieDatabase;
import com.example.foodiedelivery.interfaces.DishDao;
import com.example.foodiedelivery.interfaces.RestaurantDao;
import com.example.foodiedelivery.models.CartViewModel;
import com.example.foodiedelivery.models.Dish;
import com.example.foodiedelivery.repositories.RestaurantRepository;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A simple {@link Fragment} subclass.
 * <p>
 * create an instance of this fragment.
 */
public class MenuFragment extends Fragment implements MenuAdapter.DishInterface {


    private static final String TAG = "MenuFragment";
    List<Dish> Dishes = new ArrayList<>();
    FragmentMenuBinding fragmentMenuBinding;
    private MenuAdapter menuAdapter;
    private CartViewModel cartViewModel;
    FoodieDatabase fdb;
    RestaurantDao restaurantDao;
    DishDao dishDao;
    List<Dish> dishes;

    public MenuFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentMenuBinding = FragmentMenuBinding.inflate(inflater, container, false);
        return fragmentMenuBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        menuAdapter = new MenuAdapter(this);
        fragmentMenuBinding.recycleViewMenu.setAdapter(menuAdapter);
        fragmentMenuBinding.recycleViewMenu.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        fragmentMenuBinding.recycleViewMenu.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.HORIZONTAL));

        // TODO change to db
//        addData();
        Bundle args= getArguments();
        int restaurantId = args.getInt("restaurantId");

        Log.d(TAG , "onViewCreated: "+ restaurantId);

        fdb = Room.databaseBuilder(getActivity(), FoodieDatabase.class, "foodie.db").build();
        restaurantDao = fdb.RestaurantDao();

        dishDao = fdb.menuDao();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {

            dishes = dishDao.getDishesByRestaurantId(restaurantId);
            Log.d(TAG, "dishes: " + dishes.size());
            for(Dish dish : dishes) {
                Log.d(TAG, "loop dish: "+ dish.toString());
            }
            getActivity().runOnUiThread(() -> {
                menuAdapter.submitList(dishes);
            });
        });
        cartViewModel = new ViewModelProvider(requireActivity()).get(CartViewModel.class);



    }

    @Override
    public void addItem(Dish dish) {
        int isAddedStatus = cartViewModel.addItemToCart(dish);
        switch (isAddedStatus) {
            case 0:
                Snackbar.make(requireView(), dish.getName() + " added to cart", Snackbar.LENGTH_LONG)
                        .show();
                break;
            case 1:
                Snackbar.make(requireView(), dish.getName() + " exceeds the max quantity in cart",
                        Snackbar.LENGTH_LONG).show();
                break;
            case 2:
                Snackbar.make(requireView(), dish.getName() + " is not added.\nYou can only choose one restaurant",
                        Snackbar.LENGTH_LONG).show();
                break;
        }

    }
}