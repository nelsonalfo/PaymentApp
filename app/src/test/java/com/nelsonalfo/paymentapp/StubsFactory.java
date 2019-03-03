package com.nelsonalfo.paymentapp;

import com.nelsonalfo.paymentapp.models.CardIssuerModel;
import com.nelsonalfo.paymentapp.models.CuotaModel;
import com.nelsonalfo.paymentapp.models.PaymentMethodModel;

import java.util.Arrays;
import java.util.List;

public class StubsFactory {

    private StubsFactory() {
    }

    public static List<PaymentMethodModel> getPaymentMethodStubs() {
        return Arrays.asList(
                new PaymentMethodModel("visa", "Visa", "active", "http://img.mlstatic.com/org-img/MP3/API/logos/naranja.gif"),
                new PaymentMethodModel("master", "Mastercard", "active", "http://img.mlstatic.com/org-img/MP3/API/logos/master.gif")
        );
    }

    public static List<CardIssuerModel> getCardIssuerStubs() {
        return Arrays.asList(
                new CardIssuerModel("272", "Banco Comafi", "http://img.mlstatic.com/org-img/MP3/API/logos/272.gif"),
                new CardIssuerModel("294", "Banco Hipotecario", "http://img.mlstatic.com/org-img/MP3/API/logos/294.gif")
        );
    }

    public static List<CuotaModel> getCuotaStubs() {
        return Arrays.asList(
                new CuotaModel(1, "1 cuota de $ 4.000,00 ($ 4.000,00)"),
                new CuotaModel(3, "3 cuotas de $ 1.596,27 ($ 4.788,80)")
        );
    }
}
