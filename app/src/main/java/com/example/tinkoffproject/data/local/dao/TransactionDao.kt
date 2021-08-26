package com.example.tinkoffproject.data.local.dao

import androidx.room.*
import com.example.tinkoffproject.data.dto.response.TransactionNetwork
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface TransactionDao {

    @Query("SELECT * from TransactionNetwork WHERE walletId = :walletId")
    fun getAll(walletId: Int): Single<List<TransactionNetwork>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(entity: TransactionNetwork): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(entities: List<TransactionNetwork>): Completable

    @Delete
    fun delete(entity: TransactionNetwork): Completable

    @Query("DELETE FROM TransactionNetwork WHERE id = :id")
    fun deleteById(id: Int): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(entity: TransactionNetwork): Completable

    @Query("DELETE FROM TransactionNetwork WHERE walletId = :walletId")
    fun removeAll(walletId: Int): Completable

}