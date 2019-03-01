package com.nelsonalfo.paymentapp.presentation;

import com.nelsonalfo.paymentapp.data.PaymentRepository;
import com.nelsonalfo.paymentapp.models.CardIssuerModel;
import com.nelsonalfo.paymentapp.models.CuotaModel;
import com.nelsonalfo.paymentapp.models.PaymentMethodModel;

import java.util.List;

import javax.inject.Inject;

public class PaymentPresenter implements PaymentContract.Presenter {

    private PaymentContract.View view;
    private PaymentRepository repository;
    private PaymentDataCollector paymentDataCollector;


    @Inject
    public PaymentPresenter(PaymentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void setView(PaymentContract.View view) {
        this.view = view;
    }

    @Override
    public void setPaymentDataCollector(PaymentDataCollector paymentDataCollector) {
        this.paymentDataCollector = paymentDataCollector;
    }

    @Override
    public void goToSelectPaymentMethod(long monto) {
        paymentDataCollector.setAmount(monto);

        view.showLoading();
        repository.getPaymentMethods(this::showPaymentMethods, error -> showPaymentMethodsErrorMessage());
    }

    private void showPaymentMethods(List<PaymentMethodModel> paymentMethods) {
        view.hideLoading();
        view.showPaymentMethods(paymentMethods);
    }

    private void showPaymentMethodsErrorMessage() {
        view.hideLoading();
        view.showPaymentMethodsErrorAndRetryMessage();
    }

    @Override
    public void goToSelectCardIssuers(PaymentMethodModel paymentMethod) {
        paymentDataCollector.setPaymentMethod(paymentMethod);
        view.showLoading();

        repository.getCardIssuers(paymentMethod.getId(), this::showCardIssuers, error -> showCardIssuersErrorMessage());
    }

    private void showCardIssuers(List<CardIssuerModel> cardIssuers) {
        view.hideLoading();
        view.showCardIssuers(cardIssuers);
    }

    private void showCardIssuersErrorMessage() {
        view.hideLoading();
        view.showCardIssuersErrorAndRetryMessage();
    }

    @Override
    public void showPaymentData(CuotaModel selectedCuota) {
        view.showDataInFirstView(
                paymentDataCollector.getAmount(),
                paymentDataCollector.getPaymentMethod(),
                paymentDataCollector.getCardIssuer(),
                paymentDataCollector.getCuotas()
        );
    }
}
