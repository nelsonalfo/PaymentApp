package com.nelsonalfo.paymentapp.data;

import android.annotation.SuppressLint;

import com.nelsonalfo.paymentapp.commons.Constants;
import com.nelsonalfo.paymentapp.commons.rxjava.PostExecutionThread;
import com.nelsonalfo.paymentapp.commons.rxjava.ThreadExecutor;
import com.nelsonalfo.paymentapp.models.CardIssuer;
import com.nelsonalfo.paymentapp.models.Cuota;
import com.nelsonalfo.paymentapp.models.Installment;
import com.nelsonalfo.paymentapp.models.PaymentMethod;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

@SuppressLint("CheckResult")
public class RemotePaymentRepository implements PaymentRepository {

    private ThreadExecutor backThread;
    private PostExecutionThread uiThread;
    private PaymentApi api;

    @Inject
    public RemotePaymentRepository(ThreadExecutor backThread, PostExecutionThread uiThread, PaymentApi api) {
        this.backThread = backThread;
        this.uiThread = uiThread;
        this.api = api;
    }


    @Override
    public void getPaymentMethods(Consumer<List<PaymentMethod>> success, Consumer<Throwable> error) {
        api.getPaymentMethods(Constants.PUBLIC_KEY)
                .subscribeOn(backThread.getScheduler())
                .observeOn(uiThread.getScheduler())
                .toObservable()
                .flatMapIterable(this::getPaymentMethods)
                .filter(this::isActiveCreditCard)
                .toList()
                .subscribe(success, error);
    }

    private List<PaymentMethod> getPaymentMethods(List<PaymentMethod> paymentMethods) {
        return paymentMethods;
    }

    private boolean isActiveCreditCard(PaymentMethod paymentMethodModel) {
        return paymentMethodModel.getStatus().equals("active")
                && paymentMethodModel.getPaymentTypeId().equals("credit_card");
    }

    @Override
    public void getCardIssuers(String paymentMethodId, Consumer<List<CardIssuer>> success, Consumer<Throwable> error) {
        api.getCardIssuers(Constants.PUBLIC_KEY, paymentMethodId)
                .subscribeOn(backThread.getScheduler())
                .observeOn(uiThread.getScheduler())
                .subscribe(success, error);
    }

    @Override
    public void getCuotas(Params params, Consumer<List<Cuota>> success, Consumer<Throwable> error) {
        api.getCuotas(Constants.PUBLIC_KEY, params.monto, params.paymentMethodId, params.issuerId)
                .map(this::getCuotas)
                .subscribeOn(backThread.getScheduler())
                .observeOn(uiThread.getScheduler())
                .subscribe(success, error);
    }

    private List<Cuota> getCuotas(List<Installment> installments) {
        final Installment installment = installments.get(0);
        return installment.getCuotas();
    }
}
