package com.nelsonalfo.paymentapp.di.module;

import com.nelsonalfo.paymentapp.data.PaymentRepository;
import com.nelsonalfo.paymentapp.data.RemotePaymentRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class PaymentModule {

    @Provides
    public PaymentRepository providesPaymentRepository(RemotePaymentRepository repository){
        return repository;
    }
}
