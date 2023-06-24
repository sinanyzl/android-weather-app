package com.example.android_weather_app.features.weather_info_show.model

import com.example.android_weather_app.common.RequestCompleteListener
import com.example.android_weather_app.features.weather_info_show.model.data_class.City
import com.example.android_weather_app.features.weather_info_show.model.data_class.WeatherInfoResponse

interface WeatherInfoShowModel {
    fun getCityList(callback: RequestCompleteListener<MutableList<City>>)
    fun getWeatherInfo(cityId: Int, callback: RequestCompleteListener<WeatherInfoResponse>)
}