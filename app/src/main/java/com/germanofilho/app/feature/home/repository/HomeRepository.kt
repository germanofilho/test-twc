package com.germanofilho.app.feature.home.repository

import com.germanofilho.app.core.repository.BaseRepository
import com.germanofilho.app.core.service.ApiFactory
import com.germanofilho.app.data.model.entity.WeatherResponse
import com.germanofilho.app.data.source.remote.api.searchweather.SearchWeatherApi
import io.reactivex.Observable

class HomeRepository : BaseRepository(){

    fun getWeatherByCity(city : String) : Observable<WeatherResponse>{
        return execute(ApiFactory.request(SearchWeatherApi::class.java).getWeatherByCity(city, "metric"))
    }
}