package com.nelsonalfo.paymentapp.presentation.adapters;

import android.support.annotation.NonNull;
import android.view.View;

import com.nelsonalfo.paymentapp.models.CardIssuerModel;
import com.squareup.picasso.Picasso;

public class CardIssuerViewHolder extends ImageAndTitleViewHolder<CardIssuerModel> {

    public CardIssuerViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void bind(CardIssuerModel cardIssuer) {
        title.setText(cardIssuer.getName());
        Picasso.get().load(cardIssuer.getThumbnail()).into(image);
    }
}
