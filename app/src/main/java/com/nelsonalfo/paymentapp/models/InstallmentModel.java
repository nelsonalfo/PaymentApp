package com.nelsonalfo.paymentapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InstallmentModel  {

    @SerializedName("payer_costs")
    private List<CuotaModel> cuotas;


    public List<CuotaModel> getCuotas() {
        return cuotas;
    }

    public void setCuotas(List<CuotaModel> cuotas) {
        this.cuotas = cuotas;
    }
}
