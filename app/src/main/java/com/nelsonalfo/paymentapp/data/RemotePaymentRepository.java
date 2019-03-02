package com.nelsonalfo.paymentapp.data;

import com.nelsonalfo.paymentapp.models.CardIssuerModel;
import com.nelsonalfo.paymentapp.models.PaymentMethodModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class RemotePaymentRepository implements PaymentRepository {

    @Inject
    public RemotePaymentRepository() {
    }

    @Override
    public void getPaymentMethods(Consumer<List<PaymentMethodModel>> success, Consumer<Throwable> error) {

    }

    @Override
    public void getCardIssuers(String paymentMethodId, Consumer<List<CardIssuerModel>> success, Consumer<Throwable> error) {

    }
}
