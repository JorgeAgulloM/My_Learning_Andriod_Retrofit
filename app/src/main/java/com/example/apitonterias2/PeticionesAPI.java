package com.example.apitonterias2;

import com.example.apitonterias2.bands.DataResponseApiBands;
import com.example.apitonterias2.birthdays.DataResponseApiBirthDays;
import com.example.apitonterias2.iot_Control.ResponseLed;
import com.example.apitonterias2.tonterias.TonteriasItem;
import com.example.apitonterias2.users.DataResponseApiUsers;

import java.util.ArrayList;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface PeticionesAPI {

    /*
    TONTERIAS###########################################################################################
    GET /api/meaningoflife para obtener la respuesta al sentido de la vida como un string
    GET /api/tonterias para obtener la lista completa de tonterías como un array de object con un string
    POST /api/tonteria para añadir una nueva tontería con el parámetro "quote" de tipo string
    POST /api/upload para subir un fichero con el parámetro "file"
     */
    //El sentido de la vida
    @GET("meaningoflife")
    Call<String> lifeSense();

    //Todas las tonterias
    @GET("db/tonterias")
    Call<ArrayList<TonteriasItem>> tonterias();

    //Nueva tonteria
    @POST("db/tonterias") //####Reportar
    @FormUrlEncoded
    Call<String> newTontA(@Field("quote") String quote);
/*
    //Anulada
    //Subir fichero ### No está en la nueva Api superapi
    @Multipart
    @POST("upload")
    Call<ResponseBody> uploadFile(@Part("file") RequestBody description, @Part MultipartBody.Part file);

 */

    /*
    USUARIOS############################################################################################
    GET /api/users para obtener la lista completa de usuarios
    POST /api/register para añadir un nuevo usuario con los parámetros "user" y "pass" de tipo string
    POST /api/login para loguear un usuario con los parámetros "user" y "pass" de tipo string
     */
    //Lista de usuarios
    @GET("users")
    Call<DataResponseApiUsers> list();

    //Registrar usuario
    @FormUrlEncoded
    @POST("register")
    Call<String> register(@Field("user") String user, @Field("pass") String pass);

    //Login de usuario
    @FormUrlEncoded
    @POST("login")
    Call<String> login(@Field("user") String user, @Field("pass") String pass);


    /*
    CUMPLEAÑOS########################################################################################
    GET /api/bdays para obtener la lista de cumpleaños como un array de object con dos string y un number
    POST /api/bday para añadir un nuevo cumpleaños con los parámetros "name" y "image" de
    tipo string y "date" de tipo number
    */
    //Lista de cumpleaños
    @GET("db/bdays")
    Call<DataResponseApiBirthDays> obtenerCumpleaños();

    //Añadir nuevo cumpleaños
    @POST("db/bdays")
    Call<DataResponseApiBands> upLoadNewBirthDay(@Body RequestBody requestBody);

    /*
    EVENTOS###########################################################################################
    GET /api/events para obtener la lista de eventos como un array de object con un string y un number
    POST /api/event para añadir un nuevo evento con el parámetro "name" de tipo string y "date" de tipo number
     */




    /*
    GRUPOS############################################################################################
    GET /api/bands para obtener la lista de grupos como un array de object con tres string
     */
    //Pedir lista de bandas
    @GET("bands")
    Call<DataResponseApiBands> obtenerBandas();

    //Pedir lista de bandas de trap buuuaaaggggg
    @GET("trapi")
    Call<DataResponseApiBands> obtenerBandasTrap();

    /*
    IOT###############################################################################################
    */

    @FormUrlEncoded
    @POST("Control.php")
    Call<ResponseLed> conmutarLed(@Field("gpio") String gpio, @Field("status") String status);

    @FormUrlEncoded
    @POST("comprobar.php")
    Call<ResponseLed> comprobar(@Field("gpio") String gpio);

    @FormUrlEncoded
    @POST("systemPi.php")
    Call<ResponseLed> commandOS(@Field("command") String command);

}
