package com.meli.melichallenge.data.di

import android.content.Context
import com.google.gson.Gson
import com.meli.melichallenge.data.feature.product.ProductRemoteDataSource
import com.meli.melichallenge.data.feature.product.ProductRepository
import com.meli.melichallenge.data.service.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object ProductModule {


    @Provides
    @Singleton
    fun provideRemoteProductDatasource(
        apiClient: ApiClient,
        gson: Gson,
    ): ProductRemoteDataSource {
        return ProductRemoteDataSource(apiClient, gson)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        productRemoteDataSource: ProductRemoteDataSource,
    ): ProductRepository {
        return ProductRepository(productRemoteDataSource)
    }
}
