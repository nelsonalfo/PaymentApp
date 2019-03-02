package com.nelsonalfo.paymentapp.presentation.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.nelsonalfo.paymentapp.presentation.fragments.AmountFragment;
import com.nelsonalfo.paymentapp.presentation.fragments.PaymentMethodsFragment;

import java.util.ArrayList;
import java.util.List;

public class PaymentStepsAdapter extends FragmentStatePagerAdapter {
    public static final int AMOUNT = 0;
    public static final int PAYMENT_METHODS = 1;

    private final PaymentMethodsFragment paymentMethodsFragment;
    private final AmountFragment amountFragment;

    private final List<Fragment> paymentSteps;

    public PaymentStepsAdapter(FragmentManager fm) {
        super(fm);

        paymentSteps = new ArrayList<>();

        amountFragment = AmountFragment.newInstance();
        paymentMethodsFragment = PaymentMethodsFragment.newInstance();

        paymentSteps.add(AMOUNT, amountFragment);
        paymentSteps.add(PAYMENT_METHODS, paymentMethodsFragment);
    }

    @Override
    public Fragment getItem(int position) {
        return paymentSteps.get(position);
    }

    @Override
    public int getCount() {
        return paymentSteps.size();
    }

    public PaymentMethodsFragment getPaymentMethodsFragment() {
        return paymentMethodsFragment;
    }

    public AmountFragment getAmountFragment() {
        return amountFragment;
    }
}
