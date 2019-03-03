package com.nelsonalfo.paymentapp.data;

import com.nelsonalfo.paymentapp.models.CardIssuerModel;
import com.nelsonalfo.paymentapp.models.InstallmentModel;
import com.nelsonalfo.paymentapp.models.PaymentMethodModel;

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
    Single<List<PaymentMethodModel>> getPaymentMethods(@Query("public_key") String publicKey);

    @GET(VERSION + PAYMENT_METHODS + CARD_ISSUERS)
    Single<List<CardIssuerModel>> getBanksList(@Query("public_key") String publicKey,
                                                    @Query("payment_method_id") String paymentMethodId);

    @GET(VERSION + PAYMENT_METHODS + INSTALLMENTS)
    Single<List<InstallmentModel>> getInstallments(@Query("public_key") String publicKey,
                                                        @Query("amount") Long amount,
                                                        @Query("payment_method_id") String paymentMethodId,
                                                        @Query("issuer.id") String issuerId);
}
