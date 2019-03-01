package com.nelsonalfo.paymentapp.data;

import com.nelsonalfo.paymentapp.models.CardIssuerModel;
import com.nelsonalfo.paymentapp.models.PaymentMethodModel;

import java.util.List;

import io.reactivex.functions.Consumer;

public interface PaymentRepository {
    void getPaymentMethods(Consumer<List<PaymentMethodModel>> success, Consumer<Throwable> error);

    void getCardIssuers(String paymentMethodId, Consumer<List<CardIssuerModel>> success, Consumer<Throwable> error);
}
