package com.example.tinkoffproject.di.modules

import com.example.tinkoffproject.data.local.AppDatabase
import com.example.tinkoffproject.data.local.dao.CategoryDao
import com.example.tinkoffproject.data.local.dao.TransactionDao
import com.example.tinkoffproject.data.local.dao.WalletDao
import com.example.tinkoffproject.data.network.ApiService
import com.example.tinkoffproject.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class ViewModelModule {
    @Provides
    fun providesCategoryDao(database: AppDatabase): CategoryDao = database.getCategoryDao()

    @Provides
    fun providesTransactionDao(database: AppDatabase): TransactionDao = database.getTransactionDao()

    @Provides
    fun providesWalletDao(database: AppDatabase): WalletDao = database.getWalletDao()

    @Provides
    fun provideBaseUrl() = "http://34.88.25.70:9090/"

    @Provides
    fun provideTransactionRepository(
        apiService: ApiService,
        dao: TransactionDao
    ): TransactionRepository = TransactionRepositoryImpl(apiService, dao)

    @Provides
    fun provideCategoryRepository(
        apiService: ApiService,
        dao: CategoryDao
    ): CategoryRepository = CategoryRepositoryImpl(apiService, dao)

    @Provides
    fun provideUserRepository(
        apiService: ApiService
    ): UserRepository = UserRepositoryImpl(apiService)

    @Provides
    fun provideWalletRepository(
        apiService: ApiService,
        dao: WalletDao
    ): WalletRepository = WalletRepositoryImpl(apiService, dao)
}