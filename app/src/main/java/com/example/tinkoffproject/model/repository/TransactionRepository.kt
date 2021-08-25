package com.example.tinkoffproject.model.repository

import com.example.tinkoffproject.model.data.local.TransactionDao
import com.example.tinkoffproject.model.data.network.ApiService

class TransactionRepository(val apiService: ApiService, val dao: TransactionDao) {

}