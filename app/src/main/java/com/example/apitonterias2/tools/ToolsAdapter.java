package com.example.apitonterias2.tools;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.apitonterias2.tools.fragments.ToolsMain_Fragment;
import com.example.apitonterias2.tools.fragments.ToolsPhoto_Fragment;

public class ToolsAdapter extends FragmentPagerAdapter {


    public ToolsAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new ToolsMain_Fragment();
                break;
            case 1:
                fragment = new ToolsPhoto_Fragment();
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
                tittle = "Herramientas";
                break;
            case 1:
                tittle = "Fotos";
                break;
            default:
                tittle = "";
        }

        return tittle;
    }
}
