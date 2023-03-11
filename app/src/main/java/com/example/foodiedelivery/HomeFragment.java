package com.example.foodiedelivery;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {
   List<Integer> Pics = new ArrayList<>(Arrays.asList(R.drawable.canadian,R.drawable.canadian2,R.drawable.indian,R.drawable.indian2,R.drawable.vietnamese,R.drawable.vietnamese2,R.drawable.hongkong,R.drawable.hongkong2,R.drawable.european,R.drawable.european2));
   List<Image> ImageList;
   ViewPager2 viewPager2;
   Handler sliderHandler = new Handler();
   /*listview*/
   List<Restaurant> RestaurantList = new ArrayList<>();
   List<String> ResNames = new ArrayList<>(Arrays.asList("Ken's Kitchen, Vancouver,BC","Big Burgers, New Westminster,BC","Pizza Hut, Surrey, BC"));
   List<Integer> ResIcons = new ArrayList<>(Arrays.asList(R.drawable.res1,R.drawable.res2,R.drawable.res3));
   ListView listViewRes;

   Fragment Restaurant1Result = new Restaurant1Result();
   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      // Inflate the layout for this fragment
      return inflater.inflate(R.layout.fragment_home, container, false);
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

      listViewRes= view.findViewById(R.id.listViewMain);
      RestaurantAdapter restauranAdapter = new RestaurantAdapter(RestaurantList);
      listViewRes.setAdapter(restauranAdapter);

      listViewRes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
         @Override
         public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            switch(i){
               case 0:
                  fragmentManager.beginTransaction().replace(R.id.container, Restaurant1Result).commit();
//                  startActivity(new Intent(getActivity(), Restaurant1Result.class));break;
//               case 1:
//                  startActivity(new Intent(getActivity(),Restaurant2Result.class);break;
//               case 2:
//                  startActivity(new Intent(getActivity(),Restaurant3Result.class);break;
            }
         }
      });

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

      addData();


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
   private void addData(){
      for(int i=0;i<ResNames.size();i++){
         Restaurant thisRes = new Restaurant(ResNames.get(i),ResIcons.get(i));
         RestaurantList.add(thisRes);
      }
   }
}
