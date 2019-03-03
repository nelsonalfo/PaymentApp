package com.nelsonalfo.paymentapp.presentation.cuotas;

import android.support.annotation.NonNull;
import android.view.View;

import com.nelsonalfo.paymentapp.models.Cuota;
import com.nelsonalfo.paymentapp.commons.views.ImageAndTitleViewHolder;

public class CuotaViewHolder extends ImageAndTitleViewHolder<Cuota> {

    public CuotaViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void bind(Cuota paymentMethod) {
        title.setText(paymentMethod.getRecommendedMessage());
        image.setVisibility(View.GONE);
    }
}
