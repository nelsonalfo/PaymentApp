package com.nelsonalfo.paymentapp.presentation.cuotas;

import com.nelsonalfo.paymentapp.R;
import com.nelsonalfo.paymentapp.commons.views.PaymentStepsFragment;
import com.nelsonalfo.paymentapp.models.CuotaModel;

import java.util.Collections;
import java.util.List;

public class CuotasFragment extends PaymentStepsFragment<CuotaModel> {
    private CuotasAdapter adapter;

    public CuotasFragment() {
    }

    public static CuotasFragment newInstance() {
        return new CuotasFragment();
    }


    @Override
    public void observe() {
        viewModel.cuotas.observe(this, this::showCuotas);
    }

    @Override
    protected int getTitle() {
        return R.string.cuotas_title;
    }

    @Override
    public CuotasAdapter getAdapter() {
        if (adapter == null) {
            adapter = new CuotasAdapter(Collections.emptyList(), this);
        }
        return adapter;
    }

    public void showCuotas(List<CuotaModel> cuotas) {
        getAdapter().setData(cuotas);
    }

    @Override
    public void onItemSelected(CuotaModel item) {
        viewModel.showSelectedData(item);
    }
}
