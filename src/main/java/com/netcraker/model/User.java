package com.netcraker.model;

import java.sql.Time;

public class User {
    private Integer user_id;
    private String password;
    private String lastName;
    private String firstName;
    private String email;
    private Time creation_time;
    private boolean enabled;


    public User() {
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Time getCreation_time() {
        return creation_time;
    }

    public void setCreation_time(Time creation_time) {
        this.creation_time = creation_time;
    }
}