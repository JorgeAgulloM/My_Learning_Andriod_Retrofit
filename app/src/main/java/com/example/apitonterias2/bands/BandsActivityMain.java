package com.example.apitonterias2.bands;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.example.apitonterias2.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.apitonterias2.MainActivity.api;

public class BandsActivityMain extends AppCompatActivity {

    RecyclerView recyclerBands;
    MaterialButton btnBands;
    RecyclerViewAdapterBands adapterBands;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bands_activity_main);

        this.setTitle("API Tonterias/bandas de rock");

        /*Control del RecyclerView en el que se asimila el objeto xml, se fija el tamaño (Siempre y cuando
            sepamos que el tamaño no va a cambiar, se crea el LayoutManager y se le asigna.
        */
        recyclerBands = findViewById(R.id.recyclerBands);
        recyclerBands.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerBands.setLayoutManager(layoutManager);

        //Se declara un nuevo progresDialog con el context
        progressDialog = new ProgressDialog(this);
        progressDialog.setIcon(R.mipmap.ic_launcher);//Se agrega un icono
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); //se añade estilo
        progressDialog.setMessage("Un momento por favor..."); //se muestra un mensage

        /*Variable api importada de MainActivity
          Aquí debería ir la implementación de retrofit junto con la inicialización de la variable -api-,
          para no repetirla la he colocado en el MainActivity como -public static-
          para usarla en cualquier parte.
        */

        //Botón para adquirir los grupos de la api de tonterias.
        btnBands = findViewById(R.id.btnBands);
        btnBands.setOnClickListener(v -> {
            progressDialog.show(); //se muestra el progressDialog

            //Se crea el objeto que vamos a usar para hacer la llamada.
            Call<DataResponseApiBands> obtenerBandas = api.obtenerBandas();

            //Se usa el objeto enqueue para usar los datos que nos devuelve al hacer la petición.
            obtenerBandas.enqueue(new Callback<DataResponseApiBands>() {
                @Override
                public void onResponse(Call<DataResponseApiBands> call, Response<DataResponseApiBands> response) {
                    progressDialog.dismiss(); //se cierra el progressDialog
                    Snackbar.make(v, "Solicitud enviada correctamente", BaseTransientBottomBar.LENGTH_LONG).show();

                    //Se recogen los valores de la respuesta con el adapter para mostrarlos en el recyclerView.
                    DataResponseApiBands datos = response.body();
                    adapterBands = new RecyclerViewAdapterBands(getApplicationContext(), datos);
                    recyclerBands.setAdapter(adapterBands);

                }

                @Override
                public void onFailure(Call<DataResponseApiBands> call, Throwable t) {
                    progressDialog.dismiss(); //se cierra el progressDialog
                    Toast.makeText(BandsActivityMain.this, "Fallo en la solicitud. " + t.getMessage(), Toast.LENGTH_SHORT).show();

                }

            });

        });

    }
}