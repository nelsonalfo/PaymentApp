package com.nelsonalfo.paymentapp.presentation.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nelsonalfo.paymentapp.R;
import com.nelsonalfo.paymentapp.models.PaymentMethodModel;
import com.nelsonalfo.paymentapp.presentation.adapters.ImageAndTitleRecyclerViewAdapter;
import com.nelsonalfo.paymentapp.presentation.adapters.PaymentMethodsAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaymentMethodsFragment extends Fragment implements ImageAndTitleRecyclerViewAdapter.Listener<PaymentMethodModel> {
    private Listener listener;

    @BindView(R.id.payment_method_recycler_view)
    RecyclerView recyclerView;


    public PaymentMethodsFragment() {
    }

    public static PaymentMethodsFragment newInstance() {
        return new PaymentMethodsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_payment_methods, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
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

    public void showPaymentMethods(List<PaymentMethodModel> paymentMethods) {
        final PaymentMethodsAdapter adapter = new PaymentMethodsAdapter(paymentMethods, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(PaymentMethodModel item) {
        listener.onPaymentMethodSelected(item);
    }

    public interface Listener {
        void onPaymentMethodSelected(PaymentMethodModel selectedPaymentMethod);
    }
}
