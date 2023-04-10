package com.example.foodiedelivery.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.viewpager2.widget.ViewPager2;

import com.example.foodiedelivery.R;
import com.example.foodiedelivery.adapters.ImageAdapter;
import com.example.foodiedelivery.adapters.ResAdapter;
import com.example.foodiedelivery.db.FoodieDatabase;
import com.example.foodiedelivery.interfaces.DishDao;
import com.example.foodiedelivery.interfaces.RestaurantDao;
import com.example.foodiedelivery.models.Image;
import com.example.foodiedelivery.models.Restaurant;
import com.example.foodiedelivery.repositories.RestaurantRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {
    List<Integer> Pics = new ArrayList<>(Arrays.asList(R.drawable.canadian, R.drawable.canadian2, R.drawable.indian, R.drawable.indian2, R.drawable.vietnamese, R.drawable.vietnamese2, R.drawable.hongkong, R.drawable.hongkong2, R.drawable.european, R.drawable.european2));
    List<Image> ImageList;
    ViewPager2 viewPager2;
    RecyclerView recyclerViewRes;
    Handler sliderHandler = new Handler();
    FoodieDatabase fdb;
    RestaurantDao restaurantDao;
    DishDao dishDao;
    private Context context;
    private RestaurantRepository restaurantRepository;
    private Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
        }
    };

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewRes = v.findViewById(R.id.recyclerViewMain);


        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //db starts here
        fdb = Room.databaseBuilder(requireContext(), FoodieDatabase.class, "foodie.db").build();
        restaurantDao = fdb.RestaurantDao();
        dishDao = fdb.menuDao();
        restaurantRepository = new RestaurantRepository(restaurantDao, dishDao);
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                LiveData<List<Restaurant>> restaurants = restaurantRepository.getAllRestaurants();
            }
        });


        restaurantRepository.getAllRestaurants().observe(getViewLifecycleOwner(), new Observer<List<Restaurant>>() {

            @Override
            public void onChanged(List<Restaurant> restaurants) {
                recyclerViewRes.setLayoutManager(new LinearLayoutManager(context));
                ResAdapter resAdapter = new ResAdapter(restaurants, context);
                recyclerViewRes.setAdapter(resAdapter);
            }

        });


        ImageList = new ArrayList<>();
        viewPager2 = view.findViewById(R.id.viewPager2id);
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        ImageAdapter myAdapter = new ImageAdapter(ImageList, viewPager2);
        viewPager2.setAdapter(myAdapter);


        loadModelData();
        /* 1 set scrolling*/
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.setClipChildren(false);
        viewPager2.setClipToPadding(false);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable, 2000);
            }
        });
    }

    private void loadModelData() {
        for (int i = 0; i < Pics.size(); i++) {
            Image newImage = new Image(Pics.get(i));
            ImageList.add(newImage);
        }
    }
}
