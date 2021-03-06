package com.nelsonalfo.paymentapp.models;

import com.google.gson.annotations.SerializedName;

public class CardIssuer {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("thumbnail")
    private String thumbnail;


    public CardIssuer(String id, String name, String thumbnail) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public CardIssuer() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
