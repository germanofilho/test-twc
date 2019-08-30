package com.germanofilho.app.feature.home.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import com.germanofilho.app.core.helper.Resource
import com.germanofilho.app.core.viewmodel.BaseViewModel
import com.germanofilho.app.data.model.entity.WeatherResponse
import com.germanofilho.app.feature.home.repository.HomeRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeViewModel(private val repository: HomeRepository) : BaseViewModel(){

    val weatherLiveData = MutableLiveData<Resource<WeatherResponse>>()

    fun fetchWeatherByCity(city : String) {
        val observable = repository.getWeatherByCity(city)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { weatherLiveData.loading(true) }
            .doFinally { weatherLiveData.loading(false) }
            .subscribe({
                weatherLiveData.success(it)
            }, {
                weatherLiveData.error(it)
            })

        mCompositeDisposable.addAll(observable)
    }



}