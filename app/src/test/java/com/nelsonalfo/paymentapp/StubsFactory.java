package com.nelsonalfo.paymentapp;

import com.nelsonalfo.paymentapp.models.CardIssuer;
import com.nelsonalfo.paymentapp.models.Cuota;
import com.nelsonalfo.paymentapp.models.Installment;
import com.nelsonalfo.paymentapp.models.PaymentMethod;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StubsFactory {

    private StubsFactory() {
    }

    public static List<PaymentMethod> getPaymentMethodStubs() {
        return Arrays.asList(
                new PaymentMethod("visa", "Visa", "credit_card", "active", "http://img.mlstatic.com/org-img/MP3/API/logos/naranja.gif"),
                new PaymentMethod("master", "Mastercard", "credit_card", "active", "http://img.mlstatic.com/org-img/MP3/API/logos/master.gif")
        );
    }

    public static List<CardIssuer> getCardIssuerStubs() {
        return Arrays.asList(
                new CardIssuer("272", "Banco Comafi", "http://img.mlstatic.com/org-img/MP3/API/logos/272.gif"),
                new CardIssuer("294", "Banco Hipotecario", "http://img.mlstatic.com/org-img/MP3/API/logos/294.gif")
        );
    }

    public static List<Cuota> getCuotaStubs() {
        return Arrays.asList(
                new Cuota(1, "1 cuota de $ 4.000,00 ($ 4.000,00)"),
                new Cuota(3, "3 cuotas de $ 1.596,27 ($ 4.788,80)")
        );
    }

    public static List<Installment> getInstallmentStubs() {
        return Collections.singletonList(new Installment(getCuotaStubs()));
    }
}
