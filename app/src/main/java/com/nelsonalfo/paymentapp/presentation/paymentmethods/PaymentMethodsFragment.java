package com.nelsonalfo.paymentapp.presentation.paymentmethods;

import com.nelsonalfo.paymentapp.R;
import com.nelsonalfo.paymentapp.commons.views.PaymentStepsFragment;
import com.nelsonalfo.paymentapp.models.PaymentMethodModel;

import java.util.ArrayList;
import java.util.List;

public class PaymentMethodsFragment extends PaymentStepsFragment<PaymentMethodModel> {
    private PaymentMethodsAdapter adapter;

    public PaymentMethodsFragment() {
    }

    public static PaymentMethodsFragment newInstance() {
        return new PaymentMethodsFragment();
    }


    @Override
    public void observe() {
        viewModel.paymentMethods.observe(this, this::showPaymentMethods);
    }

    @Override
    protected int getTitle() {
        return R.string.select_payment_title;
    }

    @Override
    public PaymentMethodsAdapter getAdapter() {
        if (adapter == null) {
            adapter = new PaymentMethodsAdapter(new ArrayList<>(), this);
        }
        return adapter;
    }

    public void showPaymentMethods(List<PaymentMethodModel> paymentMethods) {
        getAdapter().setData(paymentMethods);
    }

    @Override
    public void onItemSelected(PaymentMethodModel item) {
        viewModel.fetchCardIssuers(item);
    }
}
