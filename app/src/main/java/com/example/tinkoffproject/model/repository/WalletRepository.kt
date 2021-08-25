package com.example.tinkoffproject.model.repository

import com.example.tinkoffproject.model.data.local.WalletDao
import com.example.tinkoffproject.model.data.network.ApiService

class WalletRepository(val apiService: ApiService, val dao: WalletDao){

}