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
import android.widget.TextView;

import com.nelsonalfo.paymentapp.R;
import com.nelsonalfo.paymentapp.models.CuotaModel;
import com.nelsonalfo.paymentapp.commons.views.ImageAndTitleRecyclerViewAdapter;
import com.nelsonalfo.paymentapp.presentation.viewmodel.PaymentStepsViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.util.Objects.requireNonNull;

public class CuotasFragment extends Fragment implements ImageAndTitleRecyclerViewAdapter.Listener<CuotaModel> {
    @BindView(R.id.fragment_list_recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.fragment_list_title)
    TextView titleTextView;

    private PaymentStepsViewModel viewModel;


    public CuotasFragment() {
    }

    public static CuotasFragment newInstance() {
        return new CuotasFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(requireNonNull(getActivity())).get(PaymentStepsViewModel.class);
        viewModel.cuotas.observe(this, this::showCuotas);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_list_items, container, false);
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        titleTextView.setText(R.string.cuotas_title);
    }


    public void showCuotas(List<CuotaModel> cuotas) {
        final CuotasAdapter adapter = new CuotasAdapter(cuotas, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemSelected(CuotaModel item) {
        viewModel.showSelectedData(item);
    }
}
