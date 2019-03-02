package com.nelsonalfo.paymentapp.di.module;

import com.nelsonalfo.paymentapp.presentation.PaymentActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector(modules = PaymentModule.class)
    public abstract PaymentActivity providesPaymentActivity();
}
