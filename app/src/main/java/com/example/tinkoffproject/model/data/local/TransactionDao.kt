package com.example.tinkoffproject.model.data.local

import androidx.room.*
import com.example.tinkoffproject.model.data.network.dto.response.TransactionNetwork
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface TransactionDao {
    @Query("SELECT * from TransactionNetwork")
    fun getAll(): Single<List<TransactionNetwork>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(entity: TransactionNetwork): Completable

    @Delete
    fun delete(entity: TransactionNetwork): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(entity: TransactionNetwork): Completable
}