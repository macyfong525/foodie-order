package com.example.foodiedelivery.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "users",indices={@Index(value={"email"},unique = true)})
public class User {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name="id")
    private int id;

    @NonNull
    @ColumnInfo(name="email")
    private String email;


    @NonNull
    @ColumnInfo(name="password")
    private String password;

    @NonNull
    @ColumnInfo(name="username")
    private String username;

    @NonNull
    @ColumnInfo(name="isadmin")
    private boolean isAdmin;

    public User(@NonNull Integer id,@NonNull String email,@NonNull String password,@NonNull String username, @NonNull boolean isAdmin) {
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

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(@NonNull String username) {
        this.username = username;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(@NonNull boolean admin) {
        isAdmin = admin;
    }
}
