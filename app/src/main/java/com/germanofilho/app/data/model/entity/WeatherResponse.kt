package com.germanofilho.app.data.model.entity

data class WeatherResponse(val name : String,
                           val id: Int,
                           val coord: Coord,
                           val main: Main,
                           val weather: List<Weather>,
                           val wind: Wind,
                           val sys: Sys)

data class Coord(val lon: Double, val lat: Double)

data class Weather(val id : Int,
                   val main: String,
                   val description: String,
                   val icon: String)

data class Main(val temp: Double,
                val humidity: Double,
                val temp_min: Double,
                val temp_max: Double)

data class Wind(val speed: Double, val deg: Double)

data class Sys(val id: Int, val country: String)



