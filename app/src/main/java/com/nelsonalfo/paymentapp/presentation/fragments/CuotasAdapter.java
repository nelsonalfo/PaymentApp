package com.nelsonalfo.paymentapp.presentation.fragments;

import android.view.View;

import com.nelsonalfo.paymentapp.models.CuotaModel;
import com.nelsonalfo.paymentapp.commons.views.ImageAndTitleRecyclerViewAdapter;

import java.util.List;

public class CuotasAdapter extends ImageAndTitleRecyclerViewAdapter<CuotaModel> {

    public CuotasAdapter(List<CuotaModel> dataSource, Listener<CuotaModel> listener) {
        super(dataSource, listener);
    }

    @Override
    public CuotaViewHolder getViewHolder(View view) {
        return new CuotaViewHolder(view);
    }
}
