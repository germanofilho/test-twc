package com.germanofilho.app

import android.app.Application
import android.content.Context
import com.germanofilho.app.core.di.appModule
import com.germanofilho.app.data.model.entity.Settings
import com.germanofilho.app.data.source.local.manager.SharedPreferenceManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class App : Application() {

    override fun onCreate(){
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(appModule)
        }

        SharedPreferenceManager.initialize(this@App)
        saveDefaultSetting()
    }

    private fun saveDefaultSetting(){
        if(SharedPreferenceManager.getSettings() == null){
            SharedPreferenceManager.saveSettings(Settings(25, 5, false, 5))
        }
    }
}