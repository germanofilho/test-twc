package com.germanofilho.app.data.source.local.manager

import android.content.Context
import android.content.SharedPreferences
import com.germanofilho.app.data.model.entity.Settings
import com.germanofilho.twc_test.BuildConfig
import com.google.gson.Gson

object SharedPreferenceManager {

    private lateinit var mPreference: SharedPreferences

    private const val SETTINGS = "settings"

    fun initialize(context: Context) {
        mPreference = context.getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)
    }

    fun saveSettings(settings: Settings?) {
        val json = if (settings != null) Gson().toJson(Settings(settings.maxTemp,
            settings.minTemp, settings.rain, settings.wind)) else null

        val edit = mPreference.edit()
        edit.putString(SETTINGS, json)
        edit.apply()
    }

    fun getSettings(): Settings? {
        val json = mPreference.getString(SETTINGS, null)
        return Gson().fromJson(json, Settings::class.java)
    }
}