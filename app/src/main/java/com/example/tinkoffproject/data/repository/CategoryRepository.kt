package com.example.tinkoffproject.data.repository

import com.example.tinkoffproject.App
import com.example.tinkoffproject.data.network.ApiService
import com.example.tinkoffproject.data.dto.request.CreateCategory
import com.example.tinkoffproject.data.dto.response.CategoryNetwork
import com.example.tinkoffproject.data.local.dao.CategoryDao
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface CategoryRepository {
    fun getAllCategories(): Observable<List<CategoryNetwork>>
    fun getCategoriesByType(isIncome: Boolean): Observable<List<CategoryNetwork>>
    fun postCategory(createCategory: CreateCategory): Observable<CategoryNetwork>
}


class CategoryRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dao: CategoryDao
) :
    CategoryRepository {
    override fun getAllCategories(): Observable<List<CategoryNetwork>> {
        return Observable.concatArrayEager(
            dao.getAll().subscribeOn(Schedulers.io()),
            Observable.defer {
                if (App.isNetworkAvailable())
                    apiService.getCategories()
                        .subscribeOn(Schedulers.io())
                        .flatMap {
                            dao.removeAll()
                                .andThen(Completable.fromCallable { dao.addAll(it) }
                                    .andThen(Observable.just(it)))
                        }
                else
                    Observable.empty()
            }.subscribeOn(Schedulers.io())
        )

    }

    override fun getCategoriesByType(isIncome: Boolean): Observable<List<CategoryNetwork>> {
        return Observable.concatArrayEager(
            dao.getAllByType(isIncome).subscribeOn(Schedulers.io()),
            Observable.defer {
                if (App.isNetworkAvailable())
                    apiService.getCategories()
                        .map { list -> list.filter { it.isIncome == isIncome } }
                        .subscribeOn(Schedulers.io())
                        .flatMap {
                            dao.removeAllByType(isIncome)
                                .andThen(Completable.fromCallable { dao.addAll(it) }
                                    .andThen(Observable.just(it)))
                        }
                else
                    Observable.empty()
            }.subscribeOn(Schedulers.io())
        )
    }

    override fun postCategory(createCategory: CreateCategory): Observable<CategoryNetwork> {
        return Observable.defer {
            if (App.isNetworkAvailable())
                apiService.postCategories(createCategory).subscribeOn(Schedulers.io())
                    .flatMap {
                        Completable.fromCallable { dao.add(it) }.andThen(Observable.just(it))
                    }
            else
                Observable.error(IllegalAccessError("You are offline"))
        }
    }
}