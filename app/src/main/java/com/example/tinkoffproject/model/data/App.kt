package com.example.tinkoffproject.model.data

import android.app.Application
import androidx.room.Room
import com.example.tinkoffproject.model.data.local.AppDatabase

class App : Application() {
    companion object {
        lateinit var instance: App
    }

    lateinit var database: AppDatabase
    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(this, AppDatabase::class.java, "app_database").build()
    }
}