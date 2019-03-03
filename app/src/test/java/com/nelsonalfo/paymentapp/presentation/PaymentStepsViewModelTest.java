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
public class PaymentStepsViewModelTest {
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

    private PaymentStepsViewModel viewModel;
    private CardIssuerModel selectedCardIssuer;


    @Before
    public void setUp() {
        montoIngresado = 4000;
        selectedPaymentMethod = new PaymentMethodModel("visa", "Visa", "active", "http://img.mlstatic.com/org-img/MP3/API/logos/naranja.gif");
        selectedCardIssuer = new CardIssuerModel("272", "Banco Comafi", "http://img.mlstatic.com/org-img/MP3/API/logos/272.gif");

        viewModel = new PaymentStepsViewModel();
        viewModel.setRepository(repository);
    }

    @Test
    public void given_montoIsSet_when_gotToSelectPaymentMethod_then_saveMontoInPaymentCollector() {
        viewModel.fetchPaymentMethods(montoIngresado);

        assertThat(viewModel.getAmount()).isEqualTo(montoIngresado);
    }

    @Test
    public void given_montoIsSet_when_fetchPaymentMethods_then_getPaymentMethodsFromRepository() {
        viewModel.fetchPaymentMethods(montoIngresado);

        verify(repository).getPaymentMethods(consumerPaymentMethodsSuccess.capture(), consumerError.capture());
    }

    @Test
    public void given_montoIsSet_when_fetchPaymentMethods_then_showLoading() {
        viewModel.showLoading.observeForever(showLoadingObserver);

        viewModel.fetchPaymentMethods(montoIngresado);

        assertThat(viewModel.showLoading.getValue()).isTrue();
        verify(showLoadingObserver).onChanged(eq(true));
    }

    @Test
    public void given_repositoryReturnPaymentMethods_when_fetchPaymentMethods_then_hideLoading() throws Exception {
        final List<PaymentMethodModel> paymentMethods = getPaymentMethodStubs();
        viewModel.showLoading.observeForever(showLoadingObserver);

        viewModel.fetchPaymentMethods(montoIngresado);

        verify(repository).getPaymentMethods(consumerPaymentMethodsSuccess.capture(), consumerError.capture());
        consumerPaymentMethodsSuccess.getValue().accept(paymentMethods);
        assertThat(viewModel.showLoading.getValue()).isFalse();
        verify(showLoadingObserver).onChanged(eq(false));
    }

    @Test
    public void given_repositoryReturnPaymentMethods_when_fetchPaymentMethods_then_showPaymentMethods() throws Exception {
        final List<PaymentMethodModel> paymentMethods = getPaymentMethodStubs();
        viewModel.paymentMethods.observeForever(paymentMethodsObserver);

        viewModel.fetchPaymentMethods(montoIngresado);

        verify(repository).getPaymentMethods(consumerPaymentMethodsSuccess.capture(), consumerError.capture());
        consumerPaymentMethodsSuccess.getValue().accept(paymentMethods);
        assertThat(viewModel.paymentMethods.getValue()).isEqualTo(paymentMethods);
        verify(paymentMethodsObserver).onChanged(eq(paymentMethods));
    }

    @Test
    public void given_repositoryReturnError_when_fetchPaymentMethods_then_showErrorAndRetryMessage() throws Exception {
        final HttpException error = new HttpException(Response.error(404, mockErrorBody));
        viewModel.showErrorMessage.observeForever(eventObserver);

        viewModel.fetchPaymentMethods(montoIngresado);

        verify(repository).getPaymentMethods(consumerPaymentMethodsSuccess.capture(), consumerError.capture());
        consumerError.getValue().accept(error);
        assertThat(viewModel.showErrorMessage.getValue()).isInstanceOf(Event.class);
        verify(eventObserver).onChanged(eventCaptor.capture());
    }

    @Test
    public void given_repositoryReturnError_when_fetchPaymentMethods_then_hideLoading() throws Exception {
        viewModel.showLoading.observeForever(showLoadingObserver);
        final HttpException error = new HttpException(Response.error(404, mockErrorBody));

        viewModel.fetchPaymentMethods(montoIngresado);

        verify(repository).getPaymentMethods(consumerPaymentMethodsSuccess.capture(), consumerError.capture());
        consumerError.getValue().accept(error);
        assertThat(viewModel.showLoading.getValue()).isFalse();
        verify(showLoadingObserver).onChanged(false);
    }

