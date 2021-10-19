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
import com.google.android.material.textview.MaterialTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.apitonterias2.MainActivity.api;

public class TonteriasLifeSense_Fragment extends Fragment {

    MaterialTextView txtRequest, txtResult;
    MaterialButton btnResponse;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tonterias_lifesense_fragment, container, false);

        txtRequest = view.findViewById(R.id.txtRequest);
        txtResult = view.findViewById(R.id.txtResult);
        btnResponse = view.findViewById(R.id.btnResponse);

        //Se declara un nuevo progresDialog con el context
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIcon(R.mipmap.ic_launcher);//Se agrega un icono
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); //se añade estilo
        progressDialog.setMessage("Espera un momento..."); //se muestra un mensage

        btnResponse.setOnClickListener(v -> {
            progressDialog.show();

            Call<String> life = api.lifeSense();

            life.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    progressDialog.dismiss();
                    txtResult.setText(response.body());

                    txtRequest.setText("El sentido de la via es");
                    txtResult.setVisibility(View.VISIBLE);
                    btnResponse.setText("Increible");
                    Toast.makeText(getContext(), "Conexión correcta", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    progressDialog.dismiss();
                    txtRequest.setText("Parece que la vida no tiene sentido... \nconexión fallida");
                    Toast.makeText(getContext(), "Conexión fallida", Toast.LENGTH_SHORT).show();
                }
            });
        });

        return view;
    }

}
