package com.example.foodiedelivery.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.foodiedelivery.R;
import com.example.foodiedelivery.db.FoodieDatabase;
import com.example.foodiedelivery.interfaces.DishDao;
import com.example.foodiedelivery.interfaces.RestaurantDao;
import com.example.foodiedelivery.interfaces.UserDao;
import com.example.foodiedelivery.models.Dish;
import com.example.foodiedelivery.models.Restaurant;
import com.example.foodiedelivery.models.User;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LoadData extends AppCompatActivity {

    private static final String TAG = "LoadData";
    FoodieDatabase fd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            // open the sample data file as an InputStream
            List<Restaurant> res = ReadResCSV();
            List<Dish> dishes = ReadMenuCSV();
            List<User> users = ReadUserCSV();

            fd = Room.databaseBuilder
                    (getApplicationContext(), FoodieDatabase.class, "foodie.db").build();
            UserDao userDao = fd.userDao();
            DishDao menuDao = fd.menuDao();
            RestaurantDao resDao = fd.RestaurantDao();

            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.execute(() -> {
                Log.d(TAG, "onCreate: " + users.size());
                userDao.insertUsersFromList(users);
                resDao.insertRestaurantsFromList(res);
                menuDao.insertDishesFromList(dishes);
                Log.d(TAG, "Data loading is done");
            });

        } catch (Exception e) {
            // handle the error
            e.printStackTrace();
        }
    }

    private List<Restaurant> ReadResCSV() {
        List<Restaurant> ResList = new ArrayList<>();

        InputStream inputStreamRes = getResources().openRawResource(R.raw.restaurant);
        CSVReader reader = new CSVReader(new InputStreamReader(inputStreamRes));

        try {
            String[] dataRow;
            while ((dataRow = reader.readNext()) != null) {
                Restaurant res = new Restaurant(dataRow[0], dataRow[1], dataRow[2]);
                ResList.add(res);
            }
        } catch (IOException | CsvValidationException ex) {
            throw new RuntimeException("Error reading file " + ex);
        } finally {
            try {
                inputStreamRes.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return ResList;
    }


    private List<Dish> ReadMenuCSV() {
        List<Dish> DishList = new ArrayList<>();

        InputStream inputStreamMenu = getResources().openRawResource(R.raw.menu);
        CSVReader reader = new CSVReader(new InputStreamReader(inputStreamMenu));

        try {
            String[] dataRow;
            while ((dataRow = reader.readNext()) != null) {
                Log.d(TAG, "menu: " + Arrays.toString(dataRow));
                Dish eachDish = new Dish(Long.parseLong(dataRow[0]), dataRow[2], Double.parseDouble(dataRow[4]));
                DishList.add(eachDish);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error reading file " + ex);
        } catch (CsvValidationException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStreamMenu.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return DishList;

    }

    private List<User> ReadUserCSV() {
        List<User> UserList = new ArrayList<>();

        InputStream inputStream
                = getResources().openRawResource(R.raw.user);
        BufferedReader reader
                = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String eachLine;
            while ((eachLine = reader.readLine()) != null) {
                String[] eachStudFields = eachLine.split(",");
                boolean isAdmin = Integer.parseInt(eachStudFields[3]) != 0;
                User eachUser = new User
                        (eachStudFields[0], eachStudFields[1], eachStudFields[2], isAdmin);
                UserList.add(eachUser);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Error reading file " + ex);
        } finally {
            try {
                inputStream.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return UserList;
    }
}
