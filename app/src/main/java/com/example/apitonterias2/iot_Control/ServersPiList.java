package com.example.apitonterias2.iot_Control;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ServersPiList {

    public ArrayList<ServerPi> serverPi;

    public ServersPiList() {
        serverPi = new ArrayList<>();
    }

    //Método para usar la senntencia tojson de Gson para convertir las variables a datos almacenables
    //en sharedpreferences
    public String toJson() {
        Gson gson = new Gson(); //Se declara un nuevo objeto gson
        return gson.toJson(this); //Se devuelve la variable con el contexto
    }

    //Método para usar la sentencia fromjson de gson y convertir datos de sharedpreferences en las
    // datos utiles para las variables necesarias
    public ServersPiList fromJson(String json) {
        Gson gson = new Gson(); //Se declara un nuevo objeto gson.
        //Se declara un nuevo objeto weblist con la variable json y esta clase
        return gson.fromJson(json, ServersPiList.class); //Se devuelve el objeto weblist
    }
}
