package com.nelsonalfo.paymentapp.data;

import com.nelsonalfo.paymentapp.commons.Constants;
import com.nelsonalfo.paymentapp.commons.rxjava.PostExecutionThread;
import com.nelsonalfo.paymentapp.commons.rxjava.ThreadExecutor;
import com.nelsonalfo.paymentapp.data.PaymentRepository.Params;
import com.nelsonalfo.paymentapp.models.CardIssuerModel;
import com.nelsonalfo.paymentapp.models.CuotaModel;
import com.nelsonalfo.paymentapp.models.InstallmentModel;
import com.nelsonalfo.paymentapp.models.PaymentMethodModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.TestScheduler;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

import static com.google.common.truth.Truth.assertThat;
import static com.nelsonalfo.paymentapp.StubsFactory.getCardIssuerStubs;
import static com.nelsonalfo.paymentapp.StubsFactory.getInstallmentStubs;
import static com.nelsonalfo.paymentapp.StubsFactory.getPaymentMethodStubs;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class RemotePaymentRepositoryTest {
    @Mock
    private PaymentApi api;
    @Mock
    private Consumer<Throwable> consumerError;
    @Mock
    private Consumer<List<PaymentMethodModel>> paymentMethodsConsumerSuccess;
    @Mock
    private Consumer<List<CardIssuerModel>> cardIssuersConsumerSuccess;
    @Mock
    private Consumer<List<CuotaModel>> cuotasConsumerSuccess;
    @Mock
    private ThreadExecutor backThread;
    @Mock
    private PostExecutionThread uiThread;

    @Captor
    private ArgumentCaptor<List<PaymentMethodModel>> paymentMethodsCaptor;

    private HttpException mockError;
    private TestScheduler testScheduler;

    private RemotePaymentRepository repository;


    @Before
    public void setUp() {
        mockError = new HttpException(Response.error(400,
                ResponseBody.create(MediaType.parse("application/json"), "{\n" +
                        "    \"message\": \"Invalid public key\",\n" +
                        "    \"error\": \"bad_request\",\n" +
                        "    \"status\": 400,\n" +
                        "    \"cause\": []\n" +
                        "}")));

        testScheduler = new TestScheduler();
        doReturn(testScheduler).when(backThread).getScheduler();
        doReturn(testScheduler).when(uiThread).getScheduler();

        repository = new RemotePaymentRepository(backThread, uiThread, api);
    }

    @Test
    public void given_apiReturnPaymentMethods_when_getPaymentMethods_then_returnThisPaymentMethods() throws Exception {
        final List<PaymentMethodModel> paymentMethods = getPaymentMethodStubs();
        doReturn(Single.just(paymentMethods)).when(api).getPaymentMethods(eq(Constants.PUBLIC_KEY));

        repository.getPaymentMethods(paymentMethodsConsumerSuccess, consumerError);
        testScheduler.triggerActions();

        verify(api).getPaymentMethods(eq(Constants.PUBLIC_KEY));
        verify(paymentMethodsConsumerSuccess).accept(eq(paymentMethods));
        verifyZeroInteractions(consumerError);
    }

    @Test
    public void given_apiReturnZeroPaymentMethods_when_getPaymentMethods_then_returnThisPaymentMethods() throws Exception {
        final List<PaymentMethodModel> paymentMethods = new ArrayList<>();
        doReturn(Single.just(paymentMethods)).when(api).getPaymentMethods(eq(Constants.PUBLIC_KEY));

        repository.getPaymentMethods(paymentMethodsConsumerSuccess, consumerError);
        testScheduler.triggerActions();

        verify(api).getPaymentMethods(eq(Constants.PUBLIC_KEY));
        verify(paymentMethodsConsumerSuccess).accept(eq(paymentMethods));
        verifyZeroInteractions(consumerError);
    }

    @Test
    public void given_apiReturnPaymentMethodsWithDifferentStatus_when_getPaymentMethods_then_returnPaymentMethodsWithActiveStatus() throws Exception {
        final List<PaymentMethodModel> paymentMethods = getPaymentMethodStubs();
        paymentMethods.get(1).setStatus("inactive");
        doReturn(Single.just(paymentMethods)).when(api).getPaymentMethods(eq(Constants.PUBLIC_KEY));

        repository.getPaymentMethods(paymentMethodsConsumerSuccess, consumerError);
        testScheduler.triggerActions();

        verify(api).getPaymentMethods(eq(Constants.PUBLIC_KEY));
        verify(paymentMethodsConsumerSuccess).accept(paymentMethodsCaptor.capture());
        assertThat(paymentMethodsCaptor.getValue()).hasSize(1);
        assertThat(paymentMethodsCaptor.getValue().get(0).getStatus()).isEqualTo("active");
        verifyZeroInteractions(consumerError);
    }

    @Test
    public void given_apiReturnHttpException_when_getPaymentMethods_then_returnTheException() throws Exception {
        doReturn(Single.error(mockError)).when(api).getPaymentMethods(eq(Constants.PUBLIC_KEY));

        repository.getPaymentMethods(paymentMethodsConsumerSuccess, consumerError);
        testScheduler.triggerActions();

        verify(api).getPaymentMethods(eq(Constants.PUBLIC_KEY));
        verify(consumerError).accept(mockError);
        verifyZeroInteractions(paymentMethodsConsumerSuccess);
    }

    @Test
    public void given_apiReturnCardIssuers_when_getCardIssuers_then_returnThisCardIssuers() throws Exception {
        final List<CardIssuerModel> cardIssuerModels = getCardIssuerStubs();
        doReturn(Single.just(cardIssuerModels)).when(api).getCardIssuers(eq(Constants.PUBLIC_KEY), anyString());
        final String paymentMethodId = "visa";

        repository.getCardIssuers(paymentMethodId, cardIssuersConsumerSuccess, consumerError);
        testScheduler.triggerActions();

        verify(api).getCardIssuers(eq(Constants.PUBLIC_KEY), eq(paymentMethodId));
        verify(cardIssuersConsumerSuccess).accept(eq(cardIssuerModels));
        verifyNoMoreInteractions(consumerError);
    }

    @Test
    public void given_apiReturnHttpException_when_getCardIssuers_then_returnTheException() throws Exception {
        doReturn(Single.error(mockError)).when(api).getCardIssuers(eq(Constants.PUBLIC_KEY), anyString());
        final String paymentMethodId = "visa";

        repository.getCardIssuers(paymentMethodId, cardIssuersConsumerSuccess, consumerError);
        testScheduler.triggerActions();

        verify(api).getCardIssuers(eq(Constants.PUBLIC_KEY), eq(paymentMethodId));
        verify(consumerError).accept(eq(mockError));
        verifyZeroInteractions(cardIssuersConsumerSuccess);
    }

    @Test
    public void given_apiReturnInstallment_when_getCuotas_then_returnCuotasFromInstallment() throws Exception {
        final Params params = new Params(4000, "visa", "288");
        final List<InstallmentModel> installmentStub = getInstallmentStubs();
        doReturn(Single.just(installmentStub)).when(api).getCuotas(eq(Constants.PUBLIC_KEY), anyLong(), anyString(), anyString());
        final List<CuotaModel> expectedCuotas = installmentStub.get(0).getCuotas();

        repository.getCuotas(params, cuotasConsumerSuccess, consumerError);
        testScheduler.triggerActions();

        verify(api).getCuotas(eq(Constants.PUBLIC_KEY), eq(params.monto), eq(params.paymentMethodId), eq(params.issuerId));
        verify(cuotasConsumerSuccess).accept(eq(expectedCuotas));
        verifyZeroInteractions(consumerError);
    }

    @Test
    public void given_apiReturnHttpException_when_getCuotas_then_returnTheException() throws Exception {
        final Params params = new Params(4000, "visa", "288");
        doReturn(Single.error(mockError)).when(api).getCuotas(eq(Constants.PUBLIC_KEY), anyLong(), anyString(), anyString());

        repository.getCuotas(params, cuotasConsumerSuccess, consumerError);
        testScheduler.triggerActions();

        verify(api).getCuotas(eq(Constants.PUBLIC_KEY), eq(params.monto), eq(params.paymentMethodId), eq(params.issuerId));
        verify(consumerError).accept(eq(mockError));
        verifyZeroInteractions(cuotasConsumerSuccess);
    }
}