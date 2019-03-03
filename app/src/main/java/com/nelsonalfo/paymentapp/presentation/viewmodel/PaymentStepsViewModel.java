package com.nelsonalfo.paymentapp.presentation.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.nelsonalfo.paymentapp.data.PaymentRepository;
import com.nelsonalfo.paymentapp.data.PaymentRepository.Params;
import com.nelsonalfo.paymentapp.models.CardIssuer;
import com.nelsonalfo.paymentapp.models.Cuota;
import com.nelsonalfo.paymentapp.models.PaymentMethod;

import java.util.List;

public class PaymentStepsViewModel extends ViewModel {
    private PaymentRepository repository;

    private long amount;
    private PaymentMethod paymentMethod;
    private CardIssuer cardIssuer;

    public final MutableLiveData<List<PaymentMethod>> paymentMethods = new MutableLiveData<>();
    public final MutableLiveData<List<CardIssuer>> cardIssuers = new MutableLiveData<>();
    public final MutableLiveData<List<Cuota>> cuotas = new MutableLiveData<>();

    public final MutableLiveData<Boolean> showLoading = new MutableLiveData<>();
    public final MutableLiveData<Event<Boolean>> showErrorMessage = new MutableLiveData<>();
    public final MutableLiveData<Event<Boolean>> showNoPaymentMethodsMessage = new MutableLiveData<>();
    public final MutableLiveData<Event<Boolean>> showNoCardIssuersMessage = new MutableLiveData<>();
    public final MutableLiveData<Event<Boolean>> showNoCuotasMessage = new MutableLiveData<>();
    public final MutableLiveData<Event<SelectedData>> selectedDataMessage = new MutableLiveData<>();


    public void setRepository(PaymentRepository repository) {
        this.repository = repository;
    }

    public void fetchPaymentMethods(long montoIngresado) {
        amount = montoIngresado;

        showLoading.setValue(true);
        repository.getPaymentMethods(this::showPaymentMethods, error -> showErrorMessage());
    }

    private void showPaymentMethods(List<PaymentMethod> paymentMethods) {
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

    public void fetchCardIssuers(PaymentMethod selectedPaymentMethod) {
        if (selectedPaymentMethod != null && containsId(selectedPaymentMethod.getId())) {
            this.paymentMethod = selectedPaymentMethod;

            showLoading.setValue(true);
            repository.getCardIssuers(selectedPaymentMethod.getId(), this::showCardIssuers, error -> showErrorMessage());
        }
    }

    private boolean containsId(String id) {
        return id != null && !id.isEmpty();
    }

    private void showCardIssuers(List<CardIssuer> cardIssuers) {
        showLoading.setValue(false);

        if (cardIssuers != null && !cardIssuers.isEmpty()) {
            this.cardIssuers.setValue(cardIssuers);
        } else {
            showNoCardIssuersMessage.setValue(new Event<>(true));
        }
    }

    public void fetchCuotas(CardIssuer selectedCardIssuer) {
        if (selectedCardIssuer != null && containsId(selectedCardIssuer.getId())) {
            cardIssuer = selectedCardIssuer;

            showLoading.setValue(true);
            final Params params = new Params(amount, paymentMethod.getId(), cardIssuer.getId());
            repository.getCuotas(params, this::showCuotas, error -> showErrorMessage());
        }
    }

    private void showCuotas(List<Cuota> cuotas) {
        showLoading.setValue(false);

        if (cuotas != null && !cuotas.isEmpty()) {
            this.cuotas.setValue(cuotas);
        } else {
            showNoCuotasMessage.setValue(new Event<>(true));
        }
    }

    public void showSelectedData(Cuota selectedCuota) {
        final SelectedData selectedData = new SelectedData(amount, paymentMethod, cardIssuer, selectedCuota);

        selectedDataMessage.setValue(new Event<>(selectedData));
    }

    long getAmount() {
        return amount;
    }

    PaymentMethod getSelectedPaymentMethod() {
        return paymentMethod;
    }

    CardIssuer getCardIssuer() {
        return cardIssuer;
    }
}
