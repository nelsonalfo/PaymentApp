package com.nelsonalfo.paymentapp.presentation.cardissuers;

import android.view.View;

import com.nelsonalfo.paymentapp.models.CardIssuer;
import com.nelsonalfo.paymentapp.commons.views.ImageAndTitleRecyclerViewAdapter;

import java.util.List;

public class CardIssuersAdapter extends ImageAndTitleRecyclerViewAdapter<CardIssuer> {

    CardIssuersAdapter(List<CardIssuer> dataSource, Listener<CardIssuer> listener) {
        super(dataSource, listener);
    }

    @Override
    public CardIssuerViewHolder getViewHolder(View view) {
        return new CardIssuerViewHolder(view);
    }
}
