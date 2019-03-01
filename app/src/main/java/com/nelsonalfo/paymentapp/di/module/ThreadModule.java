package com.nelsonalfo.paymentapp.di.module;

import com.nelsonalfo.paymentapp.commons.rxjava.JobExecutor;
import com.nelsonalfo.paymentapp.commons.rxjava.PostExecutionThread;
import com.nelsonalfo.paymentapp.commons.rxjava.ThreadExecutor;
import com.nelsonalfo.paymentapp.commons.rxjava.UiThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ThreadModule {

    @Singleton
    @Provides
    public ThreadExecutor provideThreadExecutor(JobExecutor executor) {
        return executor;
    }

    @Singleton
    @Provides
    public PostExecutionThread providePostExecutionThread(UiThread thread) {
        return thread;
    }

}
