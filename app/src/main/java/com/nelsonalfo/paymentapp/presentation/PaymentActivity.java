package com.nelsonalfo.paymentapp.presentation;

import android.os.Bundle;

import com.nelsonalfo.paymentapp.R;

import dagger.android.support.DaggerAppCompatActivity;

public class PaymentActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
