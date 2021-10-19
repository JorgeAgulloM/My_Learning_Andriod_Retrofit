package com.example.apitonterias2.users;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.apitonterias2.users.fragments.UserList_Fragment;
import com.example.apitonterias2.users.fragments.UserLogin_Fragment;
import com.example.apitonterias2.users.fragments.UserNew_Fragment;

public class UserAdapter extends FragmentPagerAdapter {
    public UserAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new UserLogin_Fragment();
                break;
            case 1:
                fragment = new UserNew_Fragment();
                break;
            case 2:
                fragment = new UserList_Fragment();
                break;
            default:
                fragment = null;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String tittle;
        switch (position) {
            case 0:
                tittle = "Login";
                break;
            case 1:
                tittle = "Nuevo";
                break;
            case 2:
                tittle = "Lista";
                break;
            default:
                tittle = "";
        }

        return tittle;
    }
}
