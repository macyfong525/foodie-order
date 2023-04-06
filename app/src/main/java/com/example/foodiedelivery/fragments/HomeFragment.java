package com.example.foodiedelivery.fragments;

import android.content.Context;
import android.os.Bundle;

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

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.foodiedelivery.adapters.ResAdapter;
import com.example.foodiedelivery.db.FoodieDatabase;
import com.example.foodiedelivery.db.RestaurantDatabaseHelper;
import com.example.foodiedelivery.interfaces.DishDao;
import com.example.foodiedelivery.interfaces.RestaurantDao;
import com.example.foodiedelivery.models.Dish;
import com.example.foodiedelivery.models.Image;
import com.example.foodiedelivery.adapters.ImageAdapter;
import com.example.foodiedelivery.R;
import com.example.foodiedelivery.models.Restaurant;
import com.example.foodiedelivery.repositories.RestaurantRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeFragment extends Fragment {
   List<Integer> Pics = new ArrayList<>(Arrays.asList(R.drawable.canadian,R.drawable.canadian2,R.drawable.indian,R.drawable.indian2,R.drawable.vietnamese,R.drawable.vietnamese2,R.drawable.hongkong,R.drawable.hongkong2,R.drawable.european,R.drawable.european2));
   List<Image> ImageList;
   ViewPager2 viewPager2;
   RecyclerView recyclerViewRes;
   Handler sliderHandler = new Handler();
   RestaurantDatabaseHelper ResDbHelper;
   FoodieDatabase fdb;
   RestaurantDao restaurantDao;
   DishDao dishDao;
   private Context context;
   private RestaurantRepository restaurantRepository;




//   List<Restaurant> restaurants = ResDbHelper.getAllRestaurants();




   ListView listViewRes;

   Fragment Restaurant1Result = new MenuFragment();
//   Fragment Restaurant2Result = new Menu2Fragment();
//   Fragment Restaurant3Result = new Menu3Fragment();

   @Override
   public void onAttach(@NonNull Context context){
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
            restaurantRepository.deleteAllRestaurants();
            LiveData<List<Restaurant>>restaurants =  restaurantRepository.getAllRestaurants();
            if(restaurants == null || restaurants.getValue()==null){
               List<Restaurant> dummyRestaurants = new ArrayList<>();
               List<Dish> dummyDishes = new ArrayList<>();

               dummyRestaurants.add(new Restaurant("Fake Restaurant A", "Fake Location A", "https://firebasestorage.googleapis.com/v0/b/csis3175-food.appspot.com/o/images%2FBarbieri_-_ViaSophia25668.jpg?alt=media&token=66016b1e-4b14-4f7c-b445-07be7a31adc0"));
               dummyRestaurants.add(new Restaurant("Fake Restaurant B", "Fake Location B", "https://firebasestorage.googleapis.com/v0/b/csis3175-food.appspot.com/o/images%2Fphoto-1517248135467-4c7edcad34c4.jpg?alt=media&token=f9e9abf4-9b77-4d48-b29c-addd18616d6e"));
               dummyRestaurants.add(new Restaurant("Fake Restaurant C", "Fake Location C", "https://firebasestorage.googleapis.com/v0/b/csis3175-food.appspot.com/o/images%2Finterior-of-the-cliff.jpg?alt=media&token=73671d59-4036-4f29-ad3c-92cb45b7b3d9"));
               Long[] results = restaurantRepository.insertRestaurantsFromList(dummyRestaurants);
               List<Long> restaurantIds = Arrays.asList(results);

               // Add the dishes to the list and set their restaurant IDs to the newly inserted restaurants
               dummyDishes.add(new Dish(restaurantIds.get(0), "Extra Large Meat Lovers", 15.99));
               dummyDishes.add(new Dish(restaurantIds.get(0), "Extra Large Supreme", 15.99));
               dummyDishes.add(new Dish(restaurantIds.get(0), "Extra Large Pepperoni", 13.99));
               dummyDishes.add(new Dish(restaurantIds.get(0), "Extra Large BBQ Chicken &amp; Bacon.", 14.99));
               dummyDishes.add(new Dish(restaurantIds.get(0), "Extra Large 5 Cheese.", 15.99));
               dummyDishes.add(new Dish(restaurantIds.get(0), "Extra Large Pepperoni Slice,Slice.", 15.99));
               for (Dish dish : dummyDishes){
                  dishDao.insertDish(dish);
               }
            }
         }
      });




      restaurantRepository.getAllRestaurants().observe(getViewLifecycleOwner(), new Observer<List<Restaurant>>(){

         @Override
         public void onChanged(List<Restaurant> restaurants) {
            recyclerViewRes.setLayoutManager(new LinearLayoutManager(context));
            ResAdapter resAdapter = new ResAdapter(restaurants, context);
            recyclerViewRes.setAdapter(resAdapter);
         }

      });




      ImageList = new ArrayList<>();
      viewPager2 =view.findViewById(R.id.viewPager2id);
      FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
      ImageAdapter myAdapter = new ImageAdapter(ImageList,viewPager2);
      viewPager2.setAdapter(myAdapter);



//      for (Restaurant res : restaurants){
//         try {
//            ResDbHelper.insertRestaurant(res);
//         } catch (Exception e) {
//            Log.d("Restaurant", "Failed" + e);
//         }
//      }
//      try {
//         testList = ResDbHelper.getAllRestaurants();
//      } catch (Exception e) {
//         Log.d("Restaurant", "Failed");
//      }
//      for(Restaurant res : testList){
//         Log.d("Restaurant", "successfully got the data from DBHelper, the Restaurant name: " + res.getResName());
//      }


//      listViewRes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//         @Override
//         public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//            switch(i){
//               case 0:
//                  fragmentManager.beginTransaction().replace(R.id.container, Restaurant1Result).commit();break;
////                  startActivity(new Intent(getActivity(), Restaurant1Result.class));break;
//               case 1:
//                  fragmentManager.beginTransaction().replace(R.id.container, Restaurant2Result).commit();break;
////                 startActivity(new Intent(getActivity(),Menu2Fragment.class));break;
//              case 2:
//                   fragmentManager.beginTransaction().replace(R.id.container, Restaurant3Result).commit();break;
//            }
//         }
//      });

      loadModelData();
      /* 1 set scrolling*/
      viewPager2.setOffscreenPageLimit(3);
      viewPager2.setClipChildren(false);
      viewPager2.setClipToPadding(false);
      viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

      viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){
         @Override
         public void onPageSelected(int position) {
            super.onPageSelected(position);
            sliderHandler.removeCallbacks(sliderRunnable);
            sliderHandler.postDelayed(sliderRunnable,2000);
         }
      });
   }

   private Runnable sliderRunnable = new Runnable() {
      @Override
      public void run() {
         viewPager2.setCurrentItem(viewPager2.getCurrentItem() +1);
      }
   };

   private void loadModelData() {
      for(int i=0;i<Pics.size();i++){
         Image newImage = new Image(Pics.get(i));
         ImageList.add(newImage);
      }
   }
}
