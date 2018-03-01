package com.example.dimitri.helpdeal.azureClasses.azureModels;

/**
 * Created by Dimitri on 30.12.2017.
 */

public class OfferEmployee {

    @com.google.gson.annotations.SerializedName("id")
    private String id;
    public String getOfferEmployeeId() {
        return id;
    }
    public void setOfferEmployeeId(String id) { this.id= id;}

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
    public String getEmp_salary_per_hour() { return emp_salary_per_hour; }
    public void setEmp_salary_per_hour(String emp_salary_per_hour) { this.emp_salary_per_hour = emp_salary_per_hour; }

    @com.google.gson.annotations.SerializedName("emp_experience")
    private String emp_experience;
    public String getEmp_experience() { return emp_experience; }
    public void setEmp_experience(String emp_experience) { this.emp_experience = emp_experience; }

    @com.google.gson.annotations.SerializedName("emp_category")
    private String emp_category;
    public String getEmp_category() { return emp_category; }
    public void setEmp_category(String emp_category) { this.emp_category = emp_category; }

    @com.google.gson.annotations.SerializedName("emp_subcategory")
    private String emp_subcategory;
    public String getEmp_subcategory() { return emp_subcategory; }
    public void setEmp_subcategory(String emp_subcategory) { this.emp_subcategory = emp_subcategory; }

    @com.google.gson.annotations.SerializedName("emp_description")
    private String emp_description;
    public String getEmp_description() { return emp_description; }
    public void setEmp_description(String emp_description) { this.emp_description = emp_description; }

    public OfferEmployee() {}
}
