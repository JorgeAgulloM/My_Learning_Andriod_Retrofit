package com.example.apitonterias2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


import com.example.apitonterias2.bands.BandsActivityMain;
import com.example.apitonterias2.birthdays.BirtDayActivityMain;
import com.example.apitonterias2.iot_Control.IotControlMain;
import com.example.apitonterias2.maps.MapsActivityMain;
import com.example.apitonterias2.tonterias.TonteriasActivityMain;
import com.example.apitonterias2.tools.ToolsActivity;
import com.example.apitonterias2.users.UsersActivityMain;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, DrawerLayout.DrawerListener {

    MaterialButton btnOpenLayoutTonterias, btnGoToTonterias, btnGoToUsers, btnGoToBirthDays,
            btnGoToEvents, btnGoToBands, btnGeocoder, btnRaspberry, btnToolsPhone;

    ConstraintLayout layoutTonterias;
    MaterialTextView txtInfoConNet;


    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    ProgressDialog progressDialog;


    public static PeticionesAPI api;
    public static Retrofit retrofit;
    static boolean netOk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Se declara un nuevo progresDialog con el context
        progressDialog = new ProgressDialog(this);
        progressDialog.setIcon(R.mipmap.ic_launcher);//Se agrega un icono
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); //se añade estilo
        progressDialog.setMessage("Iniciando..."); //se muestra un mensage

        txtInfoConNet = findViewById(R.id.txtInfoConNet);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://superapi.netlify.app/api/") //("https://tonterias.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        api = retrofit.create(PeticionesAPI.class);

        //ABRIR_API#####################
        btnOpenLayoutTonterias = findViewById(R.id.btnOpenLayoutTonterias);
        layoutTonterias = findViewById(R.id.layoutTonterias);
        btnOpenLayoutTonterias.setOnClickListener(v -> {
            if (layoutTonterias.getVisibility() == GONE){
                btnOpenLayoutTonterias.setText("Cerrar \nRetrofit");
                layoutTonterias.setVisibility(View.VISIBLE);
            } else {
                btnOpenLayoutTonterias.setText("Retrofit");
                layoutTonterias.setVisibility(GONE);
            }

        });

        //TONTERIAS#####################
        btnGoToTonterias = findViewById(R.id.btnGoToTonterias);
        btnGoToTonterias.setOnClickListener(v -> {
           selectOption(0);
        });

        //USERS#########################
        btnGoToUsers = findViewById(R.id.btnGoToUsers);
        btnGoToUsers.setOnClickListener(v -> {
            selectOption(1);
        });

        //EVENTS#########################
        btnGoToEvents = findViewById(R.id.btnGoToEvents);
        btnGoToEvents.setOnClickListener(v -> {
            selectOption(2);
        });

        //BIRTHDAYS#####################
        btnGoToBirthDays = findViewById(R.id.btnGoToBirthDays);
        btnGoToBirthDays.setOnClickListener(v -> {
            selectOption(3);
        });

        //BANDS##########################
        btnGoToBands = findViewById(R.id.btnGoToBands);
        btnGoToBands.setOnClickListener(v -> {
            selectOption(4);
        });

        //GPS############################
        btnGeocoder = findViewById(R.id.btnGeocoder);
        btnGeocoder.setOnClickListener(v -> {
            selectOption(5);
        });

        //RASPBERRY_PI###################
        btnRaspberry = findViewById(R.id.btnRaspberry);
        btnRaspberry.setOnClickListener(v -> {
            selectOption(6);
        });

        //TOOLSPHONE#####################
        btnToolsPhone = findViewById(R.id.btnToolsPhone);
        btnToolsPhone.setOnClickListener(v -> {
            selectOption(7);
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        drawerLayout = findViewById(R.id.activity_drawer);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigate_drawer_open, R.string.navigate_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);



        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Muestra por defecto el primer objeto del menú.
        /*
        MenuItem menuItem = navigation_view.getMenu().getItem(0);
        onNavigationItemSelected(menuItem);
        menuItem.setChecked(true);

         */

    }

    private void intent(Class classSelect) {
        Intent usersIntent = new Intent(this, classSelect);
        startActivity(usersIntent);
    }

    private void snackBar(String text) {
        View view = findViewById(R.id.activity_drawer);
        Snackbar.make(view, text, BaseTransientBottomBar.LENGTH_LONG).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        toggle.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }


    @Override
    protected void onResume() {
        this.setTitle("Android Basics");
        super.onResume();
        progressDialog.dismiss();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        boolean netOkBefore = netOk;

        if (hasFocus) {
            ConnectivityManager conManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = conManager.getActiveNetworkInfo();

            if (netInfo != null && netInfo.isConnected()) {
                netOk = true;
                if (netOkBefore != netOk) {
                    Toast.makeText(this, "Conexión a internet correcta", Toast.LENGTH_SHORT).show();

                    String[] typeNet = new String[]{"Red móvil", "Red Wifi", "Red Ethernet", "Red Wimax", "Red Bluetooth"};
                    txtInfoConNet.setText("Conexión a internet: " + netInfo.getState() + ": " + typeNet[netInfo.getType()]);
                }
            } else {
                netOk = false;
                Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_LONG).show();
                txtInfoConNet.setText("No hay conexión a internet");
            }
        }

    }


    //Para evitar que el boton hacia atras cierre la app.
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        if (layoutTonterias.getVisibility() == VISIBLE){
            layoutTonterias.setVisibility(GONE);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        selectOption(item.getOrder());
        return false;
    }

    private void selectOption(int item){

        progressDialog.show();
        switch (item){
            case 0:
                intent(TonteriasActivityMain.class);
                break;
            case 1:
                intent(UsersActivityMain.class);
                break;
            case 2:
                snackBar("Nada aquí aún");
                break;
            case 3:
                intent(BirtDayActivityMain.class);
                break;
            case 4:
                intent(BandsActivityMain.class);
                break;
            case 5:
                intent(MapsActivityMain.class);
                break;
            case 6:
                intent(IotControlMain.class);
                break;
            case 7:
                intent(ToolsActivity.class);
                break;
            default:
        }

    }


    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {
        Toast.makeText(this, "Drawgner abierto",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }


}