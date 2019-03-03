package com.nelsonalfo.paymentapp.data;

import com.nelsonalfo.paymentapp.models.CardIssuer;
import com.nelsonalfo.paymentapp.models.Installment;
import com.nelsonalfo.paymentapp.models.PaymentMethod;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.nelsonalfo.paymentapp.commons.Constants.CARD_ISSUERS;
import static com.nelsonalfo.paymentapp.commons.Constants.INSTALLMENTS;
import static com.nelsonalfo.paymentapp.commons.Constants.PAYMENT_METHODS;
import static com.nelsonalfo.paymentapp.commons.Constants.VERSION;

public interface PaymentApi {
    @GET(VERSION + PAYMENT_METHODS)
    Single<List<PaymentMethod>> getPaymentMethods(@Query("public_key") String publicKey);

    @GET(VERSION + PAYMENT_METHODS + CARD_ISSUERS)
    Single<List<CardIssuer>> getCardIssuers(@Query("public_key") String publicKey,
                                            @Query("payment_method_id") String paymentMethodId);

    @GET(VERSION + PAYMENT_METHODS + INSTALLMENTS)
    Single<List<Installment>> getCuotas(@Query("public_key") String publicKey,
                                        @Query("amount") Long amount,
                                        @Query("payment_method_id") String paymentMethodId,
                                        @Query("issuer.id") String issuerId);
}
