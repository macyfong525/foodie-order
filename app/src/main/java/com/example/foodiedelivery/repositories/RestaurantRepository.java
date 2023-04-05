package com.example.foodiedelivery.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.foodiedelivery.interfaces.DishDao;
import com.example.foodiedelivery.interfaces.RestaurantDao;
import com.example.foodiedelivery.models.Dish;
import com.example.foodiedelivery.models.Restaurant;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RestaurantRepository {
    private RestaurantDao restaurantDao;
    private ExecutorService executorService;
    private DishDao dishDao;
    private LiveData<List<Restaurant>> allRestaurants;
    private boolean isDummyDataExist = false;

    public RestaurantRepository(RestaurantDao restaurantDao, DishDao dishDao) {
        this.restaurantDao = restaurantDao;
        this.dishDao = dishDao;
        allRestaurants = restaurantDao.GetAllRestaurants();

    }

    public void insertRestaurants(Restaurant... restaurants) {
        executorService.execute(() ->
                restaurantDao.insertRestaurants(restaurants));

    }

    public Long[] insertRestaurantsFromList(List<Restaurant> restaurants) {
        return restaurantDao.insertRestaurantsFromList(restaurants);
    }

    public void insertOneRestaurant(Restaurant restaurant) {
        restaurantDao.insertOneRestaurant(restaurant);
    }

    public LiveData<List<Restaurant>> getAllRestaurants() {
        LiveData<List<Restaurant>> allRestaurants = restaurantDao.GetAllRestaurants();
        executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                List<Restaurant> restaurants = allRestaurants.getValue();
                if (restaurants == null || restaurants.size() == 0) {
                    List<Restaurant> dummyRestaurants = new ArrayList<>();
                    List<Dish> dummyDishes = new ArrayList<>();

                    dummyRestaurants.add(new Restaurant("Fake Restaurant A", "Fake Location A", "https://firebasestorage.googleapis.com/v0/b/csis3175-food.appspot.com/o/images%2FBarbieri_-_ViaSophia25668.jpg?alt=media&token=66016b1e-4b14-4f7c-b445-07be7a31adc0"));
                    dummyRestaurants.add(new Restaurant("Fake Restaurant B", "Fake Location B", "https://firebasestorage.googleapis.com/v0/b/csis3175-food.appspot.com/o/images%2Fphoto-1517248135467-4c7edcad34c4.jpg?alt=media&token=f9e9abf4-9b77-4d48-b29c-addd18616d6e"));
                    dummyRestaurants.add(new Restaurant("Fake Restaurant C", "Fake Location C", "https://firebasestorage.googleapis.com/v0/b/csis3175-food.appspot.com/o/images%2Finterior-of-the-cliff.jpg?alt=media&token=73671d59-4036-4f29-ad3c-92cb45b7b3d9"));
                    Long[] results = restaurantDao.insertRestaurantsFromList(dummyRestaurants);
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
//                    Long[] result = dishDao.insertDishesFromList(dummyDishes);

                }
            }
        });
        return allRestaurants;
    }

    public int deleteOneRestaurant(Restaurant restaurant) {
        return restaurantDao.deleteOneRestaurant(restaurant);
    }

    public void deleteAllRestaurants() {

        restaurantDao.deleteAllRestaurants();
    }

    public int deleteRestaurantWithId(int id) {
        return restaurantDao.deleteRestaurantWithId(id);
    }

}
