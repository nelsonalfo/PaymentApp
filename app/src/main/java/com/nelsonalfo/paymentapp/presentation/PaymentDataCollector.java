package com.nelsonalfo.paymentapp.presentation;

import com.nelsonalfo.paymentapp.models.PaymentMethodModel;

interface PaymentDataCollector {
    void setAmount(long amount);

    long getAmount();

    String getFormattedPaymentMethod();

    String getFormattedCardIssuer();

    String getFormattedCuota();

    void setPaymentMethod(PaymentMethodModel paymentMethod);
}
