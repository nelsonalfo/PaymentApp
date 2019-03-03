package com.nelsonalfo.paymentapp.presentation.paymentmethods;

import android.view.View;

import com.nelsonalfo.paymentapp.models.PaymentMethod;
import com.nelsonalfo.paymentapp.commons.views.ImageAndTitleRecyclerViewAdapter;

import java.util.List;

public class PaymentMethodsAdapter extends ImageAndTitleRecyclerViewAdapter<PaymentMethod> {

    public PaymentMethodsAdapter(List<PaymentMethod> dataSource, Listener<PaymentMethod> listener) {
        super(dataSource, listener);
    }

    @Override
    public PaymentMethodViewHolder getViewHolder(View view) {
        return new PaymentMethodViewHolder(view);
    }
}
