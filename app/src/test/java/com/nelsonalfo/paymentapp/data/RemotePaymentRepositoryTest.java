package com.nelsonalfo.paymentapp.data;

import com.nelsonalfo.paymentapp.commons.Constants;
import com.nelsonalfo.paymentapp.commons.rxjava.PostExecutionThread;
import com.nelsonalfo.paymentapp.commons.rxjava.ThreadExecutor;
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
import static com.nelsonalfo.paymentapp.StubsFactory.getPaymentMethodStubs;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class RemotePaymentRepositoryTest {
    @Mock
    private PaymentApi api;
    @Mock
    private Consumer<Throwable> consumerError;
    @Mock
    private Consumer<List<PaymentMethodModel>> consumerSuccess;
    @Mock
    private ThreadExecutor backThread;
    @Mock
    private PostExecutionThread uiThread;

    @Captor
    private ArgumentCaptor<List<PaymentMethodModel>> paymentMethodsCaptor;

    private TestScheduler testScheduler;

    private RemotePaymentRepository repository;
    private HttpException mockError;


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

        repository.getPaymentMethods(consumerSuccess, consumerError);
        testScheduler.triggerActions();

        verify(consumerSuccess).accept(eq(paymentMethods));
        verifyZeroInteractions(consumerError);
    }

    @Test
    public void given_apiReturnZeroPaymentMethods_when_getPaymentMethods_then_returnThisPaymentMethods() throws Exception {
        final List<PaymentMethodModel> paymentMethods = new ArrayList<>();
        doReturn(Single.just(paymentMethods)).when(api).getPaymentMethods(eq(Constants.PUBLIC_KEY));

        repository.getPaymentMethods(consumerSuccess, consumerError);
        testScheduler.triggerActions();

        verify(consumerSuccess).accept(eq(paymentMethods));
        verifyZeroInteractions(consumerError);
    }

    @Test
    public void given_apiReturnPaymentMethodsWithDifferentStatus_when_getPaymentMethods_then_returnPaymentMethodsWithActiveStatus() throws Exception {
        final List<PaymentMethodModel> paymentMethods = getPaymentMethodStubs();
        paymentMethods.get(1).setStatus("inactive");
        doReturn(Single.just(paymentMethods)).when(api).getPaymentMethods(eq(Constants.PUBLIC_KEY));

        repository.getPaymentMethods(consumerSuccess, consumerError);
        testScheduler.triggerActions();

        verify(consumerSuccess).accept(paymentMethodsCaptor.capture());
        assertThat(paymentMethodsCaptor.getValue()).hasSize(1);
        assertThat(paymentMethodsCaptor.getValue().get(0).getStatus()).isEqualTo("active");
        verifyZeroInteractions(consumerError);
    }

    @Test
    public void given_apiReturnHttpException_when_getPaymentMethods_then_returnTheException() throws Exception {

        doReturn(Single.error(mockError)).when(api).getPaymentMethods(eq(Constants.PUBLIC_KEY));

        repository.getPaymentMethods(consumerSuccess, consumerError);
        testScheduler.triggerActions();

        verify(consumerError).accept(mockError);
        verifyZeroInteractions(consumerSuccess);
    }

    @Test
    public void getCardIssuers() {
    }
}