package com.nelsonalfo.paymentapp.models;

import com.google.gson.annotations.SerializedName;

public class Cuota {

    @SerializedName("installments")
    private int installments;

    @SerializedName("recommended_message")
    private String recommendedMessage;


    public Cuota() {
    }

    public Cuota(int installments, String recommendedMessage) {
        this.installments = installments;
        this.recommendedMessage = recommendedMessage;
    }


    public int getInstallments() {
        return installments;
    }

    public String getRecommendedMessage() {
        return recommendedMessage;
    }

    public void setInstallments(Integer installments) {
        this.installments = installments;
    }

    public void setRecommendedMessage(String recommendedMessage) {
        this.recommendedMessage = recommendedMessage;
    }
}
