package com.example.apitonterias2.bands;

import com.google.gson.annotations.SerializedName;

public class BandsItem {

	@SerializedName("descripcion")
	private String descripcion;

	@SerializedName("imagen")
	private String imagen;

	@SerializedName("nombre")
	private String nombre;

	public String getDescripcion(){	return descripcion;	}

	public String getImagen(){
		return imagen;
	}

	public String getNombre(){
		return nombre;
	}
}