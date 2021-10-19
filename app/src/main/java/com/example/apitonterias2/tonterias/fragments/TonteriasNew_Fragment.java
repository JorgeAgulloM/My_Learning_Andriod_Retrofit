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

import com.example.apitonterias2.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textview.MaterialTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.Field;

import static com.example.apitonterias2.MainActivity.api;

public class TonteriasNew_Fragment extends Fragment {

    MaterialAutoCompleteTextView txtNewTonteria;
    MaterialButton btnNewTonteria;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tonterias_new_fragment, container, false);

        txtNewTonteria = view.findViewById(R.id.txtNewTonteria);

        //Se declara un nuevo progresDialog con el context
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIcon(R.mipmap.ic_launcher);//Se agrega un icono
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); //se añade estilo
        progressDialog.setMessage("Espera un momento..."); //se muestra un mensage

        btnNewTonteria = view.findViewById(R.id.btnNewTonteria);
        btnNewTonteria.setOnClickListener(v -> {

            if (txtNewTonteria.getText().toString() != null && txtNewTonteria.getText().toString() != "") {
                progressDialog.show();

                String tonteria = txtNewTonteria.getText().toString();
                Call<String> newTonteria = api.newTontA(tonteria);

                newTonteria.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        progressDialog.dismiss();

                        if (response.code() >= 500) {
                            Toast.makeText(getContext(), "El servidor tiene un problema, Error " + response.code(), Toast.LENGTH_SHORT).show();

                        } else if (response.code() >= 400) {
                            Toast.makeText(getContext(), "Algo has hecho mal según el servidor, Error " + response.code(), Toast.LENGTH_SHORT).show();

                        } else if (response.code() >= 200) {
                            Toast.makeText(getContext(), "Se ha subido la nueva tontería", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "No se ha podido subir la nueva tontería", Toast.LENGTH_SHORT).show();

                    }
                });


            } else {
                Toast.makeText(getContext(), "Debes introducir alguna tontería", Toast.LENGTH_SHORT).show();
            }

        });

        return view;
    }

}
