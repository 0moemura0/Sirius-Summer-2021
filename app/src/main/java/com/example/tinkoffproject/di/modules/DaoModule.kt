package com.example.tinkoffproject.di.modules

import com.example.tinkoffproject.data.local.AppDatabase
import com.example.tinkoffproject.data.local.dao.CategoryDao
import com.example.tinkoffproject.data.local.dao.TransactionDao
import com.example.tinkoffproject.data.local.dao.WalletDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
@InstallIn(ActivityComponent::class)
class DaoModule {
    @Provides
    fun providesCategoryDao(database: AppDatabase): CategoryDao = database.getCategoryDao()

    @Provides
    fun providesTransactionDao(database: AppDatabase): TransactionDao = database.getTransactionDao()

    @Provides
    fun providesWalletDao(database: AppDatabase): WalletDao = database.getWalletDao()

    @Provides
    fun provideBaseUrl() = "http://34.88.25.70:9090/"
}