package com.nelsonalfo.paymentapp.presentation;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.widget.Toast;

import com.nelsonalfo.paymentapp.R;
import com.nelsonalfo.paymentapp.commons.views.NonSwipeableViewPager;
import com.nelsonalfo.paymentapp.data.PaymentRepository;
import com.nelsonalfo.paymentapp.models.CardIssuer;
import com.nelsonalfo.paymentapp.models.Cuota;
import com.nelsonalfo.paymentapp.models.PaymentMethod;
import com.nelsonalfo.paymentapp.presentation.viewmodel.Event;
import com.nelsonalfo.paymentapp.presentation.viewmodel.PaymentStepsViewModel;
import com.nelsonalfo.paymentapp.presentation.viewmodel.SelectedData;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

import static com.nelsonalfo.paymentapp.presentation.PaymentStepsAdapter.AMOUNT;
import static com.nelsonalfo.paymentapp.presentation.PaymentStepsAdapter.CARD_ISSUERS;
import static com.nelsonalfo.paymentapp.presentation.PaymentStepsAdapter.CUOTAS;
import static com.nelsonalfo.paymentapp.presentation.PaymentStepsAdapter.PAYMENT_METHODS;

public class PaymentStepsActivity extends DaggerAppCompatActivity {

    @Inject
    PaymentRepository repository;

    @BindView(R.id.payment_steps_view_pager)
    NonSwipeableViewPager viewPager;

    private AlertDialog loadingDialog;

    private PaymentStepsViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        loadingDialog = new AlertDialog.Builder(this)
                .setMessage(R.string.loading_message)
                .setCancelable(false)
                .create();

        bindWithViewModel();

        final PaymentStepsAdapter adapter = new PaymentStepsAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }

    private void bindWithViewModel() {
        viewModel = ViewModelProviders.of(this).get(PaymentStepsViewModel.class);
        viewModel.setRepository(repository);

        viewModel.cardIssuers.observe(this, cardIssuers -> goToCardIssuers());
        viewModel.paymentMethods.observe(this, paymentMethods -> goToPaymentMethods());
        viewModel.cuotas.observe(this, cuotas -> goToCuotas());

        viewModel.showLoading.observe(this, this::showLoading);
        viewModel.showErrorMessage.observe(this, this::showErrorMessage);
        viewModel.showNoCuotasMessage.observe(this, this::showNoCuotasMessage);
        viewModel.showNoCardIssuersMessage.observe(this, this::showNoCardIssuersMessage);
        viewModel.showNoPaymentMethodsMessage.observe(this, this::showNoPaymentMethodsMessage);
        viewModel.selectedDataMessage.observe(this,this::showSelectedData);

    }

    private void goToPaymentMethods() {
        viewPager.setCurrentItem(PAYMENT_METHODS);
    }

    private void goToCardIssuers() {
        viewPager.setCurrentItem(CARD_ISSUERS);
    }

    private void goToCuotas() {
        viewPager.setCurrentItem(CUOTAS);
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
            Toast.makeText(this, "No se pudieron obtener los datos", Toast.LENGTH_SHORT).show();
        }
    }

    private void showNoCuotasMessage(Event<Boolean> event) {
        if (event.getContentIfNotHandled() != null) {
            Toast.makeText(this, "No existen cuotas para la seleccion realizada", Toast.LENGTH_SHORT).show();
        }
    }

    private void showNoCardIssuersMessage(Event<Boolean> event) {
        if (event.getContentIfNotHandled() != null) {
            Toast.makeText(this, "No existen Bancos para este Medio de Pago", Toast.LENGTH_SHORT).show();
        }
    }

    private void showNoPaymentMethodsMessage(Event<Boolean> event) {
        if (event.getContentIfNotHandled() != null) {
            Toast.makeText(this, "No existen Metodos de Pago", Toast.LENGTH_SHORT).show();
        }
    }

    private void showSelectedData(Event<SelectedData> event) {
        final SelectedData selectedData = event.getContentIfNotHandled();
        if (selectedData != null) {
            viewPager.setCurrentItem(AMOUNT);



            new AlertDialog.Builder(this)
                    .setMessage(getMessage(selectedData))
                    .create()
                    .show();
        }
    }

    private String getMessage(SelectedData selectedData) {
        final PaymentMethod paymentMethod = selectedData.paymentMethod;
        final CardIssuer cardIssuer = selectedData.cardIssuer;
        final Cuota cuota = selectedData.cuota;

        return "Monto seleccionado: \n$" + selectedData.amount +
                "\n\nMetodo de pago seleccionado: \n" + paymentMethod.getName() + " (id: " + paymentMethod.getId() + ")" +
                "\n\nBanco seleccionado: \n" + cardIssuer.getName() + " (id: " + cardIssuer.getId() + ")" +
                "\n\nCuota seleccionada: \n" + cuota.getRecommendedMessage() + " (installments: " + cuota.getInstallments() + ")";
    }
}
