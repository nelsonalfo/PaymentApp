package com.nelsonalfo.paymentapp.presentation;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.nelsonalfo.paymentapp.data.PaymentRepository;
import com.nelsonalfo.paymentapp.models.CardIssuerModel;
import com.nelsonalfo.paymentapp.models.PaymentMethodModel;

import java.util.List;

public class PaymentStepsViewModel extends ViewModel {
    private PaymentRepository repository;

    private long amount;
    private PaymentMethodModel paymentMethod;

    public final MutableLiveData<List<PaymentMethodModel>> paymentMethods = new MutableLiveData<>();
    public final MutableLiveData<List<CardIssuerModel>> cardIssuers = new MutableLiveData<>();

    public final MutableLiveData<Boolean> showLoading = new MutableLiveData<>();
    public final MutableLiveData<Event<Boolean>> showErrorMessage = new MutableLiveData<>();
    public final MutableLiveData<Event<Boolean>> showNoPaymentMethodsMessage = new MutableLiveData<>();
    public final MutableLiveData<Event<Boolean>> showNoCardIssuersMessage = new MutableLiveData<>();


    void setRepository(PaymentRepository repository) {
        this.repository = repository;
    }

    public void fetchPaymentMethods(long montoIngresado) {
        amount = montoIngresado;

        if (paymentMethods.getValue() == null) {
            showLoading.setValue(true);
            repository.getPaymentMethods(this::showPaymentMethods, error -> showErrorMessage());
        }
    }

    private void showPaymentMethods(List<PaymentMethodModel> paymentMethods) {
        showLoading.setValue(false);

        if (paymentMethods != null && !paymentMethods.isEmpty()) {
            this.paymentMethods.setValue(paymentMethods);
        } else {
            showNoPaymentMethodsMessage.setValue(new Event<>(true));
        }
    }

    private void showErrorMessage() {
        showLoading.setValue(false);
        showErrorMessage.setValue(new Event<>(true));
    }

    public void fetchCardIssuers(PaymentMethodModel selectedPaymentMethod) {
        if (selectedPaymentMethod != null && containsId(selectedPaymentMethod)) {
            this.paymentMethod = selectedPaymentMethod;

            if (cardIssuers.getValue() == null) {
                showLoading.setValue(true);
                repository.getCardIssuers(selectedPaymentMethod.getId(), this::showCardIssuers, error -> showErrorMessage());
            }

        }
    }

    private boolean containsId(PaymentMethodModel selectedPaymentMethod) {
        return selectedPaymentMethod.getId() != null && !selectedPaymentMethod.getId().isEmpty();
    }

    private void showCardIssuers(List<CardIssuerModel> cardIssuers) {
        showLoading.setValue(false);

        if (cardIssuers != null && !cardIssuers.isEmpty()) {
            this.cardIssuers.setValue(cardIssuers);
        } else {
            showNoCardIssuersMessage.setValue(new Event<>(true));
        }
    }


    public void fetchInstallments(CardIssuerModel selectedCardIssuer) {

    }

    public long getAmount() {
        return amount;
    }

    public PaymentMethodModel getSelectedPaymentMethod() {
        return paymentMethod;
    }
}
