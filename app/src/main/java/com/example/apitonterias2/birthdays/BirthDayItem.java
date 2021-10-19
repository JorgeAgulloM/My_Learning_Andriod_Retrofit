package com.example.apitonterias2.birthdays;

import com.google.gson.annotations.SerializedName;

public class BirthDayItem {

    @SerializedName("name")
    String name;

    @SerializedName("image")
    String image;

    @SerializedName("date")
    int date;

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public int getDate() {
        return date;
    }

}
