package com.example.apitonterias2.iot_Control;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.apitonterias2.PeticionesAPI;
import com.example.apitonterias2.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textview.MaterialTextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class IotControlMain extends AppCompatActivity {

    MaterialButton btnSetIp, btnPowerOff, btnReboot;
    MaterialAutoCompleteTextView txtNameIp, txtCurrentIp;
    MaterialTextView stateTextConnect;
    MaterialTextView[] stateLedG;
    SwitchMaterial[] switchLedG;
    MaterialTextView[] stateCommands;

    String addIp;
    Retrofit retrofit;
    PeticionesAPI api;
    InputMethodManager imm;

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    ProgressDialog progressDialog;
    Menu menu;
    int indexFragment;

    SharedPreferences sharedPreferences;
    String json;
    ServersPiList serversPiList;

    int intervalTime = 5000;
    Handler mHandler;
    boolean[] autoSwitchG;
    boolean blockUpdate;
    String[] gpioG;
    String[] commands;
    static boolean netOk;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iot_control_activity_main);

        //Se declara un nuevo progresDialog con el context
        progressDialog = new ProgressDialog(this);
        progressDialog.setIcon(R.mipmap.ic_launcher);//Se agrega un icono
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); //se añade estilo
        progressDialog.setMessage("Conectando..."); //se muestra un mensage


        //Se declaran las sharePreferences para guardar las web favoritas
        sharedPreferences = getSharedPreferences("Ips", MODE_PRIVATE);
        json = sharedPreferences.getString("ip", "");

        //Se crea una nueva instancia de webList con los objetos json
        serversPiList = new ServersPiList();
        serversPiList = serversPiList.fromJson(json);


        btnSetIp = findViewById(R.id.btnSetIp);
        btnPowerOff = findViewById(R.id.btnPowerOff);
        btnReboot = findViewById(R.id.btnReboot);

        switchLedG = new SwitchMaterial[5];
        switchLedG[0] = findViewById(R.id.switchLed1);
        switchLedG[1] = findViewById(R.id.switchLed2);
        switchLedG[2] = findViewById(R.id.switchLed3);
        switchLedG[3] = findViewById(R.id.switchLed4);
        switchLedG[4] = findViewById(R.id.switchLedBucle);

        txtCurrentIp = findViewById(R.id.txtCurrentIp);
        txtNameIp = findViewById(R.id.txtNameIp);
        stateTextConnect = findViewById(R.id.stateTextConnect);

        stateLedG = new MaterialTextView[5];
        stateLedG[0] = findViewById(R.id.stateLed1);
        stateLedG[1] = findViewById(R.id.stateLed2);
        stateLedG[2] = findViewById(R.id.stateLed3);
        stateLedG[3] = findViewById(R.id.stateLed4);
        stateLedG[4] = findViewById(R.id.stateLedBucle);

        commands = new String[2];
        commands[0] = "poweroff";
        commands[1] = "reboot";

        stateCommands = new MaterialTextView[2];
        stateCommands[0] = findViewById(R.id.statePowerOff);
        stateCommands[1] = findViewById(R.id.stateReboot);

        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mHandler = new Handler();
        autoSwitchG = new boolean[5];
        blockUpdate = false;
        gpioG = new String[]{"12", "16", "20", "21", "7"};

        btnSetIp.setOnClickListener(v -> {
            conectToIp();
            Snackbar.make(v, "Ip " + addIp + " cargada", BaseTransientBottomBar.LENGTH_LONG).show();
        });

        switchLedG[0].setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!autoSwitchG[0]) {
                ledOnOff(0, buttonView, gpioG[0], isChecked);
            } else {
                autoSwitchG[0] = false;
                enableSwitch(0, true);
            }
        });

        switchLedG[1].setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!autoSwitchG[1]) {
                ledOnOff(1, buttonView, gpioG[1], isChecked);
            } else {
                autoSwitchG[1] = false;
                enableSwitch(1, true);
            }
        });

        switchLedG[2].setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!autoSwitchG[2]) {
                ledOnOff(2, buttonView, gpioG[2], isChecked);
            } else {
                autoSwitchG[2] = false;
                enableSwitch(2, true);
            }
        });

        switchLedG[3].setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!autoSwitchG[3]) {
                ledOnOff(3, buttonView, gpioG[3], isChecked);
            } else {
                autoSwitchG[3] = false;
                enableSwitch(3, true);
            }
        });

        switchLedG[4].setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!autoSwitchG[4]) {
                ledOnOff(4, buttonView, gpioG[4], isChecked);
                blockUpdate = true;
            } else {
                autoSwitchG[4] = false;
                enableSwitch(4, true);
            }
        });

        btnPowerOff.setOnClickListener(v -> {
            commandsRasp(0);
        });

        btnReboot.setOnClickListener(v -> {
            commandsRasp(1);
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        drawerLayout = findViewById(R.id.activity_iot);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigate_drawer_open, R.string.navigate_drawer_close);
        drawerLayout.addDrawerListener(toggle);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        try {
            if (serversPiList == null) {
                serversPiList = new ServersPiList();
            }

            for (int i = 0; i < serversPiList.serverPi.size(); i++) {

                boolean exist = false;
                String itemInMenu = serversPiList.serverPi.get(i).nameServerPi;
                int x;

                for (x = 0; x < menu.size(); x++) {
                    String iteminShare = menu.getItem(x).toString();
                    if (iteminShare.equals(itemInMenu)) {
                        exist = true;
                        break;
                    }
                }

                if (!exist) {
                    menu.add(1, 1, x, serversPiList.serverPi.get(i).nameServerPi)
                            .setIcon(R.drawable.icon_raspberry_white);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int index = item.getItemId();

        switch (index) {

            case R.id.favorites:
                addIpFavorites();
                break;

            case R.id.Reconectar:
                conectToIp();
                break;

            case 1:
                String name = (String) item.getTitle();

                String ip = "";
                for (int x = 0; x < serversPiList.serverPi.size(); x++) {
                    if (serversPiList.serverPi.get(x).nameServerPi.equals(name)) {
                        ip = serversPiList.serverPi.get(x).ipserverPi;
                        break;
                    }
                }

                txtNameIp.setText(name);
                txtCurrentIp.setText(ip);
                break;
            default:

        }

        toggle.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    public void conectToIp() {
        if (ipOk()) { //Comprueba que haya una ip en el EditText
            close(); //Cierra el teclado si está abierto

            addIp = txtCurrentIp.getText().toString().trim();

            retrofit = new Retrofit.Builder()
                    .baseUrl("http://" + addIp + "/Android_Basics/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .build();

            api = retrofit.create(PeticionesAPI.class);
            startRepeat();
        }
    }

    public void addIpFavorites() {
        if (txtNameIp.getText().toString().length() != 0 && txtCurrentIp.getText().toString().length() != 0) {
            String nameServer = txtNameIp.getText().toString();
            String ipServer = txtCurrentIp.getText().toString();

            boolean serverPi = false;
            try {
                for (int i = 0; i < serversPiList.serverPi.size(); i++) {

                    if (serversPiList.serverPi.get(i).nameServerPi.equals(nameServer)) {
                        serverPi = true;
                        break;
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }

            if (!serverPi) {
                ServerPi serverPiNew = new ServerPi(nameServer, ipServer);

                if (serversPiList == null) {
                    serversPiList = new ServersPiList();
                }
                serversPiList.serverPi.add(serverPiNew);

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("ip", serversPiList.toJson());
                editor.apply();

                onPrepareOptionsMenu(menu);

                Toast.makeText(this, "Servidor Pi añadido a favoritos", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "El servidor Pi ya existe en favoritos", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void ledOnOff(int led, View view, String gpio, boolean ledSwitch) {
        String status;

        close();

        if (!ledSwitch) {
            status = "0";
        } else {
            status = "1";
        }

        if (ipOk() && (!autoSwitchG[led])) {
            Call<ResponseLed> ledOn = api.conmutarLed(gpio, status);

            ledOn.enqueue(new Callback<ResponseLed>() {
                @Override
                public void onResponse(Call<ResponseLed> call, Response<ResponseLed> response) {
                    Call<ResponseLed> comprobar = api.comprobar(gpio);

                    enableSwitch(led, false);

                    comprobar.enqueue(new Callback<ResponseLed>() {
                        @Override
                        public void onResponse(Call<ResponseLed> call, Response<ResponseLed> response) {

                            String text;
                            if (response.body().responseScript.get(0).equals("0")) {
                                text = "Apagado";
                            } else if (response.body().responseScript.get(0).equals("1")) {
                                text = "Encendido";
                            } else {
                                text = "Desconocido";
                            }
                            stateLedG[led].setText(text);
                            Snackbar.make(view, "gpio " + gpio + text, BaseTransientBottomBar.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<ResponseLed> call, Throwable t) {
                            stateLedG[led].setText("Error");
                            Toast.makeText(IotControlMain.this, "Ko. Error en la comprobación " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });


                }

                @Override
                public void onFailure(Call<ResponseLed> call, Throwable t) {
                    Toast.makeText(IotControlMain.this, "Ko. Error " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            enableSwitch(led, true);
            stateLedG[led].setText("Block");
            autoSwitchG[led] = false;
        }
    }


    String[] textCommands = new String[]{"Apagando...", "Reiniciando..."};

    public void commandsRasp(Integer command) {
        Call<ResponseLed> commandSend = api.commandOS(commands[command]);

        commandSend.enqueue(new Callback<ResponseLed>() {
            @Override
            public void onResponse(Call<ResponseLed> call, Response<ResponseLed> response) {

            }

            @Override
            public void onFailure(Call<ResponseLed> call, Throwable t) {

            }
        });

        stateCommands[command].setText(textCommands[command]);

    }


    public void startRepeat() {
        mChecker.run();
    }


    Runnable mChecker = new Runnable() {
        @Override
        public void run() {
            comprobar();

            boolean netOkBefore = netOk;

            ConnectivityManager conManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = conManager.getActiveNetworkInfo();

            if (netInfo != null && netInfo.isConnected()) {
                netOk = true;
                if (netOkBefore != netOk) {
                    Toast.makeText(IotControlMain.this, "Conexión a IP", Toast.LENGTH_SHORT).show();

                    stateTextConnect.setText("Conectado");
                    stateCommands[0].setText("System On");
                    stateCommands[1].setText("System On");
                    enableSwitch(5, true);
                }
            } else {
                netOk = false;
                Toast.makeText(IotControlMain.this, "No hay conexión con IP", Toast.LENGTH_LONG).show();
                stateTextConnect.setText("Sin conexión");
                enableSwitch(5, false);
            }

            mHandler.postDelayed(mChecker, intervalTime);
        }
    };


    public void comprobar() {
        if (ipOk()) {

            for (int gpio = 0; stateLedG.length > gpio; gpio++) {

                if (blockUpdate) {
                    gpio = 4;
                }

                Log.d("prueba de bucle", "gpio:" + gpio);
                Call<ResponseLed> comprobar = api.comprobar(gpioG[gpio]);
                int gpio2 = gpio;
                comprobar.enqueue(new Callback<ResponseLed>() {

                    @Override
                    public void onResponse(Call<ResponseLed> call, Response<ResponseLed> response) {

                        if (response.body().responseScript.get(0).equals("0")) {
                            Log.d("Leds", "Led" + gpio2 + " Apagado");

                            if (switchLedG[gpio2].isChecked()) {
                                autoSwitchG[gpio2] = true;
                                switchLedG[gpio2].setChecked(false);
                            }

                            if (gpioG[gpio2].equals("7") && blockUpdate) {
                                blockUpdate = false;
                            }

                            stateLedG[gpio2].setText("Apagado");
                        } else if (response.body().responseScript.get(0).equals("1")) {
                            Log.d("Leds", "Led" + gpio2 + " Encendido");

                            if (!switchLedG[gpio2].isChecked()) {
                                autoSwitchG[gpio2] = true;
                                switchLedG[gpio2].setChecked(true);
                            }


                            stateLedG[gpio2].setText("Encendido");
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseLed> call, Throwable t) {
                        //stateTextConnect.setText("ERROR");
                        stateLedG[gpio2].setText("Error");
                    }
                });
            }
        }
    }


    public void close() {
        imm.hideSoftInputFromWindow(btnSetIp.getWindowToken(), 0);
    }

    public boolean ipOk() {
        boolean ipOk;
        if (txtCurrentIp.getText().toString().trim().length() != 0) {
            ipOk = true;
        } else {
            Toast.makeText(this, "Falta Ip", Toast.LENGTH_SHORT).show();
            ipOk = false;
        }
        return ipOk;
    }

    public void enableSwitch(int numberSwitch, boolean onOff) {

        if (numberSwitch == 5) {
            for (int s = 0; switchLedG.length > s; s++) {
                switchLedG[s].setEnabled(onOff);
                Log.d("Bloqueos", "Switch " + s + " " + switchLedG[s].isEnabled());
            }

            btnPowerOff.setEnabled(onOff);
            btnReboot.setEnabled(onOff);

        } else {
            switchLedG[numberSwitch].setEnabled(onOff);
        }

    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}