package com.example.apitonterias2.tonterias.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;

import com.example.apitonterias2.PeticionesAPI;
import com.example.apitonterias2.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;

public class TonteriasFile_Fragment extends Fragment {

    MaterialButton btnSendFile;
    ContentLoadingProgressBar bar;
    MaterialTextView txtResultUpload;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tonterias_file_fragment, container, false);

        btnSendFile = view.findViewById(R.id.btnSendFile);
        bar = view.findViewById(R.id.progressHorizontalBar);
        txtResultUpload = view.findViewById(R.id.txtResultUpload);

        btnSendFile.setOnClickListener(v -> {
            txtResultUpload.setText("");

            bar.setActivated(true);
            bar.show();
            File file = new File(getContext().getCacheDir(), "fileToUpload.txt");

            if (!file.exists()) {
                txtResultUpload.setText("El fichero no existe ...\n");
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write("Este fichero ha sido subido con la app de Jorge A.M. Api Tonterias".getBytes());
                    fos.close();

                    txtResultUpload.setText(txtResultUpload.getText().toString() +"Se crea el fichero de texto ...\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                txtResultUpload.setText("El fichero existe ...\n");
            }

            OkHttpClient okHttpClient = new OkHttpClient();

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("https://tonterias.herokuapp.com/api/")
                    .client(okHttpClient);
            Retrofit retrofit = builder.build();
            PeticionesAPI api = retrofit.create(PeticionesAPI.class);

            RequestBody requestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("myfile", file.getName(), requestBody);
            txtResultUpload.setText(txtResultUpload.getText().toString() + "Se crea el body ... \n");

            String textString = "yujuuuuuu";
            RequestBody description = RequestBody.create(MultipartBody.FORM, textString);
            txtResultUpload.setText(txtResultUpload.getText().toString() + "Se crea la descripción ... \n");

            /*Call<ResponseBody> uploadFile = api.uploadFil(description, body);
            txtResultUpload.setText(txtResultUpload.getText().toString() + "Se inicia la subida del archivo ... \n");

            uploadFile.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    txtResultUpload.setText(txtResultUpload.getText().toString() + "Archivo subido con éxito!");
                    bar.hide();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    txtResultUpload.setText(txtResultUpload.getText().toString() + "Fallo al subir el archivo!!!");
                    bar.hide();
                }

            });

             */

        });

        return view;

    }

}
