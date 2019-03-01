package com.nelsonalfo.paymentapp.commons.rxjava;

import io.reactivex.Scheduler;

public interface PostExecutionThread {
    Scheduler getScheduler();
}
