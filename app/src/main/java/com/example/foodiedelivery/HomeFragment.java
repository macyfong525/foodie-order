package com.example.foodiedelivery;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.Handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.ConsoleHandler;


public class HomeFragment extends Fragment {

   List<Integer> Pics = new ArrayList<>(Arrays.asList(R.drawable.canadian,R.drawable.canadian2,R.drawable.indian,R.drawable.indian2,R.drawable.vietnamese,R.drawable.vietnamese2,R.drawable.hongkong,R.drawable.hongkong2,R.drawable.european,R.drawable.european2));
   List<Image> ImageList;
   ViewPager2 viewPager2;
   Handler sliderHandler = new Handler();

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {

      return inflater.inflate(R.layout.fragment_home, container, false);
      //setContentView(R.layout.fragment_home);
   }

   @Override
   public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      super.onViewCreated(view, savedInstanceState);
      ImageList = new ArrayList<>();
      viewPager2 =view.findViewById(R.id.viewPager2id);

      ImageAdapter myAdapter = new ImageAdapter(ImageList,viewPager2);
      viewPager2.setAdapter(myAdapter);

      loadModelData();

      /* 1 set scrolling*/
      viewPager2.setOffscreenPageLimit(3);
      viewPager2.setClipChildren(false);
      viewPager2.setClipToPadding(false);
      viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

      /*part 4*/

      viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback(){
         @Override
         public void onPageSelected(int position) {
            super.onPageSelected(position);

            sliderHandler.removeCallbacks(sliderRunnable);
            //set duration
            sliderHandler.postDelayed(sliderRunnable,2000);
         }
      });
   };
   /*part 4*/
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

}