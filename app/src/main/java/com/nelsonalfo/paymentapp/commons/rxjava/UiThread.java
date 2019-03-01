package com.nelsonalfo.paymentapp.commons.rxjava;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;

@Singleton
public class UiThread implements PostExecutionThread {

    @Inject
    public UiThread() {

    }

    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }
}