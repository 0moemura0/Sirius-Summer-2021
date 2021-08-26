package com.example.tinkoffproject.data

import android.app.Application
import android.content.Context

object UserData {
    var app: Application? = null
    var id: Int = 0
        get() {
            if (field == 0) {
                field = getIdFromSharedPreferences()
            }
            return field
        }
        set(value) {
            setIdToSharedPreferences(value)
            field = value
        }


    var email: String = ""
        get() {
            if (field == "") {
                field = getEmailFromSharedPreferences()
            }
            return field
        }
        set(value) {
            setEmailToSharedPreferences(value)
            field = value
        }


    private fun setIdToSharedPreferences(value: Int) {
        val sharedPreferences =
            app?.applicationContext?.getSharedPreferences(
                "APP_NAME",
                Context.MODE_PRIVATE
            )
        val editor = sharedPreferences?.edit()
        editor?.putInt("USER_ID", value)
        editor?.apply()
    }

    private fun setEmailToSharedPreferences(value: String) {
        val sharedPreferences =
            app?.applicationContext?.getSharedPreferences(
                "APP_NAME",
                Context.MODE_PRIVATE
            )
        val editor = sharedPreferences?.edit()
        editor?.putString("USER_NAME", value)
        editor?.apply()
    }

    private fun getEmailFromSharedPreferences(): String {
        return (app as Context).applicationContext.getSharedPreferences(
            "APP_NAME",
            Context.MODE_PRIVATE
        ).getString("USER_NAME", "").toString()

    }

    private fun getIdFromSharedPreferences(): Int {
        return (app as Context).applicationContext.getSharedPreferences(
            "APP_NAME",
            Context.MODE_PRIVATE
        ).getInt("USER_ID", 0)
    }
}