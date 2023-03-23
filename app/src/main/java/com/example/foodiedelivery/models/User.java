package com.example.foodiedelivery.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
@Entity(tableName = "users")
public class User {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name="id")
    private int id;

    @ColumnInfo(name="email")
    private String email;

    @ColumnInfo(name="password")
    private String password;

    @ColumnInfo(name="username")
    private String username;

    @ColumnInfo(name="isadmin")
    private boolean isAdmin;

    public User(@NonNull Integer id, String email, String password, String username, boolean isAdmin) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.isAdmin = isAdmin;
    }
    @Ignore
    public User() {
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
