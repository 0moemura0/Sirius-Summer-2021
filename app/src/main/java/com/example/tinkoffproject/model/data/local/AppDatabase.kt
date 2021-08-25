package com.example.tinkoffproject.model.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tinkoffproject.model.data.network.dto.response.CategoryNetwork
import com.example.tinkoffproject.model.data.network.dto.response.TransactionNetwork
import com.example.tinkoffproject.model.data.network.dto.response.WalletNetwork

@Database(
    entities = [
        CategoryNetwork::class,
        TransactionNetwork::class,
        WalletNetwork::class], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getCategoryDao(): CategoryDao
    abstract fun getTransactionDao(): TransactionDao
    abstract fun getWalletDao(): WalletDao
}