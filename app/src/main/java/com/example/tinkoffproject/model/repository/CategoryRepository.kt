package com.example.tinkoffproject.model.repository

import com.example.tinkoffproject.model.data.local.CategoryDao
import com.example.tinkoffproject.model.data.network.ApiService
import com.example.tinkoffproject.model.data.network.dto.response.CategoryNetwork
import io.reactivex.Single

class CategoryRepository(private val apiService: ApiService, private val dao: CategoryDao) {
    fun getCategoriesRemote(isIncome: Boolean): Single<List<CategoryNetwork>> {
        return apiService.getCategories().map { it.filter { it1 -> it1?.isIncome == isIncome } }
    }

    fun getCategoriesLocal(isIncome: Boolean): Single<List<CategoryNetwork>> {
        return (if (isIncome) dao.getIncome() else dao.getExpenses())
    }
}