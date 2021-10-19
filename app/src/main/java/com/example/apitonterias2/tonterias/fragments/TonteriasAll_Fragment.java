package com.example.apitonterias2.tonterias.fragments;

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

import com.example.apitonterias2.R;
import com.example.apitonterias2.tonterias.DataResponseApiTonterias;
import com.example.apitonterias2.tonterias.RecyclerViewAdapterTonterias;
import com.example.apitonterias2.tonterias.TonteriasItem;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.apitonterias2.MainActivity.api;

public class TonteriasAll_Fragment extends Fragment {

    RecyclerView recyclerTonterias;
    MaterialButton btnAllTonterias;
    RecyclerViewAdapterTonterias adapterTonterias;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tonterias_all_fragment, container, false);

        recyclerTonterias = view.findViewById(R.id.recyclerTonterias);
        recyclerTonterias.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerTonterias.setLayoutManager(layoutManager);

        //Se declara un nuevo progresDialog con el context
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIcon(R.mipmap.ic_launcher);//Se agrega un icono
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); //se añade estilo
        progressDialog.setMessage("Un momento por favor..."); //se muestra un mensage

        btnAllTonterias = view.findViewById(R.id.btnAllTonterias);
        btnAllTonterias.setOnClickListener(v -> {
            progressDialog.show();

            Call<ArrayList<TonteriasItem>> tontAs = api.tonterias();

            tontAs.enqueue(new Callback<ArrayList<TonteriasItem>>() {
                @Override
                public void onResponse(Call<ArrayList<TonteriasItem>> call, Response<ArrayList<TonteriasItem>> response) {
                    progressDialog.dismiss();
                    Snackbar.make(view, "Aquí tienes todas las tonterías", BaseTransientBottomBar.LENGTH_LONG).show();

                    ArrayList<TonteriasItem> datos = response.body();
                    adapterTonterias = new RecyclerViewAdapterTonterias(getContext(), datos);
                    recyclerTonterias.setAdapter(adapterTonterias);

                }

                @Override
                public void onFailure(Call<ArrayList<TonteriasItem>> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Algo ha fallado al cargar las tonterías. " + t.getCause(), Toast.LENGTH_SHORT).show();
                }
            });


        });

        return view;
    }
}
