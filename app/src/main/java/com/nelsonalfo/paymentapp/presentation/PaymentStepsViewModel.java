package com.nelsonalfo.paymentapp.presentation;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.nelsonalfo.paymentapp.data.PaymentRepository;
import com.nelsonalfo.paymentapp.data.PaymentRepository.Params;
import com.nelsonalfo.paymentapp.models.CardIssuerModel;
import com.nelsonalfo.paymentapp.models.CuotaModel;
import com.nelsonalfo.paymentapp.models.PaymentMethodModel;

import java.util.List;

public class PaymentStepsViewModel extends ViewModel {
    private PaymentRepository repository;

    private long amount;
    private PaymentMethodModel paymentMethod;
    private CardIssuerModel cardIssuer;

    public final MutableLiveData<List<PaymentMethodModel>> paymentMethods = new MutableLiveData<>();
    public final MutableLiveData<List<CardIssuerModel>> cardIssuers = new MutableLiveData<>();
    public final MutableLiveData<List<CuotaModel>> cuotas = new MutableLiveData<>();

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
        if (selectedPaymentMethod != null && containsId(selectedPaymentMethod.getId())) {
            this.paymentMethod = selectedPaymentMethod;

            if (cardIssuers.getValue() == null) {
                showLoading.setValue(true);
                repository.getCardIssuers(selectedPaymentMethod.getId(), this::showCardIssuers, error -> showErrorMessage());
            }
        }
    }

    private boolean containsId(String id) {
        return id != null && !id.isEmpty();
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
        if (selectedCardIssuer != null && containsId(selectedCardIssuer.getId())) {
            cardIssuer = selectedCardIssuer;

            if (cuotas.getValue() == null) {
                showLoading.setValue(true);

                final Params params = new Params(amount, paymentMethod.getId(), cardIssuer.getId());
                repository.getCuotas(params, this::showCuotas, error -> showErrorMessage());
            }
        }

    }

    private void showCuotas(List<CuotaModel> cuotas) {

    }

    public long getAmount() {
        return amount;
    }

    public PaymentMethodModel getSelectedPaymentMethod() {
        return paymentMethod;
    }

    public CardIssuerModel getCardIssuer() {
        return cardIssuer;
    }
}
