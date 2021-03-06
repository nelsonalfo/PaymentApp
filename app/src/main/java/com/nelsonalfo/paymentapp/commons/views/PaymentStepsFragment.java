package com.nelsonalfo.paymentapp.commons.views;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nelsonalfo.paymentapp.R;
import com.nelsonalfo.paymentapp.presentation.viewmodel.PaymentStepsViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.util.Objects.requireNonNull;

public abstract class PaymentStepsFragment<T> extends Fragment implements ImageAndTitleRecyclerViewAdapter.Listener<T> {
    @BindView(R.id.fragment_list_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_list_title)
    TextView titleTextView;

    protected PaymentStepsViewModel viewModel;


    public PaymentStepsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(requireNonNull(getActivity())).get(PaymentStepsViewModel.class);

        bindWithViewModel();
    }

    public abstract void bindWithViewModel();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_list_items, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        titleTextView.setText(getTitle());

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(getAdapter());
    }

    @StringRes
    protected abstract int getTitle();


    public abstract ImageAndTitleRecyclerViewAdapter getAdapter();
}
