package com.example.tinkoffproject.model.data.local

import androidx.room.*
import com.example.tinkoffproject.model.data.network.dto.response.CategoryNetwork
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface CategoryDao {
    @Query("SELECT * from CategoryNetwork")
    fun getAll(): Single<List<CategoryNetwork>>

    @Query("SELECT * from CategoryNetwork WHERE isIncome")
    fun getIncome(): Single<List<CategoryNetwork>>

    @Query("SELECT * from CategoryNetwork WHERE NOT isIncome")
    fun getExpenses(): Single<List<CategoryNetwork>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(entity: CategoryNetwork): Completable

    @Delete
    fun delete(entity: CategoryNetwork): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(entity: CategoryNetwork): Completable
}