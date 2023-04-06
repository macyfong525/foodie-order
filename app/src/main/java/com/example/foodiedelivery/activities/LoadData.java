//package com.example.foodiedelivery.activities;
//
//import android.os.Bundle;
//import android.util.Log;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.room.Room;
//
//import com.example.foodiedelivery.R;
//import com.example.foodiedelivery.db.FoodieDatabase;
//import com.example.foodiedelivery.interfaces.DishDao;
//import com.example.foodiedelivery.interfaces.RestaurantDao;
//import com.example.foodiedelivery.interfaces.UserDao;
//import com.example.foodiedelivery.models.Dish;
//import com.example.foodiedelivery.models.Restaurant;
//import com.example.foodiedelivery.models.User;
//import com.opencsv.CSVReader;
//import com.opencsv.exceptions.CsvValidationException;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public class LoadData extends AppCompatActivity {
//
//    private static final String TAG = "LoadData";
//    FoodieDatabase fd;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        try {
//            // open the sample data file as an InputStream
//
//
//            fd = Room.databaseBuilder
//                    (getApplicationContext(), FoodieDatabase.class, "foodie.db").build();
//            UserDao userDao = fd.userDao();
//            DishDao menuDao = fd.menuDao();
//            RestaurantDao resDao = fd.RestaurantDao();
//
//            ExecutorService executorService = Executors.newSingleThreadExecutor();
//            executorService.execute(() -> {
//
//                List<Restaurant> res = ReadResCSV();
//                resDao.insertRestaurantsFromList(res);
//
//                List<User> users = ReadUserCSV();
//                userDao.insertUsersFromList(users);
//
//                List<Dish> dishes = ReadMenuCSV();
//                menuDao.insertDishesFromList(dishes);
//            });
//
//        } catch (Exception e) {
//            // handle the error
//            e.printStackTrace();
//        }
//    }
//
//    private List<Restaurant> ReadResCSV() {
//        List<Restaurant> ResList = new ArrayList<>();
//
//        InputStream inputStreamRes = getResources().openRawResource(R.raw.restaurant);
//        CSVReader reader = new CSVReader(new InputStreamReader(inputStreamRes));
//
//        try {
//            String[] dataRow;
//            while ((dataRow = reader.readNext()) != null) {
//                Log.d(TAG, "ReadResCSV res first row: " + Arrays.toString(dataRow));
//                Restaurant res = new Restaurant(dataRow[0], dataRow[1], "Change later for url");
//                ResList.add(res);
//            }
//        } catch (IOException | CsvValidationException ex) {
//            throw new RuntimeException("Error reading file " + ex);
//        } finally {
//            try {
//                inputStreamRes.close();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//        return ResList;
//    }
//
//
//    private List<Dish> ReadMenuCSV() {
//        List<Dish> DishList = new ArrayList<>();
//
//        InputStream inputStreamMenu = getResources().openRawResource(R.raw.menu);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStreamMenu));
//
//        try {
//            String menuLine;
//            while ((menuLine = reader.readLine()) != null) {
//                String[] eachLine = menuLine.split(",");
//                Log.d(TAG, "menu: " + Arrays.toString(eachLine));
//                RestaurantDao resDao = fd.RestaurantDao();
//                long id = resDao.getResId(Integer.parseInt(eachLine[0]));
//                Dish eachDish = new Dish(id, eachLine[1], Double.parseDouble(eachLine[2]));
//                DishList.add(eachDish);
//            }
//        } catch (IOException ex) {
//            throw new RuntimeException("Error reading file " + ex);
//        } finally {
//            try {
//                inputStreamMenu.close();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//        return DishList;
//
//    }
//
//    private List<User> ReadUserCSV() {
//        List<User> UserList = new ArrayList<>();
//
//        InputStream inputStream
//                = getResources().openRawResource(R.raw.user);
//        BufferedReader reader
//                = new BufferedReader(new InputStreamReader(inputStream));
//
//        try {
//            String eachLine;
//            while ((eachLine = reader.readLine()) != null) {
//                String[] eachStudFields = eachLine.split(",");
//                boolean isAdmin = eachStudFields[3].equals('0') ? false : true;
//                User eachUser = new User
//                        (eachStudFields[0], eachStudFields[1], eachStudFields[2], isAdmin);
//                UserList.add(eachUser);
//            }
//        } catch (IOException ex) {
//            throw new RuntimeException("Error reading file " + ex);
//        } finally {
//            try {
//                inputStream.close();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//        return UserList;
//    }
//}
