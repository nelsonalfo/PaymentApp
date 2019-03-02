package com.nelsonalfo.paymentapp.presentation;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.nelsonalfo.paymentapp.data.PaymentRepository;
import com.nelsonalfo.paymentapp.models.CardIssuerModel;
import com.nelsonalfo.paymentapp.models.PaymentMethodModel;

import java.util.List;


public class PaymentViewModel extends ViewModel {
    private PaymentRepository repository;

    private long amount;
    private PaymentMethodModel paymentMethod;

    private MutableLiveData<List<PaymentMethodModel>> paymentMethods = new MutableLiveData<>();
    private MutableLiveData<List<CardIssuerModel>> cardIssuers = new MutableLiveData<>();

    private MutableLiveData<Boolean> showLoading = new MutableLiveData<>();
    private MutableLiveData<Event<Boolean>> showErrorMessage = new MutableLiveData<>();
    private MutableLiveData<Event<Boolean>> showNoPaymentMethodsMessage = new MutableLiveData<>();
    private MutableLiveData<Event<Boolean>> showNoCardIssuersMessage = new MutableLiveData<>();


    public void setRepository(PaymentRepository repository) {
        this.repository = repository;
    }

    public void goToSelectPaymentMethod(long montoIngresado) {
        amount = montoIngresado;

        showLoading.setValue(true);
        repository.getPaymentMethods(this::showPaymentMethods, error -> showErrorMessage());
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

    public void goToSelectCardIssuers(PaymentMethodModel selectedPaymentMethod) {
        if (selectedPaymentMethod != null && containsId(selectedPaymentMethod)) {
            this.paymentMethod = selectedPaymentMethod;

            showLoading.setValue(true);
            repository.getCardIssuers(selectedPaymentMethod.getId(), this::showCardIssuers, error -> showErrorMessage());
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

    public long getAmount() {
        return amount;
    }

    public PaymentMethodModel getSelectedPaymentMethod() {
        return paymentMethod;
    }

    public LiveData<List<PaymentMethodModel>> getPaymentMethods() {
        return paymentMethods;
    }

    public LiveData<Boolean> getShowLoading() {
        return showLoading;
    }

    public LiveData<Event<Boolean>> getShowErrorMessage() {
        return showErrorMessage;
    }

    public LiveData<Event<Boolean>> getShowNoPaymentMethodsMessage() {
        return showNoPaymentMethodsMessage;
    }

    public LiveData<List<CardIssuerModel>> getCardIssuers() {
        return cardIssuers;
    }

    public LiveData<Event<Boolean>> getShowNoCardIssuersMessage() {
        return showNoCardIssuersMessage;
    }
}
