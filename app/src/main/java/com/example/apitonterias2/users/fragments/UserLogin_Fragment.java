package com.example.apitonterias2.users.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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

import static com.example.apitonterias2.MainActivity.api;

public class UserLogin_Fragment extends Fragment {

    MaterialAutoCompleteTextView txtName, txtPass;
    MaterialButton btnUserLogin;
    MaterialTextView txtViewUserLoged;
    ProgressDialog progressDialog;
    InputMethodManager imm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_login_fragment, container, false);

        //Carga el objeto xml en la variable
        txtName = view.findViewById(R.id.txtName);
        txtPass = view.findViewById(R.id.txtPass);
        txtViewUserLoged = view.findViewById(R.id.txtViewUserLoged);

        progressDialog = new ProgressDialog(getContext()); //se carga un nuevo progressDialog
        progressDialog.setIcon(R.mipmap.ic_launcher);//Se le agrega un icono
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); //Un estilo
        progressDialog.setMessage("Cargando..."); //Un mensage



        btnUserLogin = view.findViewById(R.id.btnUserLogin); //Carga el objeto xml en la variable
        btnUserLogin.setOnClickListener(v -> {
            if (txtName.getText().toString() != "") {

                if (txtPass.getText().toString() != "") {

                    imm.hideSoftInputFromWindow(btnUserLogin.getWindowToken(), 0); //Oculta el teclado

                    progressDialog.show();  //Muestra un dialogo de progreso

                        String user = txtName.getText().toString(); //carga el nombre en la variable
                        String pass = txtPass.getText().toString(); //carga el pass en la variable

                        Call<String> login = api.login(user, pass); //se crea la variable login con el método de la api.

                        login.enqueue(new Callback<String>() { //Se realiza la llamada y se recupera la info que devuelva el servidor.
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) { //Si la respuesta es buena
                                progressDialog.dismiss(); //se oculta el progressDialog

                                txtViewUserLoged.setText("Usuario logeado: " + user); //se muestra el log en el textView
                                Toast.makeText(getContext(), "Usuario Logeado", Toast.LENGTH_SHORT).show(); //se muestra un Toast

                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) { //Si la respuesta es negativa
                                progressDialog.dismiss(); //se oculta el progressDialog

                                txtViewUserLoged.setText("Petición de login fallida. " + t.getMessage()); //se muestra un log en el textView
                                Toast.makeText(getContext(), "Petición de login fallida " + t.getMessage(), Toast.LENGTH_LONG).show(); //se muestra un Toast

                            }

                        });

                } else {
                    Toast.makeText(view.getContext(), "Introduce una contraseña", Toast.LENGTH_SHORT).show(); //se muestra un Toast
                }
            } else {
                Toast.makeText(view.getContext(), "Introduce un nombre", Toast.LENGTH_SHORT).show(); //se muestra un Toast
            }
        });


        return view;
    }

    //Método sobrescrito para cargar el InputMethodManager y cargarlo en la variable global
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        this.imm = imm;
    }

}
