package com.nelsonalfo.paymentapp.presentation;

import com.nelsonalfo.paymentapp.models.PaymentMethodModel;

interface PaymentDataCollector {
    void setAmount(long amount);

    long getAmount();

    String getPaymentMethod();

    String getCardIssuer();

    String getCuotas();

    void setPaymentMethod(PaymentMethodModel paymentMethod);
}
