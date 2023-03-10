package com.example.foodiedelivery.dbhelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DbHelper extends SQLiteOpenHelper {
    private static final String TAG = "FoodiesDB";
    private static final String DB_NAME = "FoodiesDB";
    private static final int DB_VERSION = 1;

    // Table : Restaurants
    private static final String RES_TABLE_NAME = "Restaurants";
    private static final String COL_ID = "id";
    private static final String COL_NAME = "res_name";
    private static final String COL_SCORE = "res_score";
    private static final String COL_RATINGS = "res_ratings";
    private static final String COL_CATEGORY = "res_category";
    private static final String COL_PRICE_RANGE = "price_range";
    private static final String COL_ADDRESS = "full_address";
    private static final String COL_ZIP_CODE = "zip_code";
    private static final String COL_LAT = "lat";
    private static final String COL_LONG = "long";
    // Table : Users
    private static final String USER_TABLE_NAME = "Users";
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_EMAIL = "email";
    private static final String COLUMN_USER_PASSWORD = "password";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_ISADMIN = "isAdmin";

    // Table : menu
    private static final String MENU_TABLE_NAME = "menu";
    private static final String COLUMN_RESTAURANT_ID = "restaurant_id";
    private static final String COLUMN_CATEGORY = "category";
    private static final String COLUMN_FOOD_NAME = "food_name";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_PRICE = "price";

    private Context mContext;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSql = "CREATE TABLE " + RES_TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " TEXT, " +
                COL_SCORE + " TEXT, " +
                COL_RATINGS + " TEXT, " +
                COL_CATEGORY + " TEXT, " +
                COL_PRICE_RANGE + " TEXT, " +
                COL_ADDRESS + " TEXT, " +
                COL_ZIP_CODE + " TEXT, " +
                COL_LAT + " REAL, " +
                COL_LONG + " REAL" +
                ")";
        db.execSQL(createTableSql);

        String createUserTableSql = "CREATE TABLE " + USER_TABLE_NAME + "("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_USER_EMAIL + " TEXT UNIQUE, "
                + COLUMN_USER_PASSWORD + " TEXT,"
                + COLUMN_USERNAME + " TEXT,"
                + COLUMN_ISADMIN + " INTEGER"
                + ")";

        // Execute table creation SQL statement
        db.execSQL(createUserTableSql);

        String CREATE_MENU_TABLE = "CREATE TABLE " + MENU_TABLE_NAME + " ("
                + COLUMN_RESTAURANT_ID + " INTEGER,"
                + COLUMN_CATEGORY + " TEXT,"
                + COLUMN_FOOD_NAME + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_PRICE + " REAL)";

        db.execSQL(CREATE_MENU_TABLE);


        String createAdminSql = String.format("INSERT INTO %s (%s,%s,%s,%s) values (%s,%s,%s,%s)",
                USER_TABLE_NAME,
                COLUMN_USER_EMAIL,
                COLUMN_USER_PASSWORD,
                COLUMN_USERNAME,
                COLUMN_ISADMIN,
                "'admin'",
                "'admin'",
                "'admin'",
                1);
        db.execSQL(createAdminSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableSql = "DROP TABLE IF EXISTS " + RES_TABLE_NAME;
        String dropUserTableSql = "DROP TABLE IF EXISTS " + USER_TABLE_NAME;
        db.execSQL(dropTableSql);
        db.execSQL(dropUserTableSql);
        onCreate(db);
    }

    public void importResDataFromCSV(InputStream inputStream) {
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
            SQLiteDatabase db = getWritableDatabase();
            String[] dataRow;
            while ((dataRow = reader.readNext()) != null) {
                ContentValues values = new ContentValues();
                values.put(COL_ID, dataRow[0]);
                values.put(COL_NAME, dataRow[2]);
                values.put(COL_SCORE, dataRow[3]);
                values.put(COL_RATINGS, dataRow[4]);
                values.put(COL_CATEGORY, dataRow[5]);
                values.put(COL_PRICE_RANGE, dataRow[6]);
                values.put(COL_ADDRESS, dataRow[7]);
                values.put(COL_ZIP_CODE, dataRow[8]);
                values.put(COL_LAT, Float.parseFloat(dataRow[9]));
                values.put(COL_LONG, Float.parseFloat(dataRow[10]));
                db.insert(RES_TABLE_NAME, null, values);
            }
            inputStream.close();
            reader.close();
//            db.close();
        } catch (IOException e) {
            Log.e(TAG, "Error importing data from CSV", e);
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }

    public void importMenuDataFromCSV(InputStream inputStream) {
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(inputStream));
            SQLiteDatabase db = getWritableDatabase();
            String[] dataRow;
            while ((dataRow = reader.readNext()) != null) {
                ContentValues values = new ContentValues();
                values.put(COLUMN_RESTAURANT_ID, Integer.parseInt(dataRow[0]));
                values.put(COLUMN_CATEGORY, dataRow[1]);
                values.put(COLUMN_FOOD_NAME, dataRow[2]);
                values.put(COLUMN_DESCRIPTION, dataRow[3]);
                values.put(COLUMN_PRICE, Double.parseDouble(dataRow[4].split(" ")[0]));
                db.insert(MENU_TABLE_NAME, null, values);
            }
            inputStream.close();
            reader.close();
//            db.close();
        } catch (IOException e) {
            Log.e(TAG, "Error importing data from CSV", e);
        } catch (CsvValidationException e) {
            throw new RuntimeException(e);
        }
    }
}

