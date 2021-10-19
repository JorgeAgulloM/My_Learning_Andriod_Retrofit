package com.example.apitonterias2.bands;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataResponseApiBands {

    @SerializedName("grupos")
    private List<BandsItem> grupos;

    public DataResponseApiBands(List<BandsItem> grupos) {
        this.grupos = grupos;
    }

    public List<BandsItem> getGrupos(){
        return grupos;
    }


}
