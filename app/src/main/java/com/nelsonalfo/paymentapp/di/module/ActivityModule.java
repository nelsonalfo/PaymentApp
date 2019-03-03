package com.nelsonalfo.paymentapp.di.module;

import com.nelsonalfo.paymentapp.presentation.PaymentStepsActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector(modules = PaymentModule.class)
    public abstract PaymentStepsActivity providesPaymentActivity();
}
