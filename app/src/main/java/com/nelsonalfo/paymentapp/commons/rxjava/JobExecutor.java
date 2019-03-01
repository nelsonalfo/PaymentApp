package com.nelsonalfo.paymentapp.commons.rxjava;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

@Singleton
public class JobExecutor implements ThreadExecutor {
    @Inject
    public JobExecutor() {
    }

    @Override
    public Scheduler getScheduler() {
        return Schedulers.io();
    }
}