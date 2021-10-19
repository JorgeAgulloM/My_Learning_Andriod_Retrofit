package com.example.apitonterias2.birthdays;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;

import com.example.apitonterias2.R;
import com.google.android.material.tabs.TabLayout;

public class BirtDayActivityMain extends AppCompatActivity {

    TabLayout tabLayoutBirthDays;
    ViewPager viewPagerBirthDays;
    BirthDayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.birthdays_activity_main);

        this.setTitle("API Tonterias/Cumplaños");

        tabLayoutBirthDays = findViewById(R.id.tabLayoutBirthDays);
        viewPagerBirthDays = findViewById(R.id.viewPagerMainBirthDays);

        adapter = new BirthDayAdapter(getSupportFragmentManager());
        viewPagerBirthDays.setAdapter(adapter);

        tabLayoutBirthDays.setupWithViewPager(viewPagerBirthDays);

    }

    public String clipBoard(){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);
        String copy = item.getHtmlText();

        return copy;
    }

}