package com.example.tinkoffproject.model.repository

import com.example.tinkoffproject.model.data.local.CategoryDao
import com.example.tinkoffproject.model.data.local.TransactionDao
import com.example.tinkoffproject.model.data.local.WalletDao
import com.example.tinkoffproject.model.data.network.ApiService

object RepositoryProvider {
    fun provideTransactionRepository(
        apiService: ApiService,
        dao: TransactionDao
    ): TransactionRepository {
        return TransactionRepository(apiService, dao)
    }

    fun provideCategoryRepository(apiService: ApiService, dao: CategoryDao): CategoryRepository {
        return CategoryRepository(apiService, dao)
    }

    fun provideUserRepository(apiService: ApiService): UserRepository {
        return UserRepository(apiService)
    }

    fun provideWalletRepository(apiService: ApiService, dao: WalletDao): WalletRepository {
        return WalletRepository(apiService, dao)
    }
}