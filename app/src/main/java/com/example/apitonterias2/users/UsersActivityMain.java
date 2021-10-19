package com.example.apitonterias2.users;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.example.apitonterias2.R;
import com.google.android.material.tabs.TabLayout;

public class UsersActivityMain extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPagerMainUsers;
    UserAdapter adapter;
    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity_main);

        this.setTitle("API Tonterias/usuarios");

        tabLayout = findViewById(R.id.tabLayout);
        viewPagerMainUsers = findViewById(R.id.viewPagerMainUsers);

        adapter = new UserAdapter(getSupportFragmentManager());
        viewPagerMainUsers.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPagerMainUsers);
    }

    //Método para esconder/visualizar el teclado virtual
    public void hideKeyboard(Button button, int onOff) {
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(button.getWindowToken(), onOff);
    }

}