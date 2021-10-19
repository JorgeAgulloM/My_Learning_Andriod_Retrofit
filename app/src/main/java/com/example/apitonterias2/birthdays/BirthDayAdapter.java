package com.example.apitonterias2.birthdays;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.apitonterias2.birthdays.fragments.BirthDayList_Fragment;
import com.example.apitonterias2.birthdays.fragments.BirthDayNew_Fragment;

public class BirthDayAdapter extends FragmentPagerAdapter {
    public BirthDayAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new BirthDayList_Fragment();
                break;
            case 1:
                fragment = new BirthDayNew_Fragment();
                break;
            default:
                fragment = null;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String tittle;
        switch (position) {
            case 0:
                tittle = "Lista de cumpleaños";
                break;
            case 1:
                tittle = "Nuevo cumpleaños";
                break;
            default:
                tittle = "";
        }
        return tittle;
    }
}
