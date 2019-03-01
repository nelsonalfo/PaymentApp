package com.nelsonalfo.paymentapp.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.nelsonalfo.paymentapp.commons.Constants.API_BASE_URL;


@Module
public class NetworkModule {

    @Provides
    HttpLoggingInterceptor providesLoggingInterceptor() {
        return new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    }

    @Provides
    OkHttpClient providesOkHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        final OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor);

        return httpClient.build();
    }

    @Singleton
    @Provides
    Retrofit providesRetrofit(OkHttpClient okHttpClient) {
        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create());

        return builder.client(okHttpClient).build();
    }
}
