package com.nelsonalfo.paymentapp.presentation;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.nelsonalfo.paymentapp.presentation.amount.AmountFragment;
import com.nelsonalfo.paymentapp.presentation.cardissuers.CardIssuersFragment;
import com.nelsonalfo.paymentapp.presentation.cuotas.CuotasFragment;
import com.nelsonalfo.paymentapp.presentation.paymentmethods.PaymentMethodsFragment;

import java.util.ArrayList;
import java.util.List;

public class PaymentStepsAdapter extends FragmentPagerAdapter {
    static final int AMOUNT = 0;
    static final int PAYMENT_METHODS = 1;
    static final int CARD_ISSUERS = 2;
    static final int CUOTAS = 3;

    private final List<Fragment> paymentSteps;

    PaymentStepsAdapter(FragmentManager fm) {
        super(fm);

        paymentSteps = new ArrayList<>();

        paymentSteps.add(AMOUNT, AmountFragment.newInstance());
        paymentSteps.add(PAYMENT_METHODS, PaymentMethodsFragment.newInstance());
        paymentSteps.add(CARD_ISSUERS, CardIssuersFragment.newInstance());
        paymentSteps.add(CUOTAS, CuotasFragment.newInstance());
    }

    @Override
    public Fragment getItem(int position) {
        return paymentSteps.get(position);
    }

    @Override
    public int getCount() {
        return paymentSteps.size();
    }
}
