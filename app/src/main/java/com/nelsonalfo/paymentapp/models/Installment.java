package com.nelsonalfo.paymentapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Installment {

    @SerializedName("payer_costs")
    private List<Cuota> cuotas;

    public Installment() {
    }

    public Installment(List<Cuota> cuotas) {
        this.cuotas = cuotas;
    }

    public List<Cuota> getCuotas() {
        return cuotas;
    }

    public void setCuotas(List<Cuota> cuotas) {
        this.cuotas = cuotas;
    }
}
