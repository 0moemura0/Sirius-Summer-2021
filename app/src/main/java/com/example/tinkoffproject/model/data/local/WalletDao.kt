package com.example.tinkoffproject.model.data.local

import androidx.room.*
import com.example.tinkoffproject.model.data.network.dto.response.WalletNetwork
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface WalletDao {
    @Query("SELECT * from WalletNetwork")
    fun getAll(): Single<List<WalletNetwork>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(entity: WalletNetwork): Completable

    @Delete
    fun delete(entity: WalletNetwork): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(entity: WalletNetwork): Completable

}