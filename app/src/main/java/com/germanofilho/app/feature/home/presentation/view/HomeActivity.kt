package com.germanofilho.app.feature.home.presentation.view

import android.os.Bundle
import android.view.View
import com.germanofilho.app.core.helper.observeResource
import com.germanofilho.app.core.view.BaseActivity
import com.germanofilho.app.data.model.entity.WeatherResponse
import com.germanofilho.app.data.source.local.manager.SharedPreferenceManager
import com.germanofilho.app.feature.home.presentation.viewmodel.HomeViewModel
import com.germanofilho.app.feature.settings.presentation.view.SettingsActivity
import com.germanofilho.twc_test.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : BaseActivity() {

    private val viewModel by viewModel<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        initViewModel()
    }

    override fun onStart() {
        super.onStart()
        btn_search.setOnClickListener {
            if(!edt_city.text.isEmpty()) {
                viewModel.fetchWeatherByCity(edt_city.text.toString())
            } else {
                edt_city.error = getString(R.string.empty_field)
                edt_city.requestFocus()
            }
        }

        btn_settings.setOnClickListener {
            startActivity(SettingsActivity.newInstance(this))
        }
    }

    private fun initViewModel() {
        viewModel.weatherLiveData.observeResource(this,
            onSuccess = {
                hideKeyboard()
                showSuccess()
                loadData(it)
            },
            onError = {
                showError()
            },
            onLoading = {
                showLoading(it)
            })
    }


    private fun showLoading(b: Boolean){
        if(b) {
            progress_bar.visibility = View.VISIBLE
            btn_search.text = ""
        } else {
            progress_bar.visibility = View.GONE
            btn_search.text = getString(R.string.search)
        }
    }

    private fun showError(){
        fadeIn(txt_day_status)
        txt_day_status.text = getString(R.string.msg_not_found)
        txt_day_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_neutral_day, 0, 0, 0)
        txt_result.visibility = View.INVISIBLE
        txt_average.visibility = View.INVISIBLE
        img_weather.visibility = View.INVISIBLE
    }

    private fun loadData(data : WeatherResponse){
        Picasso.get()
            .load(getImage(data.weather.first().icon))
            .into(img_weather)
        txt_result.text = getString(R.string.data_weather, data.main.temp_min.toInt(), data.main.temp_max.toInt(), data.wind.speed.toInt())
        txt_average.text = getString(R.string.data_city, data.name, data.sys.country, getFlagEmoji(data.sys.country), data.main.temp.toInt(), data.weather.first().description)

        if(isGoodDay(data)){
            txt_day_status.text = getString(R.string.msg_day_status, "Good")
            txt_day_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_good_day, 0, 0, 0)
        } else {
            txt_day_status.text = getString(R.string.msg_day_status, "Bad")
            txt_day_status.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_bad_day, 0, 0, 0)
        }

        fadeIn(txt_day_status)

    }

    private fun getFlagEmoji(countryCode : String): String {
        val firstLetter = Character.codePointAt(countryCode, 0) - 0x41 + 0x1F1E6
        val secondLetter = Character.codePointAt(countryCode, 1) - 0x41 + 0x1F1E6
        return String(Character.toChars(firstLetter)) + String(Character.toChars(secondLetter))
    }

    private fun isGoodDay(data : WeatherResponse) : Boolean{
        val settings = SharedPreferenceManager.getSettings()

        if(data.wind.speed > settings?.wind?.toFloat() ?: 5f) return false
        if(data.main.temp_max > settings?.maxTemp?.toFloat() ?: 25f) return false
        if(data.main.temp_min < settings?.minTemp?.toFloat() ?: 5f) return false
        if(data.weather.first().description.contains("rain") && settings?.rain == false) return false
        if(!data.weather.first().description.contains("rain") && settings?.rain == true) return false

        return true
    }

    private fun showSuccess(){
        txt_average.visibility = View.VISIBLE
        txt_result.visibility = View.VISIBLE
        img_weather.visibility = View.VISIBLE
    }

    private fun fadeIn(view : View){
        view.alpha = 0.0f
        view.animate().alpha(1f).setDuration(500).start()
    }
}
