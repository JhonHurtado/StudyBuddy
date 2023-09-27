package com.rep.studybuddy.model.preferencias

import android.content.Context
import android.content.SharedPreferences

class Prefs(private val context: Context) {

    private val SHARED_NAME = "DATABASE"
    private val SHARED_USER_TOKEN = "TOKEN"



    private val storage: SharedPreferences = context.getSharedPreferences(SHARED_NAME, 0)


    fun saveToken(token: String) {
        storage.edit().putString(SHARED_USER_TOKEN, token).apply()
    }

    fun getToken(): String? {
        return storage.getString(SHARED_USER_TOKEN, null)
    }

    fun deleteToken() {
        storage.edit().remove(SHARED_USER_TOKEN).apply()
    }

    fun saveRecuerdame(recuerdame: Boolean) {
        storage.edit().putBoolean("recuerdame", recuerdame).apply()
    }

    fun getRecuerdame(): Boolean {
        return storage.getBoolean("recuerdame", false)
    }


}