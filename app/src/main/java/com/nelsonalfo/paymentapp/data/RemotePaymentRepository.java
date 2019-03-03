package com.nelsonalfo.paymentapp.data;

import android.annotation.SuppressLint;

import com.nelsonalfo.paymentapp.commons.Constants;
import com.nelsonalfo.paymentapp.commons.rxjava.PostExecutionThread;
import com.nelsonalfo.paymentapp.commons.rxjava.ThreadExecutor;
import com.nelsonalfo.paymentapp.models.CardIssuerModel;
import com.nelsonalfo.paymentapp.models.CuotaModel;
import com.nelsonalfo.paymentapp.models.PaymentMethodModel;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

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

    @SuppressLint("CheckResult")
    @Override
    public void getPaymentMethods(Consumer<List<PaymentMethodModel>> success, Consumer<Throwable> error) {
        api.getPaymentMethods(Constants.PUBLIC_KEY)
                .subscribeOn(backThread.getScheduler())
                .observeOn(uiThread.getScheduler())
                .toObservable()
                .flatMapIterable(this::getPaymentMethods)
                .filter(this::isActive)
                .toList()
                .subscribe(success, error);
    }

    private List<PaymentMethodModel> getPaymentMethods(List<PaymentMethodModel> paymentMethods) {
        return paymentMethods;
    }

    private boolean isActive(PaymentMethodModel paymentMethodModel) {
        return paymentMethodModel.getStatus().equals("active");
    }

    @Override
    public void getCardIssuers(String paymentMethodId, Consumer<List<CardIssuerModel>> success, Consumer<Throwable> error) {

    }

    @Override
    public void getCuotas(Params params, Consumer<List<CuotaModel>> success, Consumer<Throwable> error) {

    }
}
