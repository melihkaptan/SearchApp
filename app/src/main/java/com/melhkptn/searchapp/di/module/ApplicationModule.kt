package com.melhkptn.searchapp.di.module

import com.melhkptn.searchapp.BuildConfig
import com.melhkptn.searchapp.data.source.remote.SearchAPI
import com.melhkptn.searchapp.data.source.remote.base.DefaultRequestInterceptor
import com.melhkptn.searchapp.presentation.adapter.SearchAdapter
import com.melhkptn.searchapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun provideSearchApi(retrofit: Retrofit) : SearchAPI =
        retrofit.create(SearchAPI::class.java)

    @Provides
    @Singleton
    fun provideRequestInterceptor(): Interceptor = DefaultRequestInterceptor()

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        requestInterceptor: DefaultRequestInterceptor,
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient =
        with(OkHttpClient.Builder()) {
            addInterceptor(requestInterceptor)
            if (BuildConfig.DEBUG) addInterceptor(loggingInterceptor)
            connectTimeout(Constants.TIMEOUT_MILIS, TimeUnit.MILLISECONDS)
            build()
        }


    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient) = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Constants.BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .client(client)
        .build()



}