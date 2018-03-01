package com.example.dimitri.helpdeal.azureClasses.azureModels;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Dimitri on 30.12.2017.
 */

public class BranchOfferView implements Parcelable {

    @com.google.gson.annotations.SerializedName("id")
    private String id;
    public String getOfferEmployeeId() {
        return id;
    }
    public void setOfferEmployeeId(String id) {
        this.id = id;
    }

    @com.google.gson.annotations.SerializedName("emp_user_id")
    private String emp_user_id;
    public String getEmp_user_id() {
        return emp_user_id;
    }
    public void setEmp_user_id(String emp_user_id) {
        this.emp_user_id = emp_user_id;
    }

    @com.google.gson.annotations.SerializedName("emp_salary_per_hour")
    private String emp_salary_per_hour;
    public String getEmp_salary_per_hour() {
        return emp_salary_per_hour;
    }
    public void setEmp_salary_per_hour(String emp_salary_per_hour) { this.emp_salary_per_hour = emp_salary_per_hour; }

    @com.google.gson.annotations.SerializedName("emp_experience")
    private String emp_experience;
    public String getEmp_experience() {
        return emp_experience;
    }
    public void setEmp_experience(String emp_experience) {
        this.emp_experience = emp_experience;
    }

    @com.google.gson.annotations.SerializedName("emp_category")
    private String emp_category;
    public String getEmp_category() {
        return emp_category;
    }
    public void setEmp_category(String emp_category) {
        this.emp_category = emp_category;
    }

    @com.google.gson.annotations.SerializedName("emp_subcategory")
    private String emp_subcategory;
    public String getEmp_subcategory() {
        return emp_subcategory;
    }
    public void setEmp_subcategory(String emp_subcategory) { this.emp_subcategory = emp_subcategory; }

    @com.google.gson.annotations.SerializedName("emp_description")
    private String emp_description;
    public String getEmp_description() {
        return emp_description;
    }
    public void setEmp_description(String emp_description) { this.emp_description = emp_description; }

    @com.google.gson.annotations.SerializedName("u_firstname")
    private String firstname;
    public String getUserFirstname() {
        return firstname;
    }
    public void getUserFirstName(String firstname) {
        this.firstname = firstname;
    }

    @com.google.gson.annotations.SerializedName("u_name")
    private String name;
    public String getUserName() {
        return name;
    }
    public void setUserName(String name) {
        this.name = name;
    }

    @com.google.gson.annotations.SerializedName("u_email")
    private String email;
    public String getUserEmail() {
        return email;
    }
    public void setUserEmail(String email) {
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

    @com.google.gson.annotations.SerializedName("u_count_of_rating")
    private String u_count_of_rating;
    public void setCountOf_rating(String rating) {
        this.u_count_of_rating = rating;
    }
    public String getCountOfRating() {
        return u_count_of_rating;
    }


    @com.google.gson.annotations.SerializedName("u_summe_of_rating")
    private String u_summe_of_rating;
    public void setSummeOfRating(String rating) {
        this.u_summe_of_rating = rating;
    }
    public String getSummeOfRating() {
        return u_summe_of_rating;
    }

    @com.google.gson.annotations.SerializedName("u_var_email")
    private Boolean u_var_email;
    public void setVarificationEmail(Boolean email) {
        this.u_var_email = email;
    }
    public Boolean getVarificationEmail() {
        return u_var_email;
    }

    @com.google.gson.annotations.SerializedName("u_var_phone")
    private Boolean u_var_phone;
    public void setVarificationPhone(Boolean phone) {
        this.u_var_phone = phone;
    }
    public Boolean getVarificationPhone() {
        return u_var_phone;
    }

    @com.google.gson.annotations.SerializedName("u_var_pass")
    private Boolean u_var_pass;
    public void setVarificationPass(Boolean pass) {
        this.u_var_pass = pass;
    }
    public Boolean getVarificationPass() {
        return u_var_pass;
    }

    @com.google.gson.annotations.SerializedName("u_photo")
    private String u_photo;
    public void setPhoto(String photo) {
        this.u_photo = photo;
    }
    public String getPhoto(){return u_photo;}


    public BranchOfferView() {
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof BranchOfferView && ((BranchOfferView) o).id == id;
    }

    protected BranchOfferView(Parcel in) {
        id = in.readString();
        emp_user_id = in.readString();
        emp_salary_per_hour = in.readString();
        emp_experience = in.readString();
        emp_category = in.readString();
        emp_subcategory = in.readString();
        emp_description = in.readString();
        firstname = in.readString();
        name = in.readString();
        email = in.readString();
        email = in.readString();
        password = in.readString();
        index = in.readString();
        city = in.readString();
        street = in.readString();
        u_count_of_rating = in.readString();
        u_summe_of_rating = in.readString();
        u_photo = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(emp_user_id);
        dest.writeString(emp_salary_per_hour);
        dest.writeString(emp_experience);
        dest.writeString(emp_category);
        dest.writeString(emp_subcategory);
        dest.writeString(emp_description);
        dest.writeString(firstname);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(password);
        dest.writeString(index);
        dest.writeString(city);
        dest.writeString(street);
        dest.writeString(u_count_of_rating);
        dest.writeString(u_summe_of_rating);
        dest.writeString(u_photo);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BranchOfferView> CREATOR = new Creator<BranchOfferView>() {
        @Override
        public BranchOfferView createFromParcel(Parcel in) {
            return new BranchOfferView(in);
        }

        @Override
        public BranchOfferView[] newArray(int size) {
            return new BranchOfferView[size];
        }
    };
}
