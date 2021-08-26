package com.example.tinkoffproject.data.local.dao

import androidx.room.*
import com.example.tinkoffproject.data.dto.response.CategoryNetwork
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface CategoryDao {
    @Query("SELECT * from CategoryNetwork")
    fun getAll(): Observable<List<CategoryNetwork>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(entity: CategoryNetwork): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAll(entities: List<CategoryNetwork>): Completable

    @Delete
    fun delete(entity: CategoryNetwork): Completable

    @Query("DELETE FROM CategoryNetwork WHERE id = :id")
    fun deleteById(id: Int): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(entity: CategoryNetwork): Completable

    @Query("DELETE FROM CategoryNetwork")
    fun removeAll(): Completable

    @Query("SELECT * from CategoryNetwork WHERE isIncome = :isIncome")
    fun getAllByType(isIncome: Boolean): Observable<List<CategoryNetwork>>

    @Query("DELETE FROM CategoryNetwork WHERE isIncome = :isIncome")
    fun removeAllByType(isIncome: Boolean): Completable
}