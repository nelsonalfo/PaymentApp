package com.nelsonalfo.paymentapp.data;

import com.nelsonalfo.paymentapp.models.CardIssuerModel;
import com.nelsonalfo.paymentapp.models.CuotaModel;
import com.nelsonalfo.paymentapp.models.PaymentMethodModel;

import java.util.List;

import io.reactivex.functions.Consumer;

public interface PaymentRepository {
    void getPaymentMethods(Consumer<List<PaymentMethodModel>> success, Consumer<Throwable> error);

    void getCardIssuers(String paymentMethodId, Consumer<List<CardIssuerModel>> success, Consumer<Throwable> error);

    void getCuotas(Params params, Consumer<List<CuotaModel>> success, Consumer<Throwable> error);

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
