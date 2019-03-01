package com.nelsonalfo.paymentapp;


import com.nelsonalfo.paymentapp.di.DaggerPaymentAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

public class PaymentAppApplication extends DaggerApplication {
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerPaymentAppComponent.builder().create(this);
    }
}