    @Test
    public void given_repositoryReturnZeroPaymentMethods_when_fetchPaymentMethods_then_showNoPaymentMethodsMessage() throws Exception {
        final List<PaymentMethodModel> paymentMethods = new ArrayList<>();
        viewModel.showNoPaymentMethodsMessage.observeForever(eventObserver);

        viewModel.fetchPaymentMethods(montoIngresado);

        verify(repository).getPaymentMethods(consumerPaymentMethodsSuccess.capture(), consumerError.capture());
        consumerPaymentMethodsSuccess.getValue().accept(paymentMethods);
        assertThat(viewModel.showNoPaymentMethodsMessage.getValue()).isInstanceOf(Event.class);
        verify(eventObserver).onChanged(eventCaptor.capture());
    }

    @Test
    public void given_repositoryReturnNull_when_fetchPaymentMethods_then_showNoPaymentMethodsMessage() throws Exception {
        final ArrayList<PaymentMethodModel> paymentMethods = null;
        viewModel.showNoPaymentMethodsMessage.observeForever(eventObserver);

        viewModel.fetchPaymentMethods(montoIngresado);

        verify(repository).getPaymentMethods(consumerPaymentMethodsSuccess.capture(), consumerError.capture());
        consumerPaymentMethodsSuccess.getValue().accept(paymentMethods);
        assertThat(viewModel.showNoPaymentMethodsMessage.getValue()).isInstanceOf(Event.class);
        verify(eventObserver).onChanged(eventCaptor.capture());
    }

    @Test
    public void given_dataIsAlreadySet_when_gotToSelectPaymentMethod_then_showTheDataAlreadySet() {
        final List<PaymentMethodModel> paymentMethodStubs = getPaymentMethodStubs();
        viewModel.paymentMethods.observeForever(paymentMethodsObserver);
        viewModel.paymentMethods.setValue(paymentMethodStubs);

        viewModel.fetchPaymentMethods(montoIngresado);

        verifyZeroInteractions(showLoadingObserver);
        verifyZeroInteractions(repository);
        verify(paymentMethodsObserver).onChanged(eq(paymentMethodStubs));
    }

    @Test
    public void given_paymentMethodIsSet_when_fetchCardIssuers_then_saveMontoInPaymentCollector() {
        viewModel.fetchCardIssuers(selectedPaymentMethod);

        assertThat(viewModel.getSelectedPaymentMethod()).isEqualTo(selectedPaymentMethod);
    }

    @Test
    public void given_paymentMethodIsSet_when_fetchCardIssuers_then_showLoading() {
        viewModel.showLoading.observeForever(showLoadingObserver);

        viewModel.fetchCardIssuers(selectedPaymentMethod);

        assertThat(viewModel.showLoading.getValue()).isTrue();
        verify(showLoadingObserver).onChanged(eq(true));
    }

    @Test
    public void given_paymentMethodIsSet_when_fetchCardIssuers_then_getCardIssuersFromRepository() {
        viewModel.fetchCardIssuers(selectedPaymentMethod);

        verify(repository).getCardIssuers(eq(selectedPaymentMethod.getId()), consumerCardIssuersSuccess.capture(), consumerError.capture());
    }

    @Test
    public void given_repositoryReturnCardIssuers_when_fetchCardIssuers_then_hideLoading() throws Exception {
        final List<CardIssuerModel> cardIssuers = getCardIssuerStubs();
        viewModel.showLoading.observeForever(showLoadingObserver);

        viewModel.fetchCardIssuers(selectedPaymentMethod);

        verify(repository).getCardIssuers(eq(selectedPaymentMethod.getId()), consumerCardIssuersSuccess.capture(), consumerError.capture());
        consumerCardIssuersSuccess.getValue().accept(cardIssuers);
        assertThat(viewModel.showLoading.getValue()).isFalse();
        verify(showLoadingObserver).onChanged(eq(false));
    }

    @Test
    public void given_repositoryReturnCardIssuers_when_fetchCardIssuers_then_showCardIssuers() throws Exception {
        final List<CardIssuerModel> cardIssuers = getCardIssuerStubs();
        viewModel.cardIssuers.observeForever(cardIssuersObserver);

        viewModel.fetchCardIssuers(selectedPaymentMethod);

        verify(repository).getCardIssuers(eq(selectedPaymentMethod.getId()), consumerCardIssuersSuccess.capture(), consumerError.capture());
        consumerCardIssuersSuccess.getValue().accept(cardIssuers);
        assertThat(viewModel.cardIssuers.getValue()).isEqualTo(cardIssuers);
        verify(cardIssuersObserver).onChanged(eq(cardIssuers));
    }

