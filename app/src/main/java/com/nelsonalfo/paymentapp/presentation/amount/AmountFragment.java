package com.nelsonalfo.paymentapp.presentation.amount;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.nelsonalfo.paymentapp.R;
import com.nelsonalfo.paymentapp.presentation.viewmodel.PaymentStepsViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static java.util.Objects.requireNonNull;

public class AmountFragment extends Fragment {
    @BindView(R.id.monto_edit_text)
    EditText montoEditText;


    public static AmountFragment newInstance() {
        return new AmountFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_amount, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @OnClick(R.id.siguiente_button)
    public void onSiguienteButtonPressed() {
        if (!TextUtils.isEmpty(montoEditText.getText())) {
            fetchPaymentMethods();
        } else {
            montoEditText.setError(getString(R.string.amount_not_set_error));
        }

    }

    private void fetchPaymentMethods() {
        final String textValue = montoEditText.getText().toString().trim();

        final PaymentStepsViewModel viewModel = ViewModelProviders.of(requireNonNull(getActivity()))
                .get(PaymentStepsViewModel.class);

        viewModel.fetchPaymentMethods(Long.parseLong(textValue));
    }
}
