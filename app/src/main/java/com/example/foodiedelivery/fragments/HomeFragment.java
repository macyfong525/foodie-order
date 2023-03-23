package com.example.foodiedelivery.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.foodiedelivery.adapters.ResAdapter;
import com.example.foodiedelivery.db.RestaurantDatabaseHelper;
import com.example.foodiedelivery.models.Image;
import com.example.foodiedelivery.adapters.ImageAdapter;
import com.example.foodiedelivery.R;
import com.example.foodiedelivery.models.Restaurant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {
   List<Integer> Pics = new ArrayList<>(Arrays.asList(R.drawable.canadian,R.drawable.canadian2,R.drawable.indian,R.drawable.indian2,R.drawable.vietnamese,R.drawable.vietnamese2,R.drawable.hongkong,R.drawable.hongkong2,R.drawable.european,R.drawable.european2));
   List<Image> ImageList;
   ViewPager2 viewPager2;
   RecyclerView recyclerViewRes;
   Handler sliderHandler = new Handler();
   /*listview*/
//   List<Restaurant> RestaurantList = new ArrayList<>();
//   List<String> ResNames = new ArrayList<>(Arrays.asList("Ken's Kitchen, Vancouver,BC","Big Burgers, New Westminster,BC","Pizza Hut, Surrey, BC"));
//   List<Integer> ResIcons = new ArrayList<>(Arrays.asList(R.drawable.res1,R.drawable.res2,R.drawable.res3));
   // ResDB and dummy data starts here
   RestaurantDatabaseHelper ResDbHelper;
//   Restaurant restaurant1 = new Restaurant(1, "Restaurant 1", "Location 1", "Burger_2023_03-20_18_33_48");
//   long id1 = ResDbHelper.insertRestaurant(restaurant1);
//   Restaurant restaurant2 = new Restaurant(2, "Restaurant 2", "Location 2", "test2_2023_03-20_19_22_03");
//   long id2 = ResDbHelper.insertRestaurant(restaurant2);
   List<Restaurant> restaurants = new ArrayList<>(); // for dummy data purpose only!!!!
   List<Restaurant> testList = new ArrayList<>(); // for receiving data from DBHelper


   private Context context;


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
      context = getContext();
      ResDbHelper = new RestaurantDatabaseHelper(context);

      return v;
   }
   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      ImageList = new ArrayList<>();
      viewPager2 =view.findViewById(R.id.viewPager2id);

      FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

      ImageAdapter myAdapter = new ImageAdapter(ImageList,viewPager2);
      viewPager2.setAdapter(myAdapter);
      /*for listView*/

//      listViewRes= view.findViewById(R.id.listViewMain);
//      RestaurantAdapter_outdated restauranAdapter = new RestaurantAdapter_outdated(RestaurantList);
//      listViewRes.setAdapter(restauranAdapter);

      restaurants.add(new Restaurant(1, "Res1", "Location 1", "https://firebasestorage.googleapis.com/v0/b/csis3175-food.appspot.com/o/images%2F2023_03-05_16_07_26?alt=media&token=50b2272a-f6f7-40ca-9beb-c4733c9ff0b7"));
      restaurants.add(new Restaurant(2, "Res2", "Location 2", "https://firebasestorage.googleapis.com/v0/b/csis3175-food.appspot.com/o/images%2F2023_03-05_16_07_26?alt=media&token=50b2272a-f6f7-40ca-9beb-c4733c9ff0b7"));
      restaurants.add(new Restaurant(3, "Res3", "Location 3", "https://firebasestorage.googleapis.com/v0/b/csis3175-food.appspot.com/o/images%2F2023_03-05_16_07_26?alt=media&token=50b2272a-f6f7-40ca-9beb-c4733c9ff0b7"));
      for (Restaurant res : restaurants){
         try {
            ResDbHelper.insertRestaurant(res);
         } catch (Exception e) {
            Log.d("Restaurant", "Failed" + e);
         }
      }
      try {
         testList = ResDbHelper.getAllRestaurants();
      } catch (Exception e) {
         Log.d("Restaurant", "Failed");
      }
      for(Restaurant res : testList){
         Log.d("Restaurant", "successfully got the data from DBHelper, the Restaurant name: " + res.getResName());
      }

      recyclerViewRes = view.findViewById(R.id.recyclerViewMain);
      recyclerViewRes.setLayoutManager(new LinearLayoutManager(context));
      ResAdapter resAdapter = new ResAdapter(testList, context);
      recyclerViewRes.setAdapter(resAdapter);

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
            //set duration
            sliderHandler.postDelayed(sliderRunnable,2000);
         }
      });

//      addData();
   };

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
   };
//   private void addData(){
//      for(int i=0;i<ResNames.size();i++){
//         Restaurant thisRes = new Restaurant(ResNames.get(i),ResIcons.get(i));
//         RestaurantList.add(thisRes);
//      }
//   }
}
