package com.nelsonalfo.paymentapp.presentation.cardissuers;

import android.view.View;

import com.nelsonalfo.paymentapp.models.CardIssuerModel;
import com.nelsonalfo.paymentapp.commons.views.ImageAndTitleRecyclerViewAdapter;

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
