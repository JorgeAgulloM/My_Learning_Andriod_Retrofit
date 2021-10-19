package com.example.apitonterias2.tools.fragments;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.example.apitonterias2.tools.ToolsActivity;
import com.google.android.material.button.MaterialButton;

import static android.app.Activity.RESULT_OK;

public class ToolsMain_Fragment extends Fragment {

    RecyclerView recyclerAllMsg;
    MaterialButton btnAddMsg, btnOpenCamera, btnAddAudio;
    ProgressDialog progressDialog;

    @SuppressLint("QueryPermissionsNeeded")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tools_principal_fragment,container,false);

        recyclerAllMsg = view.findViewById(R.id.recyclerAllUsers);
        recyclerAllMsg.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerAllMsg.setLayoutManager(layoutManager);

        //Se declara un nuevo progresDialog con el context
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIcon(R.mipmap.ic_launcher);//Se agrega un icono
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); //se añade estilo


        btnAddMsg = view.findViewById(R.id.btnAddMsg);
        btnAddMsg.setOnClickListener(v -> {
            progressDialog.setMessage("Un momento por favor..."); //se muestra un mensage
            progressDialog.show();




        });





        btnOpenCamera = view.findViewById(R.id.btnOpenCamera);
        btnOpenCamera.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (intent.resolveActivity(getActivity().getPackageManager()) != null){
                progressDialog.setMessage("Abriendo cámara..."); //se muestra un mensage
                progressDialog.show();
                startActivityForResult(intent, 1);
                progressDialog.dismiss();

            } else {
                Toast.makeText(getContext(), "La cámara no está disponible", Toast.LENGTH_LONG).show();
            }
        });



        btnAddAudio = view.findViewById(R.id.btnAddAudio);
        btnAddAudio.setOnClickListener(v -> {

        });
        return view;
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == 1) {
                gestionCameraData(data);
            }
        }
    }

    public void gestionCameraData(Intent data){
        ToolsActivity activity = new ToolsActivity();
        activity.receibeData(data);
    }

}
