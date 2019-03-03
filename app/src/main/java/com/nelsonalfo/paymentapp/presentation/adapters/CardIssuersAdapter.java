package com.nelsonalfo.paymentapp.presentation.adapters;

import android.view.View;

import com.nelsonalfo.paymentapp.models.CardIssuerModel;

import java.util.List;

public class CardIssuersAdapter extends ImageAndTitleRecyclerViewAdapter<CardIssuerModel> {

    public CardIssuersAdapter(List<CardIssuerModel> dataSource, Listener<CardIssuerModel> listener) {
        super(dataSource, listener);
    }

    @Override
    public CardIssuerViewHolder getViewHolder(View view) {
        return new CardIssuerViewHolder(view);
    }
}
