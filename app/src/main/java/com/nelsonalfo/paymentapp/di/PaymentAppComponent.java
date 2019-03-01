package com.nelsonalfo.paymentapp.di;

import com.nelsonalfo.paymentapp.PaymentAppApplication;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(
        modules = {
                AndroidInjectionModule.class,
                AndroidSupportInjectionModule.class,
                NetworkModule.class,
                ThreadModule.class,
                ActivityModule.class
        }
)
public interface PaymentAppComponent extends AndroidInjector<PaymentAppApplication> {
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<PaymentAppApplication> {

    }
}
