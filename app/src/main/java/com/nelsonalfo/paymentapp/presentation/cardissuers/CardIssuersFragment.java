package com.nelsonalfo.paymentapp.presentation.cardissuers;

import com.nelsonalfo.paymentapp.R;
import com.nelsonalfo.paymentapp.commons.views.PaymentStepsFragment;
import com.nelsonalfo.paymentapp.models.CardIssuerModel;

import java.util.Collections;
import java.util.List;

public class CardIssuersFragment extends PaymentStepsFragment<CardIssuerModel> {
    private CardIssuersAdapter adapter;

    public CardIssuersFragment() {
    }

    public static CardIssuersFragment newInstance() {
        return new CardIssuersFragment();
    }


    @Override
    public void observe() {
        viewModel.cardIssuers.observe(this, this::showCardIssuers);
    }

    @Override
    protected int getTitle() {
        return R.string.card_issuers_title;
    }

    @Override
    public CardIssuersAdapter getAdapter() {
        if(adapter == null){
            adapter = new CardIssuersAdapter(Collections.emptyList(), this);
        }
        return adapter;
    }

    public void showCardIssuers(List<CardIssuerModel> cardIssuers) {
        getAdapter().setData(cardIssuers);
    }

    @Override
    public void onItemSelected(CardIssuerModel item) {
        viewModel.fetchCuotas(item);
    }
}
