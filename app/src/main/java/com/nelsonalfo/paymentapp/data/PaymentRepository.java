package com.nelsonalfo.paymentapp.data;

import com.nelsonalfo.paymentapp.models.CardIssuer;
import com.nelsonalfo.paymentapp.models.Cuota;
import com.nelsonalfo.paymentapp.models.PaymentMethod;

import java.util.List;

import io.reactivex.functions.Consumer;

public interface PaymentRepository {
    void getPaymentMethods(Consumer<List<PaymentMethod>> success, Consumer<Throwable> error);

    void getCardIssuers(String paymentMethodId, Consumer<List<CardIssuer>> success, Consumer<Throwable> error);

    void getCuotas(Params params, Consumer<List<Cuota>> success, Consumer<Throwable> error);

    class Params {
        public long monto;
        public String paymentMethodId;
        public String issuerId;

        public Params(long monto, String paymentMethodId, String cardIssuerId) {
            this.monto = monto;
            this.paymentMethodId = paymentMethodId;
            issuerId = cardIssuerId;
        }
    }
}
