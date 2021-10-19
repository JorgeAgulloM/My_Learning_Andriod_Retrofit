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

import com.example.apitonterias2.R;
import com.example.apitonterias2.users.UsersActivityMain;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textview.MaterialTextView;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.apitonterias2.MainActivity.api;

public class UserNew_Fragment extends Fragment {

    MaterialTextView txtResult;
    MaterialAutoCompleteTextView txtName, txtPass;
    MaterialButton btnUserLogin;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_new_fragment, container, false);

        txtResult = view.findViewById(R.id.txtResult);
        txtName = view.findViewById(R.id.txtName);
        txtPass = view.findViewById(R.id.txtPass);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIcon(R.mipmap.ic_launcher);//Se le agrega un icono
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); //Un estilo
        progressDialog.setMessage("Cargando..."); //Un mensage

        btnUserLogin = view.findViewById(R.id.btnUserLogin);
        btnUserLogin.setOnClickListener(v -> {

            if (txtName.getText().toString() != "") {

                if (txtPass.getText().toString() != "") {

                    //otra forma para ocultar el teclado desde un fragment (hideKeyboard está en UsersActivityMain)
                    ((UsersActivityMain) Objects.requireNonNull(getActivity())).hideKeyboard(btnUserLogin, 0);

                        progressDialog.show();
                        String name = txtName.getText().toString();
                        String pass = txtPass.getText().toString();

                        Call<String> register = api.register(name, pass);

                        register.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "Registro de usuario realizado.", Toast.LENGTH_SHORT).show();

                                txtName.setText("");
                                txtPass.setText("");

                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "Registro fallido. " + t.getMessage() + ", " + t.getCause(), Toast.LENGTH_SHORT).show();

                            }

                        });


                } else {
                    Toast.makeText(getContext(), "Debes introducir un pass", Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(getContext(), "Debes introducir un nombre de usuario.", Toast.LENGTH_SHORT).show();
            }

        });

        return view;

    }

}
