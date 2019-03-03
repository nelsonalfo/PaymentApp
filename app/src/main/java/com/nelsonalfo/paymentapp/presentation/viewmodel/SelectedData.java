package com.nelsonalfo.paymentapp.presentation.viewmodel;

import com.nelsonalfo.paymentapp.models.CardIssuer;
import com.nelsonalfo.paymentapp.models.Cuota;
import com.nelsonalfo.paymentapp.models.PaymentMethod;

public class SelectedData {
    public final long amount;
    public final PaymentMethod paymentMethod;
    public final CardIssuer cardIssuer;
    public final Cuota cuota;

    public SelectedData(long amount, PaymentMethod paymentMethod, CardIssuer cardIssuer, Cuota cuota) {
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.cardIssuer = cardIssuer;
        this.cuota = cuota;
    }
}
