package com.nelsonalfo.paymentapp.data;

import android.os.Handler;

import com.nelsonalfo.paymentapp.models.CardIssuerModel;
import com.nelsonalfo.paymentapp.models.PaymentMethodModel;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;

public class RemotePaymentRepository implements PaymentRepository {

    @Inject
    public RemotePaymentRepository() {
    }

    @Override
    public void getPaymentMethods(Consumer<List<PaymentMethodModel>> success, Consumer<Throwable> error) {
        new Handler().postDelayed(() -> {
            try {
                success.accept(Arrays.asList(
                        new PaymentMethodModel("visa", "Visa", "active", "http://img.mlstatic.com/org-img/MP3/API/logos/naranja.gif"),
                        new PaymentMethodModel("master", "Mastercard", "active", "http://img.mlstatic.com/org-img/MP3/API/logos/master.gif")
                ));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 1000);

    }

    @Override
    public void getCardIssuers(String paymentMethodId, Consumer<List<CardIssuerModel>> success, Consumer<Throwable> error) {
        new Handler().postDelayed(() -> {
            try {
                success.accept(Arrays.asList(
                        new CardIssuerModel("272", "Banco Comafi", "http://img.mlstatic.com/org-img/MP3/API/logos/272.gif"),
                        new CardIssuerModel("294", "Banco Hipotecario", "http://img.mlstatic.com/org-img/MP3/API/logos/294.gif")
                ));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 1000);
    }
}
