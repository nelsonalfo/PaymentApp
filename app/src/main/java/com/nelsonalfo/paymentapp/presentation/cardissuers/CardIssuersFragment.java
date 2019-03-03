package com.nelsonalfo.paymentapp.presentation.cardissuers;

import com.nelsonalfo.paymentapp.R;
import com.nelsonalfo.paymentapp.commons.views.PaymentStepsFragment;
import com.nelsonalfo.paymentapp.models.CardIssuer;

import java.util.Collections;
import java.util.List;

public class CardIssuersFragment extends PaymentStepsFragment<CardIssuer> {
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

    public void showCardIssuers(List<CardIssuer> cardIssuers) {
        getAdapter().setData(cardIssuers);
    }

    @Override
    public void onItemSelected(CardIssuer item) {
        viewModel.fetchCuotas(item);
    }
}
