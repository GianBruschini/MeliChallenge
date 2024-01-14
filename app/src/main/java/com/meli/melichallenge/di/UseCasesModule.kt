package com.meli.melichallenge.di

import com.meli.melichallenge.data.feature.product.ProductRepository
import com.meli.melichallenge.domain.usecase.product.GetProductDetailUseCase
import com.meli.melichallenge.domain.usecase.product.GetProductsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCasesModule {

    @Provides
    @Singleton
    fun provideGetProductUseCase(
        productRepository: ProductRepository,
    ): GetProductsUseCase {
        return GetProductsUseCase(productRepository)
    }

    @Provides
    @Singleton
    fun provideDetailProductUseCase(
        productRepository: ProductRepository,
    ): GetProductDetailUseCase {
        return GetProductDetailUseCase (productRepository)
    }

}
