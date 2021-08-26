package com.example.tinkoffproject.data.local.dao

import androidx.room.*
import com.example.tinkoffproject.data.dto.response.WalletNetwork
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface WalletDao {
    @Query("SELECT * from WalletNetwork")
    fun getAll(): Observable<List<WalletNetwork>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(entity: WalletNetwork): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(entities: List<WalletNetwork>): Completable

    @Delete
    fun delete(entity: WalletNetwork): Completable

    @Query("DELETE FROM WalletNetwork WHERE id = :userId")
    fun deleteByUserId(userId: Int): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(entity: WalletNetwork): Completable

    @Query("DELETE FROM WalletNetwork")
    fun removeAll(): Completable


}