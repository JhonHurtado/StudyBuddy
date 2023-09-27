package com.rep.studybuddy.aplication

import android.annotation.SuppressLint
import android.app.Application
import com.rep.studybuddy.model.preferencias.Prefs

class Aplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var prefs: Prefs
    }

    override fun onCreate() {
        prefs = Prefs(applicationContext)
        super.onCreate()
    }

}