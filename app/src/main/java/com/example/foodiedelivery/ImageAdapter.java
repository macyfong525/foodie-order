package com.example.foodiedelivery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder>{
    //2 attributes: cursor and viewPager2
    List<Image> ImageList;
    ViewPager2 viewPager2;
    //constructor

    public ImageAdapter(List<Image> imageList, ViewPager2 viewPager2) {
        ImageList = imageList;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_image,parent,false);
        return new ImageViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.imgViewMain.setImageResource(ImageList.get(position).getImage());
        /*part 6-2: to continue the loop when reaching to the last image*/
        if(position == ImageList.size()-2){
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return ImageList.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        //one attribute
        RoundedImageView imgViewMain;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imgViewMain = itemView.findViewById(R.id.imgViewLayout);
        }
    };
    /*part 6*/
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            ImageList.addAll(ImageList);
            notifyDataSetChanged();
        }
    };
}
