package com.nelsonalfo.paymentapp.presentation;

import com.nelsonalfo.paymentapp.models.CardIssuerModel;
import com.nelsonalfo.paymentapp.models.CuotaModel;
import com.nelsonalfo.paymentapp.models.PaymentMethodModel;

public class SelectedData {
    public final long amount;
    public final PaymentMethodModel paymentMethod;
    public final CardIssuerModel cardIssuer;
    public final CuotaModel cuota;

    public SelectedData(long amount, PaymentMethodModel paymentMethod, CardIssuerModel cardIssuer, CuotaModel cuota) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.cardIssuer = cardIssuer;
        this.cuota = cuota;
    }
}
