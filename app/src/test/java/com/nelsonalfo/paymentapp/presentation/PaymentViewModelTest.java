package com.nelsonalfo.paymentapp.presentation;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;

import com.nelsonalfo.paymentapp.data.PaymentRepository;
import com.nelsonalfo.paymentapp.models.CardIssuerModel;
import com.nelsonalfo.paymentapp.models.PaymentMethodModel;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class PaymentViewModelTest {
    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Mock
    private Observer<Boolean> showLoadingObserver;
    @Mock
    private Observer<Event<Boolean>> eventObserver;
    @Mock
    private Observer<List<PaymentMethodModel>> paymentMethodsObserver;
    @Mock
    private Observer<List<CardIssuerModel>> cardIssuersObserver;
    @Mock
    private PaymentRepository repository;
    @Mock
    private ResponseBody mockErrorBody;

    @Captor
    private ArgumentCaptor<Consumer<List<PaymentMethodModel>>> consumerPaymentMethodsSuccess;
    @Captor
    private ArgumentCaptor<Consumer<List<CardIssuerModel>>> consumerCardIssuersSuccess;
    @Captor
    private ArgumentCaptor<Consumer<Throwable>> consumerError;
    @Captor
    private ArgumentCaptor<Event<Boolean>> eventCaptor;

    private PaymentMethodModel selectedPaymentMethod;
    private long montoIngresado;

    private PaymentViewModel viewModel;


    @Before
    public void setUp() {
        montoIngresado = 4000;
        selectedPaymentMethod = new PaymentMethodModel("visa", "Visa", "active", "http://img.mlstatic.com/org-img/MP3/API/logos/naranja.gif");

        viewModel = new PaymentViewModel();
        viewModel.setRepository(repository);
    }

    @Test
    public void given_montoIsSet_when_gotToSelectPaymentMethod_then_saveMontoInPaymentCollector() {
        viewModel.goToSelectPaymentMethod(montoIngresado);

        assertThat(viewModel.getAmount()).isEqualTo(montoIngresado);
    }


    @Test
    public void given_montoIsSet_when_goToSelectPaymentMethod_then_getPaymentMethodsFromRepository() {
        viewModel.goToSelectPaymentMethod(montoIngresado);

        verify(repository).getPaymentMethods(consumerPaymentMethodsSuccess.capture(), consumerError.capture());
    }

    @Test
    public void given_montoIsSet_when_goToSelectPaymentMethod_then_showLoading() {
        viewModel.getShowLoading().observeForever(showLoadingObserver);

        viewModel.goToSelectPaymentMethod(montoIngresado);

        assertThat(viewModel.getShowLoading().getValue()).isTrue();
        verify(showLoadingObserver).onChanged(eq(true));
    }


    @Test
    public void given_repositoryReturnPaymentMethods_when_goToSelectPaymentMethod_then_hideLoading() throws Exception {
        final List<PaymentMethodModel> paymentMethods = getPaymentMethodStubs();
        viewModel.getShowLoading().observeForever(showLoadingObserver);

        viewModel.goToSelectPaymentMethod(montoIngresado);

        verify(repository).getPaymentMethods(consumerPaymentMethodsSuccess.capture(), consumerError.capture());
        consumerPaymentMethodsSuccess.getValue().accept(paymentMethods);
        assertThat(viewModel.getShowLoading().getValue()).isFalse();
        verify(showLoadingObserver).onChanged(eq(false));
    }


    @Test
    public void given_repositoryReturnPaymentMethods_when_goToSelectPaymentMethod_then_showPaymentMethods() throws Exception {
        final List<PaymentMethodModel> paymentMethods = getPaymentMethodStubs();
        viewModel.getPaymentMethods().observeForever(paymentMethodsObserver);

        viewModel.goToSelectPaymentMethod(montoIngresado);

        verify(repository).getPaymentMethods(consumerPaymentMethodsSuccess.capture(), consumerError.capture());
        consumerPaymentMethodsSuccess.getValue().accept(paymentMethods);
        assertThat(viewModel.getPaymentMethods().getValue()).isEqualTo(paymentMethods);
        verify(paymentMethodsObserver).onChanged(eq(paymentMethods));
    }


    @Test
    public void given_repositoryReturnError_when_goToSelectPaymentMethod_then_showErrorAndRetryMessage() throws Exception {
        final HttpException error = new HttpException(Response.error(404, mockErrorBody));
        viewModel.getShowErrorMessage().observeForever(eventObserver);

        viewModel.goToSelectPaymentMethod(montoIngresado);

        verify(repository).getPaymentMethods(consumerPaymentMethodsSuccess.capture(), consumerError.capture());
        consumerError.getValue().accept(error);
        assertThat(viewModel.getShowErrorMessage().getValue()).isInstanceOf(Event.class);
        verify(eventObserver).onChanged(eventCaptor.capture());
    }


    @Test
    public void given_repositoryReturnError_when_goToSelectPaymentMethod_then_hideLoading() throws Exception {
        viewModel.getShowLoading().observeForever(showLoadingObserver);
        final HttpException error = new HttpException(Response.error(404, mockErrorBody));

        viewModel.goToSelectPaymentMethod(montoIngresado);

        verify(repository).getPaymentMethods(consumerPaymentMethodsSuccess.capture(), consumerError.capture());
        consumerError.getValue().accept(error);
        assertThat(viewModel.getShowLoading().getValue()).isFalse();
        verify(showLoadingObserver).onChanged(false);
    }

    @Test
    public void given_repositoryReturnZeroPaymentMethods_when_goToSelectPaymentMethod_then_showNoPaymentMethodsMessage() throws Exception {
        final List<PaymentMethodModel> paymentMethods = new ArrayList<>();
        viewModel.getShowNoPaymentMethodsMessage().observeForever(eventObserver);

        viewModel.goToSelectPaymentMethod(montoIngresado);

        verify(repository).getPaymentMethods(consumerPaymentMethodsSuccess.capture(), consumerError.capture());
        consumerPaymentMethodsSuccess.getValue().accept(paymentMethods);
        assertThat(viewModel.getShowNoPaymentMethodsMessage().getValue()).isInstanceOf(Event.class);
        verify(eventObserver).onChanged(eventCaptor.capture());
    }


    @Test
    public void given_repositoryReturnNull_when_goToSelectPaymentMethod_then_showNoPaymentMethodsMessage() throws Exception {
        final ArrayList<PaymentMethodModel> paymentMethods = null;
        viewModel.getShowNoPaymentMethodsMessage().observeForever(eventObserver);

        viewModel.goToSelectPaymentMethod(montoIngresado);

        verify(repository).getPaymentMethods(consumerPaymentMethodsSuccess.capture(), consumerError.capture());
        consumerPaymentMethodsSuccess.getValue().accept(paymentMethods);
        assertThat(viewModel.getShowNoPaymentMethodsMessage().getValue()).isInstanceOf(Event.class);
        verify(eventObserver).onChanged(eventCaptor.capture());
    }


    @Test
    public void given_paymentMethodIsSet_when_goToSelectCardIssuers_then_saveMontoInPaymentCollector() {
        viewModel.goToSelectCardIssuers(selectedPaymentMethod);

        assertThat(viewModel.getSelectedPaymentMethod()).isEqualTo(selectedPaymentMethod);
    }


    @Test
    public void given_paymentMethodIsSet_when_goToSelectCardIssuers_then_showLoading() {
        viewModel.getShowLoading().observeForever(showLoadingObserver);

        viewModel.goToSelectCardIssuers(selectedPaymentMethod);

        assertThat(viewModel.getShowLoading().getValue()).isTrue();
        verify(showLoadingObserver).onChanged(eq(true));
    }


    @Test
    public void given_paymentMethodIsSet_when_goToSelectCardIssuers_then_getCardIssuersFromRepository() {
        viewModel.goToSelectCardIssuers(selectedPaymentMethod);

        verify(repository).getCardIssuers(eq(selectedPaymentMethod.getId()), consumerCardIssuersSuccess.capture(), consumerError.capture());
    }


    @Test
    public void given_repositoryReturnCardIssuers_when_goToSelectCardIssuers_then_hideLoading() throws Exception {
        final List<CardIssuerModel> cardIssuers = getCardIssuerStubs();
        viewModel.getShowLoading().observeForever(showLoadingObserver);

        viewModel.goToSelectCardIssuers(selectedPaymentMethod);

        verify(repository).getCardIssuers(eq(selectedPaymentMethod.getId()), consumerCardIssuersSuccess.capture(), consumerError.capture());
        consumerCardIssuersSuccess.getValue().accept(cardIssuers);
        assertThat(viewModel.getShowLoading().getValue()).isFalse();
        verify(showLoadingObserver).onChanged(eq(false));
    }


    @Test
    public void given_repositoryReturnCardIssuers_when_goToSelectCardIssuers_then_showCardIssuers() throws Exception {
        final List<CardIssuerModel> cardIssuers = getCardIssuerStubs();
        viewModel.getCardIssuers().observeForever(cardIssuersObserver);

        viewModel.goToSelectCardIssuers(selectedPaymentMethod);

        verify(repository).getCardIssuers(eq(selectedPaymentMethod.getId()), consumerCardIssuersSuccess.capture(), consumerError.capture());
        consumerCardIssuersSuccess.getValue().accept(cardIssuers);
        assertThat(viewModel.getCardIssuers().getValue()).isEqualTo(cardIssuers);
        verify(cardIssuersObserver).onChanged(eq(cardIssuers));
    }


    @Test
    public void given_repositoryReturnError_when_goToSelectCardIssuers_then_hideLoading() throws Exception {
        final HttpException error = new HttpException(Response.error(404, mockErrorBody));
        viewModel.getShowLoading().observeForever(showLoadingObserver);

        viewModel.goToSelectCardIssuers(selectedPaymentMethod);

        verify(repository).getCardIssuers(eq(selectedPaymentMethod.getId()), consumerCardIssuersSuccess.capture(), consumerError.capture());
        consumerError.getValue().accept(error);
        assertThat(viewModel.getShowLoading().getValue()).isFalse();
        verify(showLoadingObserver).onChanged(false);
    }


    @Test
    public void given_repositoryReturnError_when_goToSelectCardIssuers_then_showErrorAndRetryMessage() throws Exception {
        final HttpException error = new HttpException(Response.error(404, mockErrorBody));
        viewModel.getShowErrorMessage().observeForever(eventObserver);

        viewModel.goToSelectCardIssuers(selectedPaymentMethod);

        verify(repository).getCardIssuers(eq(selectedPaymentMethod.getId()), consumerCardIssuersSuccess.capture(), consumerError.capture());
        consumerError.getValue().accept(error);
        assertThat(viewModel.getShowErrorMessage().getValue()).isInstanceOf(Event.class);
        verify(eventObserver).onChanged(eventCaptor.capture());
    }


    @Test
    public void given_repositoryReturnZeroCardIssuers_when_goToSelectCardIssuers_then_showNoCardIssuersMessage() throws Exception {
        final List<CardIssuerModel> cardIssuers = new ArrayList<>();
        viewModel.getShowNoCardIssuersMessage().observeForever(eventObserver);

        viewModel.goToSelectCardIssuers(selectedPaymentMethod);

        verify(repository).getCardIssuers(eq(selectedPaymentMethod.getId()), consumerCardIssuersSuccess.capture(), consumerError.capture());
        consumerCardIssuersSuccess.getValue().accept(cardIssuers);
        assertThat(viewModel.getShowNoCardIssuersMessage().getValue()).isInstanceOf(Event.class);
        verify(eventObserver).onChanged(eventCaptor.capture());
    }


    @Test
    public void given_repositoryReturnNull_when_goToSelectCardIssuers_then_showNoCardIssuersMessage() throws Exception {
        final List<CardIssuerModel> cardIssuers = null;
        viewModel.getShowNoCardIssuersMessage().observeForever(eventObserver);

        viewModel.goToSelectCardIssuers(selectedPaymentMethod);

        verify(repository).getCardIssuers(eq(selectedPaymentMethod.getId()), consumerCardIssuersSuccess.capture(), consumerError.capture());
        consumerCardIssuersSuccess.getValue().accept(cardIssuers);
        assertThat(viewModel.getShowNoCardIssuersMessage().getValue()).isInstanceOf(Event.class);
        verify(eventObserver).onChanged(eventCaptor.capture());
    }


    @Test
    public void given_paramIsNull_when_goToSelectCardIssuers_then_doNothing() {
        final PaymentMethodModel selectedPaymentMethod = null;

        viewModel.goToSelectCardIssuers(selectedPaymentMethod);

        verifyZeroInteractions(showLoadingObserver);
        verifyZeroInteractions(repository);
        verifyZeroInteractions(cardIssuersObserver);
        verifyZeroInteractions(eventObserver);
    }

    @Test
    public void given_selectedPaymentMethodIdIsNull_when_goToSelectCardIssuers_then_doNothing() {
        selectedPaymentMethod.setId(null);

        viewModel.goToSelectCardIssuers(selectedPaymentMethod);

        verifyZeroInteractions(showLoadingObserver);
        verifyZeroInteractions(repository);
        verifyZeroInteractions(cardIssuersObserver);
        verifyZeroInteractions(eventObserver);
    }

    @Test
    public void given_selectedPaymentMethodIdIsEmpty_when_goToSelectCardIssuers_then_doNothing() {
        selectedPaymentMethod.setId("");

        viewModel.goToSelectCardIssuers(selectedPaymentMethod);

        verifyZeroInteractions(showLoadingObserver);
        verifyZeroInteractions(repository);
        verifyZeroInteractions(cardIssuersObserver);
        verifyZeroInteractions(eventObserver);
    }

    /*
    @Test
    public void given_selectedCuotaIsSet_when_showPaymentData_then_showDataInFirstView() {
        doReturn(4000L).when(paymentDataCollector).getAmount();
        doReturn("Mastercard").when(paymentDataCollector).getFormattedPaymentMethod();
        doReturn("Banco Comafi").when(paymentDataCollector).getFormattedCardIssuer();
        doReturn("3 cuotas de $ 1.596,27 ($ 4.788,80)").when(paymentDataCollector).getFormattedCuota();
        final CuotaModel selectedCuota = new CuotaModel(3, "3 cuotas de $ 1.596,27 ($ 4.788,80)");

        presenter.showPaymentData(selectedCuota);

        verify(view).showDataInFirstView(
                paymentDataCollector.getAmount(),
                paymentDataCollector.getFormattedPaymentMethod(),
                paymentDataCollector.getFormattedCardIssuer(),
                paymentDataCollector.getFormattedCuota()
        );
    }

    //TODO agregar caso de obtencion de informacion de cuotas

    */
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