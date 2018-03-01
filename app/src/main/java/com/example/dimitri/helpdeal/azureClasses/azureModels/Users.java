package com.example.dimitri.helpdeal.azureClasses.azureModels;

/**
 * Created by Dimitri on 04.10.2017.
 */

public class Users {

    @com.google.gson.annotations.SerializedName("id")
    private String id;
    public void setId(String uid) {
        this.id = uid;
    }
    public String getId() {
        return id;
    }

    @com.google.gson.annotations.SerializedName("u_firstname")
    private String firstname;
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @com.google.gson.annotations.SerializedName("u_name")
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @com.google.gson.annotations.SerializedName("u_email")
    private String email;
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @com.google.gson.annotations.SerializedName("u_password")
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


    @com.google.gson.annotations.SerializedName("u_index")
    private String index;
    public String getIndex() {
        return index;
    }
    public void setIndex(String index) {
        this.index = index;
    }

    @com.google.gson.annotations.SerializedName("u_city")
    private String city;
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    @com.google.gson.annotations.SerializedName("u_street")
    private String street;
    public void setStreet(String street) {
        this.street = street;
    }
    public String getStreet() {
        return street;
    }

    @com.google.gson.annotations.SerializedName("u_phone")
    private String phone;
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }


    public Users(){};

    @Override
    public boolean equals(Object o) {
        return o instanceof Users && ((Users) o).id == id;
    }


}