    @Test
    public void given_repositoryReturnError_when_fetchCardIssuers_then_hideLoading() throws Exception {
        final HttpException error = new HttpException(Response.error(404, mockErrorBody));
        viewModel.showLoading.observeForever(showLoadingObserver);

        viewModel.fetchCardIssuers(selectedPaymentMethod);

        verify(repository).getCardIssuers(eq(selectedPaymentMethod.getId()), consumerCardIssuersSuccess.capture(), consumerError.capture());
        consumerError.getValue().accept(error);
        assertThat(viewModel.showLoading.getValue()).isFalse();
        verify(showLoadingObserver).onChanged(false);
    }


    @Test
    public void given_repositoryReturnError_when_fetchCardIssuers_then_showErrorAndRetryMessage() throws Exception {
        final HttpException error = new HttpException(Response.error(404, mockErrorBody));
        viewModel.showErrorMessage.observeForever(eventObserver);

        viewModel.fetchCardIssuers(selectedPaymentMethod);

        verify(repository).getCardIssuers(eq(selectedPaymentMethod.getId()), consumerCardIssuersSuccess.capture(), consumerError.capture());
        consumerError.getValue().accept(error);
        assertThat(viewModel.showErrorMessage.getValue()).isInstanceOf(Event.class);
        verify(eventObserver).onChanged(eventCaptor.capture());
    }


    @Test
    public void given_repositoryReturnZeroCardIssuers_when_fetchCardIssuers_then_showNoCardIssuersMessage() throws Exception {
        final List<CardIssuerModel> cardIssuers = new ArrayList<>();
        viewModel.showNoCardIssuersMessage.observeForever(eventObserver);

        viewModel.fetchCardIssuers(selectedPaymentMethod);

        verify(repository).getCardIssuers(eq(selectedPaymentMethod.getId()), consumerCardIssuersSuccess.capture(), consumerError.capture());
        consumerCardIssuersSuccess.getValue().accept(cardIssuers);
        assertThat(viewModel.showNoCardIssuersMessage.getValue()).isInstanceOf(Event.class);
        verify(eventObserver).onChanged(eventCaptor.capture());
    }


    @Test
    public void given_repositoryReturnNull_when_fetchCardIssuers_then_showNoCardIssuersMessage() throws Exception {
        final List<CardIssuerModel> cardIssuers = null;
        viewModel.showNoCardIssuersMessage.observeForever(eventObserver);

        viewModel.fetchCardIssuers(selectedPaymentMethod);

        verify(repository).getCardIssuers(eq(selectedPaymentMethod.getId()), consumerCardIssuersSuccess.capture(), consumerError.capture());
        consumerCardIssuersSuccess.getValue().accept(cardIssuers);
        assertThat(viewModel.showNoCardIssuersMessage.getValue()).isInstanceOf(Event.class);
        verify(eventObserver).onChanged(eventCaptor.capture());
    }


    @Test
    public void given_paramIsNull_when_fetchCardIssuers_then_doNothing() {
        final PaymentMethodModel selectedPaymentMethod = null;

        viewModel.fetchCardIssuers(selectedPaymentMethod);

        verifyZeroInteractions(showLoadingObserver);
        verifyZeroInteractions(repository);
        verifyZeroInteractions(cardIssuersObserver);
        verifyZeroInteractions(eventObserver);
    }

    @Test
    public void given_selectedPaymentMethodIdIsNull_when_fetchCardIssuers_then_doNothing() {
        selectedPaymentMethod.setId(null);

        viewModel.fetchCardIssuers(selectedPaymentMethod);

        verifyZeroInteractions(showLoadingObserver);
        verifyZeroInteractions(repository);
        verifyZeroInteractions(cardIssuersObserver);
        verifyZeroInteractions(eventObserver);
    }

    @Test
    public void given_selectedPaymentMethodIdIsEmpty_when_fetchCardIssuers_then_doNothing() {
        selectedPaymentMethod.setId("");

        viewModel.fetchCardIssuers(selectedPaymentMethod);

        verifyZeroInteractions(showLoadingObserver);
        verifyZeroInteractions(repository);
        verifyZeroInteractions(cardIssuersObserver);
        verifyZeroInteractions(eventObserver);
    }

    @Test
    public void given_dataIsAlreadySet_when_fetchCardIssuers_then_showTheDataAlreadySet() {
        final List<CardIssuerModel> cardIssuerStubs = getCardIssuerStubs();
        viewModel.cardIssuers.observeForever(cardIssuersObserver);
        viewModel.cardIssuers.setValue(cardIssuerStubs);

        viewModel.fetchCardIssuers(selectedPaymentMethod);

        verifyZeroInteractions(showLoadingObserver);
        verifyZeroInteractions(repository);
        verify(cardIssuersObserver).onChanged(eq(cardIssuerStubs));
    }

    @Test
    public void given_cardIssuerIsSet_when_fetchInstallments_then_showLoading() {
        viewModel.fetchInstallments(selectedCardIssuer);
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