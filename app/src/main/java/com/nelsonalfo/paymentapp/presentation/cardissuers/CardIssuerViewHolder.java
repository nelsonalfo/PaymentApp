package com.nelsonalfo.paymentapp.presentation.cardissuers;

import android.support.annotation.NonNull;
import android.view.View;

import com.nelsonalfo.paymentapp.models.CardIssuer;
import com.nelsonalfo.paymentapp.commons.views.ImageAndTitleViewHolder;
import com.squareup.picasso.Picasso;

public class CardIssuerViewHolder extends ImageAndTitleViewHolder<CardIssuer> {

    public CardIssuerViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void bind(CardIssuer cardIssuer) {
        title.setText(cardIssuer.getName());
        Picasso.get().load(cardIssuer.getThumbnail()).into(image);
    }
}
