package com.example.foodiedelivery;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.foodiedelivery.databinding.ActivityMainBinding;
import com.example.foodiedelivery.databinding.FragmentRestaurant1ResultBinding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 *
 * create an instance of this fragment.
 */
public class Restaurant1Result extends Fragment {
    List<Dish> Dishes = new ArrayList<>();
    List<String> DishNames =new ArrayList<>(Arrays.asList("Extra Large Meat Lovers","Extra Large Supreme","Extra Large Pepperoni.","Extra Large BBQ Chicken &amp; Bacon.","Extra Large 5 Cheese.","Extra Large Pepperoni Slice,Slice."));
    List<String> DishPrices = new ArrayList<>(Arrays.asList("15.99","15.99","14.99","15.99","14.99","3.99"));
    List<Integer> DishImages = new ArrayList<>(Arrays.asList(R.drawable.meatlovers,R.drawable.exlarsupreme,R.drawable.extlarbbqchixbacon,R.drawable.extlarfivecheese,R.drawable.extlarpepperoni,R.drawable.pizza6));
    //private FragmentHousesBinding binding;
    LayoutInflater inflaterThis;
    FragmentRestaurant1ResultBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        DishAdapter dishAdapter = new DishAdapter(Restaurant1Result.this,Dishes);
//        setContentView(binding.getRoot());
        addData();
        binding =FragmentRestaurant1ResultBinding.inflate(getLayoutInflater());
        DishAdapter gridAdapter = new DishAdapter(getContext(),Dishes);
        GridView gridView1Images = view.findViewById(R.id.gridView1Images);
        binding.gridView1Images.setAdapter(gridAdapter);
        binding.getRoot();

        ImageView ingViewLarge = view.findViewById(R.id.imgViewLarge);
        gridView1Images.setNumColumns(3);
        gridView1Images.setVerticalSpacing(8);
        gridView1Images.setHorizontalSpacing(8);
        binding.gridView1Images.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getContext(),"You've clicked: "+Dishes.get(i).getName(),Toast.LENGTH_LONG).show();
            }
        });
    }
    private void addData() {
        Dishes.add(new Dish("Extra Large Meat Lovers","15.99",R.drawable.meatlovers));
        Dishes.add(new Dish("Extra Large Supreme","15.99",R.drawable.exlarsupreme));
        Dishes.add(new Dish("Extra Large Pepperoni","13.99",R.drawable.extlarbbqchixbacon));
        Dishes.add(new Dish("Extra Large BBQ Chicken &amp; Bacon.","14.99",R.drawable.extlarpepperoni));
        Dishes.add(new Dish("Extra Large 5 Cheese.","15.99",R.drawable.extlarfivecheese));
        Dishes.add(new Dish("Extra Large Pepperoni Slice,Slice.","15.99",R.drawable.pizza6));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

//        binding =FragmentRestaurant1ResultBinding.inflate(getLayoutInflater());
//    setContentView(binding.getRoot());
       // FragmentRestaurant1ResultBinding binding = FragmentRestaurant1ResultBinding.inflate(inflater, container, false);
        //set variables in Binding

        //setContentView(binding.getRoot());
//        DishAdapter gridAdapter = new DishAdapter(getContext(),Dishes);
//        GridView gridView1Images = findViewById(R.id.gridView1Images);

       return inflater.inflate(R.layout.fragment_restaurant1_result, container, false);
    }
}