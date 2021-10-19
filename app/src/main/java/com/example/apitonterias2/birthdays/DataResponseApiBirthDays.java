package com.example.apitonterias2.birthdays;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DataResponseApiBirthDays {

    @SerializedName("bdays")
    ArrayList<BirthDayItem> birthDayItems;

    public DataResponseApiBirthDays(ArrayList<BirthDayItem> birthDayItems) {
        this.birthDayItems = birthDayItems;
    }

    public ArrayList<BirthDayItem> getBirthDayItems() {
        return birthDayItems;
    }
}
