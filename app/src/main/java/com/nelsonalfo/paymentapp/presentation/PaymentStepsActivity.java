package com.nelsonalfo.paymentapp.presentation;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import com.nelsonalfo.paymentapp.R;
import com.nelsonalfo.paymentapp.commons.views.NonSwipeableViewPager;
import com.nelsonalfo.paymentapp.data.PaymentRepository;
import com.nelsonalfo.paymentapp.models.CardIssuerModel;
import com.nelsonalfo.paymentapp.presentation.adapters.PaymentStepsAdapter;
import com.nelsonalfo.paymentapp.presentation.fragments.AmountFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class PaymentStepsActivity extends DaggerAppCompatActivity implements
        AmountFragment.Listener {

    @Inject
    PaymentRepository repository;

    @BindView(R.id.payment_steps_view_pager)
    NonSwipeableViewPager viewPager;

    private PaymentStepsAdapter adapter;
    private AlertDialog loadingDialog;

    private PaymentStepsViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        loadingDialog = new AlertDialog.Builder(this)
                .setMessage(R.string.obteniendo_datos)
                .create();

        bindWithViewModel();

        adapter = new PaymentStepsAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    private void bindWithViewModel() {
        viewModel = ViewModelProviders.of(this).get(PaymentStepsViewModel.class);
        viewModel.setRepository(repository);

        viewModel.showLoading.observe(this, this::showLoading);
        viewModel.showErrorMessage.observe(this, this::showErrorMessage);
        viewModel.cardIssuers.observe(this, cardIssuers -> goToCardIssuers());
        viewModel.paymentMethods.observe(this, paymentMethods -> goToPaymentMethods());
        viewModel.showNoCardIssuersMessage.observe(this, this::showNoCardIssuersMessage);
        viewModel.showNoPaymentMethodsMessage.observe(this, this::showNoPaymentMethodsMessage);
    }

    private void goToPaymentMethods() {
        viewPager.setCurrentItem(PaymentStepsAdapter.PAYMENT_METHODS);
    }

    private void goToCardIssuers() {
        viewPager.setCurrentItem(PaymentStepsAdapter.CARD_ISSUERS);
    }

    public void showLoading(boolean show) {
        if (show) {
            loadingDialog.show();
        } else {
            loadingDialog.hide();
        }
    }


    private void showErrorMessage(Event<Boolean> event) {
        if (event.getContentIfNotHandled() != null) {
            //TODO show error
        }
    }

    private void showNoCardIssuersMessage(Event<Boolean> event) {
        if (event.getContentIfNotHandled() != null) {
            //TODO show message
        }
    }

    private void showNoPaymentMethodsMessage(Event<Boolean> event) {
        if (event.getContentIfNotHandled() != null) {
            //TODO show message
        }
    }

    private void showCardIssuers(List<CardIssuerModel> cardIssuers) {
        //TODO crear fragmento apra mostrar card issuers
    }

    @Override
    public void onMontoSet(long monto) {
        viewModel.fetchPaymentMethods(monto);
    }

    public void showPaymentMethodsErrorAndRetryMessage() {

    }

    public void showDataInFirstView(long amount, String paymentMethod, String cardIssuer, String cuotas) {

    }

    public void showCardIssuersErrorAndRetryMessage() {

    }
}
