package com.nelsonalfo.paymentapp.di;

import com.nelsonalfo.paymentapp.presentation.PaymentActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector
    public abstract PaymentActivity providesPaymentActivity();
}
