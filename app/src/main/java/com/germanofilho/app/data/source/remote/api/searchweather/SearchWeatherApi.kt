package com.germanofilho.app.data.source.remote.api.searchweather

import com.germanofilho.app.data.model.entity.WeatherResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchWeatherApi {
    @GET("weather")
    fun getWeatherByCity(@Query("q") city: String, @Query("units") units: String): Observable<WeatherResponse>

}