package com.nelsonalfo.paymentapp.presentation.paymentmethods;

import android.support.annotation.NonNull;
import android.view.View;

import com.nelsonalfo.paymentapp.models.PaymentMethod;
import com.nelsonalfo.paymentapp.commons.views.ImageAndTitleViewHolder;
import com.squareup.picasso.Picasso;

public class PaymentMethodViewHolder extends ImageAndTitleViewHolder<PaymentMethod> {

    public PaymentMethodViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void bind(PaymentMethod paymentMethod) {
        title.setText(paymentMethod.getName());

        Picasso.get().setLoggingEnabled(true);
        Picasso.get().load(paymentMethod.getThumbnail()).into(image);
    }
}
