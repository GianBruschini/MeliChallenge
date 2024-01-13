package com.meli.melichallenge.data.di

import com.google.gson.Gson
import com.meli.melichallenge.data.feature.product.ProductRepository
import com.meli.melichallenge.data.service.ApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object ProductModule {


    @Provides
    @Singleton
    fun provideUserRepository(
        apiClient: ApiClient
    ): ProductRepository {
        return ProductRepository(apiClient)
    }
}
