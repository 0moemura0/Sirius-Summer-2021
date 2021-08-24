package com.example.tinkoffproject.model.repository

import com.example.tinkoffproject.model.data.network.ApiService

object ApiRepositoryProvider {
    fun provideTransactionRepository(apiService: ApiService): TransactionRepository {
        return TransactionRepository(apiService)
    }

    fun provideCategoryRepository(apiService: ApiService): CategoryRepository {
        return CategoryRepository(apiService)
    }

    fun provideUserRepository(apiService: ApiService): UserRepository {
        return UserRepository(apiService)
    }

    fun provideWalletRepository(apiService: ApiService): WalletRepository {
        return WalletRepository(apiService)
    }
}