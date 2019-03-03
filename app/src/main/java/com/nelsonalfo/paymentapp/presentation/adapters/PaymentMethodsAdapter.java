package com.nelsonalfo.paymentapp.presentation.adapters;

import android.view.View;

import com.nelsonalfo.paymentapp.models.PaymentMethodModel;

import java.util.List;

public class PaymentMethodsAdapter extends ImageAndTitleRecyclerViewAdapter<PaymentMethodModel> {

    public PaymentMethodsAdapter(List<PaymentMethodModel> dataSource, Listener<PaymentMethodModel> listener) {
        super(dataSource, listener);
    }

    @Override
    public PaymentMethodViewHolder getViewHolder(View view) {
        return new PaymentMethodViewHolder(view);
    }
}
