package com.nelsonalfo.paymentapp.presentation.cuotas;

import android.support.annotation.NonNull;
import android.view.View;

import com.nelsonalfo.paymentapp.models.CuotaModel;
import com.nelsonalfo.paymentapp.commons.views.ImageAndTitleViewHolder;

public class CuotaViewHolder extends ImageAndTitleViewHolder<CuotaModel> {

    public CuotaViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    @Override
    public void bind(CuotaModel paymentMethod) {
        title.setText(paymentMethod.getRecommendedMessage());
        image.setVisibility(View.GONE);
    }
}
