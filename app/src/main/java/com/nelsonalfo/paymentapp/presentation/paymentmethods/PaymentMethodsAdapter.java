package com.nelsonalfo.paymentapp.presentation.paymentmethods;

import android.view.View;

import com.nelsonalfo.paymentapp.models.PaymentMethodModel;
import com.nelsonalfo.paymentapp.commons.views.ImageAndTitleRecyclerViewAdapter;

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
