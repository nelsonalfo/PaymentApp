package com.nelsonalfo.paymentapp.presentation.adapters;

import android.support.annotation.NonNull;
import android.view.View;

import com.nelsonalfo.paymentapp.models.PaymentMethodModel;
import com.squareup.picasso.Picasso;

public class PaymentMethodViewHolder extends ImageAndTitleViewHolder<PaymentMethodModel> {

    public PaymentMethodViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void bind(PaymentMethodModel paymentMethod) {
        title.setText(paymentMethod.getName());

        Picasso.get().setLoggingEnabled(true);
        Picasso.get().load(paymentMethod.getThumbnail()).into(image);
    }
}
