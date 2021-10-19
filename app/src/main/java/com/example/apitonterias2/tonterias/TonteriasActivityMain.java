package com.example.apitonterias2.tonterias;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import com.example.apitonterias2.R;
import com.google.android.material.tabs.TabLayout;

public class TonteriasActivityMain extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPagerMainTonterias;
    TonteriasAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tonterias_activity_main);

        this.setTitle("Android Basics/Retrofit");

        tabLayout = findViewById(R.id.tabLayout);
        viewPagerMainTonterias = findViewById(R.id.viewPagerMainTonterias);

        adapter = new TonteriasAdapter(getSupportFragmentManager());
        viewPagerMainTonterias.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPagerMainTonterias);
    }

}