package com.germanofilho.app.feature.settings.presentation.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.germanofilho.app.data.model.entity.Settings
import com.germanofilho.app.data.source.local.manager.SharedPreferenceManager
import com.germanofilho.twc_test.R
import com.warkiz.widget.IndicatorSeekBar
import com.warkiz.widget.OnSeekChangeListener
import com.warkiz.widget.SeekParams
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.content_settings.*

class SettingsActivity : AppCompatActivity() {

    companion object {
        fun newInstance(context: Context): Intent {
            return Intent(context, SettingsActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        toolbar_settings.setTitle(R.string.settings)
        setSupportActionBar(toolbar_settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar_settings.setNavigationOnClickListener { onBackPressed() }
        checkSavedSettings()
        loadCurrentData()
        shouldEnableSaveButton(false)
    }

    override fun onStart() {
        super.onStart()
        sb_wind_speed_max.onSeekChangeListener = seekChangeListener
        sb_temperature_max.onSeekChangeListener = seekChangeListener
        sb_temperature_min.onSeekChangeListener = seekChangeListener
        sw_rain.setOnClickListener {
            loadCurrentData()
            shouldEnableSaveButton(true)
        }

        btn_save.setOnClickListener {
            SharedPreferenceManager.saveSettings(
                Settings(
                    sb_temperature_max.progress,
                    sb_temperature_min.progress, sw_rain.isChecked, sb_wind_speed_max.progress
                )
            )

            shouldEnableSaveButton(false)
            Toast.makeText(this, getString(R.string.msg_saved_conditions), Toast.LENGTH_LONG).show()
        }
    }


    private fun loadCurrentData() {
        txt_current_setting.text = getString(
            com.germanofilho.twc_test.R.string.settings_info,
            sb_temperature_max.progress.toString(),
            sb_temperature_min.progress.toString(),
            sb_wind_speed_max.progress.toString(),
            sw_rain.isChecked.toString()
        )
    }


    private val seekChangeListener = object : OnSeekChangeListener {
        override fun onSeeking(seekParams: SeekParams?) {}

        override fun onStartTrackingTouch(seekBar: IndicatorSeekBar?) {}

        override fun onStopTrackingTouch(seekBar: IndicatorSeekBar?) {
            loadCurrentData()
            shouldEnableSaveButton(true)
        }
    }


    private fun checkSavedSettings() {
        val settings = SharedPreferenceManager.getSettings()
        if (settings != null) {
            sb_temperature_max.setProgress(settings.maxTemp.toFloat())
            sb_temperature_min.setProgress(settings.minTemp.toFloat())
            sb_wind_speed_max.setProgress(settings.wind.toFloat())
            sw_rain.isChecked = settings.rain
        } else {
            sb_temperature_max.setProgress(25f)
            sb_temperature_min.setProgress(5f)
            sb_wind_speed_max.setProgress(5f)
            sw_rain.isChecked = false
        }
    }

    private fun shouldEnableSaveButton(b: Boolean) {
        if (b) {
            btn_save.isEnabled = true
            btn_save.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary))
        } else {
            btn_save.isEnabled = false
            btn_save.setBackgroundColor(ContextCompat.getColor(this, R.color.gray))
        }
    }

}
