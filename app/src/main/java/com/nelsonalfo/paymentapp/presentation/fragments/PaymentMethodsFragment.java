package com.nelsonalfo.paymentapp.presentation.fragments;

import android.arch.lifecycle.ViewModelProviders;
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
import com.nelsonalfo.paymentapp.presentation.PaymentStepsViewModel;
import com.nelsonalfo.paymentapp.presentation.adapters.ImageAndTitleRecyclerViewAdapter;
import com.nelsonalfo.paymentapp.presentation.adapters.PaymentMethodsAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.util.Objects.requireNonNull;

public class PaymentMethodsFragment extends Fragment implements ImageAndTitleRecyclerViewAdapter.Listener<PaymentMethodModel> {
    @BindView(R.id.payment_method_recycler_view)
    RecyclerView recyclerView;
    private PaymentStepsViewModel viewModel;


    public PaymentMethodsFragment() {
    }

    public static PaymentMethodsFragment newInstance() {
        return new PaymentMethodsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(requireNonNull(getActivity())).get(PaymentStepsViewModel.class);
        viewModel.paymentMethods.observe(this, this::showPaymentMethods);
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


    public void showPaymentMethods(List<PaymentMethodModel> paymentMethods) {
        final PaymentMethodsAdapter adapter = new PaymentMethodsAdapter(paymentMethods, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(PaymentMethodModel item) {
        viewModel.fetchCardIssuers(item);
    }
}
