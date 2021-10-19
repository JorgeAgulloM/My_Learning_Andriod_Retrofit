package com.example.apitonterias2.tools;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import com.example.apitonterias2.R;
import com.example.apitonterias2.tools.fragments.ToolsPhoto_Fragment;
import com.google.android.material.tabs.TabLayout;

import java.lang.annotation.Target;

public class ToolsActivity extends AppCompatActivity {

    TabLayout tabLayoutTools;
    ViewPager viewPagerMainTools;
    ToolsAdapter adapter;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tools_main_activity);

        this.setTitle("Herramientas");

        tabLayoutTools = findViewById(R.id.tabLayoutTools);
        viewPagerMainTools = findViewById(R.id.viewPagerMainTools);

        adapter = new ToolsAdapter(getSupportFragmentManager());
        viewPagerMainTools.setAdapter(adapter);

        tabLayoutTools.setupWithViewPager(viewPagerMainTools);

    }

    public void receibeData(Intent data){
        this.intent = data;
    }

    public Intent sendData(){
        return intent;
    }

    public void sendData0(Intent data){
        /*
        ToolsPhoto_Fragment tp_fragment = new ToolsPhoto_Fragment();
        tp_fragment.receibeData(data);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.tools_photo_fragment, tp_fragment, "data");
        transaction.commit();


         */
    }


}