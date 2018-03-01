package com.example.dimitri.helpdeal.azureClasses.azureModels;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Dimitri on 11.11.2017.
 */

public class OfferCustomer implements Parcelable {

    @com.google.gson.annotations.SerializedName("id")
    private String id;
    public String getOfferId() {
        return id;
    }
    public void setOfferId(String offerId) {
        this.id= offerId;
    }

    @com.google.gson.annotations.SerializedName("job_categorie")
    private String job_categorie;
    public String getCategorie() {
        return job_categorie;
    }
    public void setCategorie(String categorie) {
        this.job_categorie = categorie;
    }

    @com.google.gson.annotations.SerializedName("job_subCategorie")
    private String job_subCategorie;
    public String getJob_subCategorie() {return job_subCategorie;}
    public void setJob_subCategorie(String job_subCategorie) {this.job_subCategorie = job_subCategorie;}

    @com.google.gson.annotations.SerializedName("job_titel")
    private String job_titel;
    public String getJobTitel() {
        return job_titel;
    }
    public void setJobTitel(String job_titel) {
        this.job_titel= job_titel;
    }

    @com.google.gson.annotations.SerializedName("job_description")
    private String job_description;
    public String getDescription() {
        return job_description;
    }
    public void setOfferDescription(String offerText) {
        this.job_description = offerText;
    }

    @com.google.gson.annotations.SerializedName("job_startdatum")
    private Date job_startdate;
    public Date getJob_startdate() { return job_startdate;}
    public void setJob_startdatum(Date startdate) { this.job_startdate = startdate;}

    @com.google.gson.annotations.SerializedName("job_enddatum")
    private Date job_endtdatum;
    public Date getJob_enddate() { return job_endtdatum; }
    public void setJob_enddate(Date job_enddate) {
        this.job_endtdatum = job_enddate;
    }

    @com.google.gson.annotations.SerializedName("job_userId")
    private String job_userId;
    public String getUserId() {
        return job_userId;
    }
    public void setUserId(String userId) {
        this.job_userId = userId;
    }

    @com.google.gson.annotations.SerializedName("job_index")
    private String job_index;
    public String getJob_index() { return job_index;}
    public void setJob_index(String job_index) { this.job_index = job_index; }

    @com.google.gson.annotations.SerializedName("job_city")
    private String job_city;
    public String getJob_city() { return job_city; }
    public void setJob_city(String job_city) { this.job_city = job_city; }

    @com.google.gson.annotations.SerializedName("job_createdAt")
    private String job_createdAt;
    public String getjob_createdAt() { return job_city; }
    //public void setJob_city(String job_city) { this.job_city = job_city; }


    public OfferCustomer() {}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(job_categorie);
        dest.writeString(job_subCategorie);
        dest.writeString(job_titel);
        dest.writeString(job_description);
        dest.writeString(job_userId);
        dest.writeString(job_index);
        dest.writeString(job_city);
        dest.writeSerializable(job_startdate);
        dest.writeSerializable(job_endtdatum);
    }

    public OfferCustomer(Parcel in) {
        id = in.readString();
        job_categorie = in.readString();
        job_subCategorie = in.readString();
        job_titel = in.readString();
        job_description = in.readString();
        job_userId = in.readString();
        job_index = in.readString();
        job_city = in.readString();
        job_startdate =(Date)in.readSerializable();
        job_endtdatum =(Date)in.readSerializable();
    }

    public static final Creator<OfferCustomer> CREATOR = new Creator<OfferCustomer>() {
        @Override
        public OfferCustomer createFromParcel(Parcel in) {
            return new OfferCustomer(in);
        }

        @Override
        public OfferCustomer[] newArray(int size) {
            return new OfferCustomer[size];
        }
    };
}
