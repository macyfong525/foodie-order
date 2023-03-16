package com.example.foodiedelivery.login;

public class User {

    private int id;
    private String email;
    private String password;

    private String username;

    private boolean isAdmin;

    public User(int id, String email, String password, String username, Boolean isAdmin) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.isAdmin = isAdmin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}
