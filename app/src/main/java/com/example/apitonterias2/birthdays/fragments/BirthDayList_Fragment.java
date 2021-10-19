package com.example.apitonterias2.birthdays.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.apitonterias2.R;
import com.example.apitonterias2.bands.BandsActivityMain;
import com.example.apitonterias2.bands.DataResponseApiBands;
import com.example.apitonterias2.bands.RecyclerViewAdapterBands;
import com.example.apitonterias2.birthdays.DataResponseApiBirthDays;
import com.example.apitonterias2.birthdays.RecyclerViewAdapterBirthDays;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.apitonterias2.MainActivity.api;

public class BirthDayList_Fragment extends Fragment {

    RecyclerView recyclerBirthDays;
    RecyclerViewAdapterBirthDays adapterBdays;
    MaterialButton btnLoadBirthDays;
    ProgressDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.birthdays_list_fragment, container, false);

        recyclerBirthDays = view.findViewById(R.id.recyclerBirthDays);
        recyclerBirthDays.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerBirthDays.setLayoutManager(layoutManager);

        //Se declara un nuevo progresDialog con el context
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIcon(R.mipmap.ic_launcher);//Se agrega un icono
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); //se añade estilo
        progressDialog.setMessage("Un momento por favor..."); //se muestra un mensage

        btnLoadBirthDays = view.findViewById(R.id.btnLoadBirthDays);
        btnLoadBirthDays.setOnClickListener(v -> {
            progressDialog.show();

            Call<DataResponseApiBirthDays> addBdays = api.obtenerCumpleaños();

            addBdays.enqueue(new Callback<DataResponseApiBirthDays>() {
                @Override
                public void onResponse(Call<DataResponseApiBirthDays> call, Response<DataResponseApiBirthDays> response) {
                    progressDialog.dismiss();
                    Snackbar.make(v, "Solicitud enviada correctamente", BaseTransientBottomBar.LENGTH_LONG).show();

                    //Se recogen los valores de la respuesta con el adapter para mostrarlos en el recyclerView.
                    DataResponseApiBirthDays datos = response.body();
                    adapterBdays = new RecyclerViewAdapterBirthDays(getContext(), datos);
                    recyclerBirthDays.setAdapter(adapterBdays);

                }

                @Override
                public void onFailure(Call<DataResponseApiBirthDays> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Fallo en la solicitud. " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        });


        return view;
    }
}
