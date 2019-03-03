package com.nelsonalfo.paymentapp.presentation.cuotas;

import android.view.View;

import com.nelsonalfo.paymentapp.models.Cuota;
import com.nelsonalfo.paymentapp.commons.views.ImageAndTitleRecyclerViewAdapter;

import java.util.List;

public class CuotasAdapter extends ImageAndTitleRecyclerViewAdapter<Cuota> {

    public CuotasAdapter(List<Cuota> dataSource, Listener<Cuota> listener) {
        super(dataSource, listener);
    }

    @Override
    public CuotaViewHolder getViewHolder(View view) {
        return new CuotaViewHolder(view);
    }
}
