package com.example.apitonterias2.tonterias;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.apitonterias2.tonterias.fragments.TonteriasAll_Fragment;
import com.example.apitonterias2.tonterias.fragments.TonteriasFile_Fragment;
import com.example.apitonterias2.tonterias.fragments.TonteriasLifeSense_Fragment;
import com.example.apitonterias2.tonterias.fragments.TonteriasNew_Fragment;

public class TonteriasAdapter extends FragmentPagerAdapter {
    public TonteriasAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new TonteriasLifeSense_Fragment();
                break;
            case 1:
                fragment = new TonteriasAll_Fragment();
                break;
            case 2:
                fragment = new TonteriasNew_Fragment();
                break;
           // case 3:
             //   fragment = new TonteriasFile_Fragment();
             //   break;
            default:
                fragment = null;
                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String tittle;
        switch (position){
            case 0:
                tittle = "Sentido de la vida";
                break;
            case 1:
                tittle = "Todas las tonterias";
                break;
            case 2:
                tittle = "Nueva tonteria";
                break;
            case 3:
                tittle = "Subir un fichero";
                break;
            default:
                tittle = "";
                break;
        }
        return tittle;
    }
}
