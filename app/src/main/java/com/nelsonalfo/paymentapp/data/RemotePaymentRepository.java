package com.nelsonalfo.paymentapp.data;

import android.annotation.SuppressLint;

import com.nelsonalfo.paymentapp.commons.Constants;
import com.nelsonalfo.paymentapp.commons.rxjava.PostExecutionThread;
import com.nelsonalfo.paymentapp.commons.rxjava.ThreadExecutor;
import com.nelsonalfo.paymentapp.models.CardIssuerModel;
import com.nelsonalfo.paymentapp.models.CuotaModel;
import com.nelsonalfo.paymentapp.models.InstallmentModel;
import com.nelsonalfo.paymentapp.models.PaymentMethodModel;

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
    public void getPaymentMethods(Consumer<List<PaymentMethodModel>> success, Consumer<Throwable> error) {
        api.getPaymentMethods(Constants.PUBLIC_KEY)
                .subscribeOn(backThread.getScheduler())
                .observeOn(uiThread.getScheduler())
                .toObservable()
                .flatMapIterable(this::getPaymentMethods)
                .filter(this::isActiveCreditCard)
                .toList()
                .subscribe(success, error);
    }

    private List<PaymentMethodModel> getPaymentMethods(List<PaymentMethodModel> paymentMethods) {
        return paymentMethods;
    }

    private boolean isActiveCreditCard(PaymentMethodModel paymentMethodModel) {
        return paymentMethodModel.getStatus().equals("active")
                && paymentMethodModel.getPaymentTypeId().equals("credit_card");
    }

    @Override
    public void getCardIssuers(String paymentMethodId, Consumer<List<CardIssuerModel>> success, Consumer<Throwable> error) {
        api.getCardIssuers(Constants.PUBLIC_KEY, paymentMethodId)
                .subscribeOn(backThread.getScheduler())
                .observeOn(uiThread.getScheduler())
                .subscribe(success, error);
    }

    @Override
    public void getCuotas(Params params, Consumer<List<CuotaModel>> success, Consumer<Throwable> error) {
        api.getCuotas(Constants.PUBLIC_KEY, params.monto, params.paymentMethodId, params.issuerId)
                .map(this::getCuotas)
                .subscribeOn(backThread.getScheduler())
                .observeOn(uiThread.getScheduler())
                .subscribe(success, error);
    }

    private List<CuotaModel> getCuotas(List<InstallmentModel> installments) {
        final InstallmentModel installment = installments.get(0);
        return installment.getCuotas();
    }
}
