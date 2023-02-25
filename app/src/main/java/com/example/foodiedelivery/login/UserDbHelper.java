package com.example.foodiedelivery.login;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDbHelper extends SQLiteOpenHelper {
    // Define database name, version, and table name and column names
    private static final String DATABASE_NAME = "FoodiesDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "Users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_USERNAME = "username";

    // Constructor
    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database is created for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Define table creation SQL statement
        String createTableSql = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_EMAIL + " TEXT UNIQUE, "
                + COLUMN_PASSWORD + " TEXT,"
                + COLUMN_USERNAME + " TEXT"
                + ")";

        // Execute table creation SQL statement
        db.execSQL(createTableSql);
    }

    // Called when the database needs to be upgraded to a new version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Define table upgrade SQL statement
        String upgradeTableSql = "DROP TABLE IF EXISTS " + TABLE_NAME;

        // Execute table upgrade SQL statement
        db.execSQL(upgradeTableSql);

        // Call onCreate to create a new database with updated schema
        onCreate(db);
    }

    // Insert a new user into the database
    public long insertUser(String email, String password, String username) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, password);
        values.put(COLUMN_USERNAME, username);

        long newRowId = db.insert(TABLE_NAME, null, values);

        // Close the database connection
        db.close();

        return newRowId;
    }

    // Query the database for a user with a specific email and password
    public User getUserByEmail(String email) {
        SQLiteDatabase db = getReadableDatabase();

        String[] projection = {
                COLUMN_ID,
                COLUMN_EMAIL,
                COLUMN_PASSWORD,
                COLUMN_USERNAME
        };

        String selection = COLUMN_EMAIL + " = ?";
        String[] selectionArgs = { email };

        Cursor cursor = db.query(
                TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        User user = null;

        if (cursor.moveToFirst()) {
            int idColumnIndex = cursor.getColumnIndex(COLUMN_ID);
            int emailColumnIndex = cursor.getColumnIndex(COLUMN_EMAIL);
            int passwordColumnIndex = cursor.getColumnIndex(COLUMN_PASSWORD);
            int userColumnIndex = cursor.getColumnIndex(COLUMN_USERNAME);

            // Check if the column exists in the result set
            if (idColumnIndex != -1 && emailColumnIndex != -1 && passwordColumnIndex != -1 && userColumnIndex!=-1) {
                int id = cursor.getInt(idColumnIndex);
                String userEmail = cursor.getString(emailColumnIndex);
                String userPassword = cursor.getString(passwordColumnIndex);
                String userName = cursor.getString(userColumnIndex);

                user = new User(id, userEmail, userPassword, userName);
            }
        }

        cursor.close();

        return user;
    }
}

