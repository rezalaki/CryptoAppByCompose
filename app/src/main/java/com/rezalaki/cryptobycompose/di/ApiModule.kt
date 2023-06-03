package com.rezalaki.cryptobycompose.di

import com.rezalaki.cryptobycompose.network.Apis
import com.rezalaki.cryptobycompose.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideClient() =
        OkHttpClient.Builder()
            .connectTimeout(Constants.DEFAULT_NETWORK_TIMEOUT_SECOND, TimeUnit.SECONDS)
            .writeTimeout(Constants.DEFAULT_NETWORK_TIMEOUT_SECOND, TimeUnit.SECONDS)
            .readTimeout(Constants.DEFAULT_NETWORK_TIMEOUT_SECOND, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient) =
        Retrofit.Builder()
            .baseUrl(Constants.API_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Apis::class.java)
}