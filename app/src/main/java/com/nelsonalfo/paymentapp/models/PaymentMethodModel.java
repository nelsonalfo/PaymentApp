package com.nelsonalfo.paymentapp.models;

import com.google.gson.annotations.SerializedName;

public class PaymentMethodModel  {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("status")
    private String status;
    @SerializedName("thumbnail")
    private String thumbnail;


    public PaymentMethodModel(String id, String name, String status, String thumbnail) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.thumbnail = thumbnail;
    }

    public PaymentMethodModel() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
