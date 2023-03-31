package com.example.foodiedelivery.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;
import com.example.foodiedelivery.interfaces.RestaurantDao;
import com.example.foodiedelivery.models.Dish;
import com.example.foodiedelivery.models.Restaurant;

import java.util.ArrayList;
import java.util.List;

public class RestaurantRepository {
    private RestaurantDao restaurantDao;
    public RestaurantRepository(RestaurantDao dao){
        restaurantDao = dao;
    }

    public void insertRestaurants(Restaurant...restaurants){
        restaurantDao.insertRestaurants(restaurants);
    }

    public Long[] insertRestaurantsFromList(List<Restaurant> restaurants){
        return restaurantDao.insertRestaurantsFromList(restaurants);
    }

    public void insertOneRestaurant(Restaurant restaurant){
        restaurantDao.insertOneRestaurant(restaurant);
    }

    public List<Restaurant> getAllRestaurants(){
        List<Restaurant> allRestaurant = restaurantDao.GetAllRestaurants();
        if(allRestaurant.size()==0){
            //creating dummy data when there's nothing inside
            List<Restaurant> dummyRestaurants = new ArrayList<>();
            dummyRestaurants.add(new Restaurant("Fake Restaurant A", "Fake Location A", "https://firebasestorage.googleapis.com/v0/b/csis3175-food.appspot.com/o/images%2FBarbieri_-_ViaSophia25668.jpg?alt=media&token=66016b1e-4b14-4f7c-b445-07be7a31adc0"));
            dummyRestaurants.add(new Restaurant("Fake Restaurant B", "Fake Location B", "https://firebasestorage.googleapis.com/v0/b/csis3175-food.appspot.com/o/images%2Fphoto-1517248135467-4c7edcad34c4.jpg?alt=media&token=f9e9abf4-9b77-4d48-b29c-addd18616d6e"));
            dummyRestaurants.add(new Restaurant("Fake Restaurant C", "Fake Location C", "https://firebasestorage.googleapis.com/v0/b/csis3175-food.appspot.com/o/images%2Finterior-of-the-cliff.jpg?alt=media&token=73671d59-4036-4f29-ad3c-92cb45b7b3d9"));
            restaurantDao.insertRestaurantsFromList(dummyRestaurants);
            List<Dish> dummyDishes = new ArrayList<>();
            dummyDishes.add(new Dish(1, "Extra Large Meat Lovers", 15.99));
            dummyDishes.add(new Dish(1, "Extra Large Supreme", 15.99));
            dummyDishes.add(new Dish(1, "Extra Large Pepperoni", 13.99));
            dummyDishes.add(new Dish(1, "Extra Large BBQ Chicken &amp; Bacon.", 14.99));
            dummyDishes.add(new Dish(1, "Extra Large 5 Cheese.", 15.99));
            dummyDishes.add(new Dish(2, "Extra Large Pepperoni Slice,Slice.", 15.99));

            return restaurantDao.GetAllRestaurants();
        }
        return restaurantDao.GetAllRestaurants();
    }

    public int deleteOneRestaurant(Restaurant restaurant){
        return restaurantDao.deleteOneRestaurant(restaurant);
    }

    public void deleteAllRestaurants(){
        restaurantDao.deleteAllRestaurants();
    }

    public int deleteRestaurantWithId(int id){
        return restaurantDao.deleteRestaurantWithId(id);
    }

}
