package com.nelsonalfo.paymentapp.presentation.fragments;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.nelsonalfo.paymentapp.R;
import com.nelsonalfo.paymentapp.presentation.PaymentStepsViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static java.util.Objects.requireNonNull;

public class AmountFragment extends Fragment {
    @BindView(R.id.monto_edit_text)
    EditText montoEditText;

    private Listener listener;
    private PaymentStepsViewModel viewModel;


    public AmountFragment() {
    }

    public static AmountFragment newInstance() {
        return new AmountFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(requireNonNull(getActivity())).get(PaymentStepsViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_amount, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @OnClick(R.id.siguiente_button)
    public void onSiguienteButtonPressed() {
        if (listener != null) {
            if (!TextUtils.isEmpty(montoEditText.getText())) {
                notifyMontoIsSet();
            }else{
                montoEditText.setError("Debe ingresar un valor");
            }
        }
    }

    private void notifyMontoIsSet() {
        final String textValue = montoEditText.getText().toString().trim();
        viewModel.fetchPaymentMethods(Long.parseLong(textValue));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Listener) {
            listener = (Listener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement Listener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    public interface Listener {
        void onMontoSet(long monto);
    }
}
