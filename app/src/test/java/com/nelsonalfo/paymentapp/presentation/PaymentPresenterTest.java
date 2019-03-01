package com.nelsonalfo.paymentapp.presentation;

import com.nelsonalfo.paymentapp.data.PaymentRepository;
import com.nelsonalfo.paymentapp.models.CardIssuerModel;
import com.nelsonalfo.paymentapp.models.CuotaModel;
import com.nelsonalfo.paymentapp.models.PaymentMethodModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PaymentPresenterTest {

    @Mock
    private PaymentRepository repository;
    @Mock
    private PaymentContract.View view;
    @Mock
    private ResponseBody mockErrorBody;
    @Mock
    private PaymentDataCollector paymentDataCollector;
    @Captor
    private ArgumentCaptor<Consumer<List<PaymentMethodModel>>> consumerPaymentMethodsSuccess;
    @Captor
    private ArgumentCaptor<Consumer<List<CardIssuerModel>>> consumerCardIssuersSuccess;
    @Captor
    private ArgumentCaptor<Consumer<Throwable>> consumerError;

    private PaymentMethodModel selectedPaymentMethod;

    private long montoIngresado;

    private PaymentPresenter presenter;


    @Before
    public void setUp() {
        montoIngresado = 4000;
        selectedPaymentMethod = new PaymentMethodModel("visa", "Visa", "active", "http://img.mlstatic.com/org-img/MP3/API/logos/naranja.gif");

        presenter = new PaymentPresenter(repository);
        presenter.setView(view);
        presenter.setPaymentDataCollector(paymentDataCollector);
    }

    @Test
    public void given_montoIsSet_when_gotToSelectPaymentMethod_then_saveMontoInPaymentCollector() {
        presenter.goToSelectPaymentMethod(montoIngresado);

        verify(paymentDataCollector).setAmount(eq(montoIngresado));
    }

    @Test
    public void given_montoIsSet_when_goToSelectPaymentMethod_then_getPaymentMethodsFromRepository() {
        presenter.goToSelectPaymentMethod(montoIngresado);

        verify(repository).getPaymentMethods(consumerPaymentMethodsSuccess.capture(), consumerError.capture());
    }

    @Test
    public void given_montoIsSet_when_goToSelectPaymentMethod_then_showLoading() {
        presenter.goToSelectPaymentMethod(montoIngresado);

        verify(view).showLoading();
    }

    @Test
    public void given_repositoryReturnPaymentMethods_when_goToSelectPaymentMethod_then_hideLoading() throws Exception {
        final List<PaymentMethodModel> paymentMethods = getPaymentMethodStubs();

        presenter.goToSelectPaymentMethod(montoIngresado);

        verify(repository).getPaymentMethods(consumerPaymentMethodsSuccess.capture(), consumerError.capture());
        consumerPaymentMethodsSuccess.getValue().accept(paymentMethods);
        verify(view).hideLoading();
    }

    @Test
    public void given_repositoryReturnPaymentMethods_when_goToSelectPaymentMethod_then_showPaymentMethods() throws Exception {
        final List<PaymentMethodModel> paymentMethods = getPaymentMethodStubs();

        presenter.goToSelectPaymentMethod(montoIngresado);

        verify(repository).getPaymentMethods(consumerPaymentMethodsSuccess.capture(), consumerError.capture());
        consumerPaymentMethodsSuccess.getValue().accept(paymentMethods);
        verify(view).showPaymentMethods(eq(paymentMethods));
    }

    @Test
    public void given_repositoryReturnError_when_goToSelectPaymentMethod_then_showErrorAndRetryMessage() throws Exception {
        final HttpException error = new HttpException(Response.error(404, mockErrorBody));

        presenter.goToSelectPaymentMethod(montoIngresado);

        verify(repository).getPaymentMethods(consumerPaymentMethodsSuccess.capture(), consumerError.capture());
        consumerError.getValue().accept(error);
        verify(view).showPaymentMethodsErrorAndRetryMessage();
    }

    @Test
    public void given_repositoryReturnError_when_goToSelectPaymentMethod_then_hideLoading() throws Exception {
        final HttpException error = new HttpException(Response.error(404, mockErrorBody));

        presenter.goToSelectPaymentMethod(montoIngresado);

        verify(repository).getPaymentMethods(consumerPaymentMethodsSuccess.capture(), consumerError.capture());
        consumerError.getValue().accept(error);
        verify(view).hideLoading();
    }

    @Test
    public void given_paymentMethodIsSet_when_goToSelectCardIssuers_then_saveMontoInPaymentCollector() {
        presenter.goToSelectCardIssuers(selectedPaymentMethod);

        verify(paymentDataCollector).setPaymentMethod(eq(selectedPaymentMethod));
    }

    @Test
    public void given_paymentMethodIsSet_when_goToSelectCardIssuers_then_showLoading() {
        presenter.goToSelectCardIssuers(selectedPaymentMethod);

        verify(view).showLoading();
    }

    @Test
    public void given_paymentMethodIsSet_when_goToSelectCardIssuers_then_getCardIssuersFromRepository() {
        presenter.goToSelectCardIssuers(selectedPaymentMethod);

        verify(repository).getCardIssuers(eq(selectedPaymentMethod.getId()), consumerCardIssuersSuccess.capture(), consumerError.capture());
    }

    @Test
    public void given_repositoryReturnCardIssuers_when_goToSelectCardIssuers_then_hideLoading() throws Exception {
        final List<CardIssuerModel> cardIssuers = getCardIssuerStubs();

        presenter.goToSelectCardIssuers(selectedPaymentMethod);

        verify(repository).getCardIssuers(eq(selectedPaymentMethod.getId()), consumerCardIssuersSuccess.capture(), consumerError.capture());
        consumerCardIssuersSuccess.getValue().accept(cardIssuers);
        verify(view).hideLoading();
    }

    @Test
    public void given_repositoryReturnCardIssuers_when_goToSelectCardIssuers_then_showCardIssuers() throws Exception {
        final List<CardIssuerModel> cardIssuers = getCardIssuerStubs();

        presenter.goToSelectCardIssuers(selectedPaymentMethod);

        verify(repository).getCardIssuers(eq(selectedPaymentMethod.getId()), consumerCardIssuersSuccess.capture(), consumerError.capture());
        consumerCardIssuersSuccess.getValue().accept(cardIssuers);
        verify(view).showCardIssuers(eq(cardIssuers));
    }

    @Test
    public void given_repositoryReturnError_when_goToSelectCardIssuers_then_hideLoading() throws Exception {
        final HttpException error = new HttpException(Response.error(404, mockErrorBody));

        presenter.goToSelectCardIssuers(selectedPaymentMethod);

        verify(repository).getCardIssuers(eq(selectedPaymentMethod.getId()), consumerCardIssuersSuccess.capture(), consumerError.capture());
        consumerError.getValue().accept(error);
        verify(view).hideLoading();
    }

    @Test
    public void given_repositoryReturnError_when_goToSelectCardIssuers_then_showErrorAndRetryMessage() throws Exception {
        final HttpException error = new HttpException(Response.error(404, mockErrorBody));

        presenter.goToSelectCardIssuers(selectedPaymentMethod);

        verify(repository).getCardIssuers(eq(selectedPaymentMethod.getId()), consumerCardIssuersSuccess.capture(), consumerError.capture());
        consumerError.getValue().accept(error);
        verify(view).showCardIssuersErrorAndRetryMessage();
    }

    @Test
    public void given_selectedCuotaIsSet_when_showPaymentData_then_showDataInFirstView() {
        doReturn(4000L).when(paymentDataCollector).getAmount();
        doReturn("Mastercard").when(paymentDataCollector).getPaymentMethod();
        doReturn("Banco Comafi").when(paymentDataCollector).getCardIssuer();
        doReturn("3 cuotas de $ 1.596,27 ($ 4.788,80)").when(paymentDataCollector).getCuotas();

        final CuotaModel selectedCuota = new CuotaModel(3, "3 cuotas de $ 1.596,27 ($ 4.788,80)");


        presenter.showPaymentData(selectedCuota);

        verify(view).showDataInFirstView(
                paymentDataCollector.getAmount(),
                paymentDataCollector.getPaymentMethod(),
                paymentDataCollector.getCardIssuer(),
                paymentDataCollector.getCuotas()
        );
    }

    //TODO que pasa cuando no viene ningun card issuer

    //TODO que pasa cuando no viene ningun payment method

    private List<PaymentMethodModel> getPaymentMethodStubs() {
        return Arrays.asList(
                new PaymentMethodModel("visa", "Visa", "active", "http://img.mlstatic.com/org-img/MP3/API/logos/naranja.gif"),
                new PaymentMethodModel("master", "Mastercard", "active", "http://img.mlstatic.com/org-img/MP3/API/logos/master.gif")
        );
    }

    private List<CardIssuerModel> getCardIssuerStubs() {
        return Arrays.asList(
                new CardIssuerModel("272", "Banco Comafi", "http://img.mlstatic.com/org-img/MP3/API/logos/272.gif"),
                new CardIssuerModel("294", "Banco Hipotecario", "http://img.mlstatic.com/org-img/MP3/API/logos/294.gif")
        );
    }

}