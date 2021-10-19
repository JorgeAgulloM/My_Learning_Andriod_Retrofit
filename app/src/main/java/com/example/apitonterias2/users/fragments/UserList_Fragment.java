package com.example.apitonterias2.users.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.apitonterias2.R;
import com.example.apitonterias2.users.DataResponseApiUsers;
import com.example.apitonterias2.users.RecyclerViewAdapterUserList;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.apitonterias2.MainActivity.api;

public class UserList_Fragment extends Fragment {

    RecyclerView recyclerAllUsers;
    MaterialButton btnUserList;
    ProgressDialog progressDialog;
    RecyclerViewAdapterUserList adapterUsers;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_list_fragment, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIcon(R.mipmap.ic_launcher);//Se le agrega un icono
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); //Un estilo
        progressDialog.setMessage("Cargando..."); //Un mensage

        recyclerAllUsers = view.findViewById(R.id.recyclerAllUsers);

        btnUserList = view.findViewById(R.id.btnAddMsg);
        btnUserList.setOnClickListener(v -> {

            progressDialog.show();

            Call<DataResponseApiUsers> list = api.list();

            list.enqueue(new Callback<DataResponseApiUsers>() {
                @Override
                public void onResponse(Call<DataResponseApiUsers> call, Response<DataResponseApiUsers> response) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Lista descargada", Toast.LENGTH_SHORT).show();
                    DataResponseApiUsers datos = response.body();
                    adapterUsers = new RecyclerViewAdapterUserList(getActivity(), datos);
                    recyclerAllUsers.setAdapter(adapterUsers);
//recyclerBands.setAdapter(adapterBands);
                }

                @Override
                public void onFailure(Call<DataResponseApiUsers> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(), "Fallo al descargar la lista. " + t.getMessage(), Toast.LENGTH_LONG).show();
                }

            });

        });

        return view;

    }

}
