package com.nelsonalfo.paymentapp.presentation;

import com.nelsonalfo.paymentapp.models.CardIssuerModel;
import com.nelsonalfo.paymentapp.models.CuotaModel;
import com.nelsonalfo.paymentapp.models.PaymentMethodModel;

import java.util.List;

public interface PaymentContract {
    interface View {
        void showLoading();

        void hideLoading();

        void showPaymentMethods(List<PaymentMethodModel> paymentMethods);

        void showPaymentMethodsErrorAndRetryMessage();

        void showDataInFirstView(long amount, String paymentMethod, String cardIssuer, String cuotas);

        void showCardIssuers(List<CardIssuerModel> cardIssuers);

        void showCardIssuersErrorAndRetryMessage();
    }

    interface Presenter {
        void setView(View view);

        void goToSelectPaymentMethod(long monto);

        void goToSelectCardIssuers(PaymentMethodModel paymentMethod);

        void showPaymentData(CuotaModel selectedCuota);

        void setPaymentDataCollector(PaymentDataCollector paymentDataCollector);
    }
}
