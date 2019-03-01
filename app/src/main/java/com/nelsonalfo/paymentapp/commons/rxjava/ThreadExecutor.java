package com.nelsonalfo.paymentapp.commons.rxjava;

import io.reactivex.Scheduler;

public interface ThreadExecutor {
    Scheduler getScheduler();
}
