package com.nelsonalfo.paymentapp.models;

import com.google.gson.annotations.SerializedName;

public class PaymentMethod {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("status")
    private String status;
    @SerializedName("thumbnail")
    private String thumbnail;
    @SerializedName("payment_type_id")
    private String paymentTypeId;


    public PaymentMethod(String id, String name, String paymentTypeId, String status, String thumbnail) {
        this.id = id;
        this.name = name;
        this.paymentTypeId = paymentTypeId;
        this.status = status;
        this.thumbnail = thumbnail;
    }

    public PaymentMethod() {
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

    public String getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(String paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }
}
