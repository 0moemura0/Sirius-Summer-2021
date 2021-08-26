package com.example.tinkoffproject.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tinkoffproject.data.dto.response.CategoryNetwork
import com.example.tinkoffproject.data.dto.response.TransactionNetwork
import com.example.tinkoffproject.data.dto.response.WalletNetwork
import com.example.tinkoffproject.data.local.dao.CategoryDao
import com.example.tinkoffproject.data.local.dao.TransactionDao
import com.example.tinkoffproject.data.local.dao.WalletDao

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