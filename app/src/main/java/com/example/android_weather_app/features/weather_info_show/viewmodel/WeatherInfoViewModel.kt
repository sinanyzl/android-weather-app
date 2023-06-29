package com.example.android_weather_app.features.weather_info_show.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android_weather_app.common.RequestCompleteListener
import com.example.android_weather_app.features.weather_info_show.model.WeatherInfoShowModel
import com.example.android_weather_app.features.weather_info_show.model.data_class.City
import com.example.android_weather_app.features.weather_info_show.model.data_class.WeatherData
import com.example.android_weather_app.features.weather_info_show.model.data_class.WeatherInfoResponse
import com.example.android_weather_app.utils.kelvinToCelsius
import com.example.android_weather_app.utils.unixTimestampToDateTimeString
import com.example.android_weather_app.utils.unixTimestampToTimeString

class WeatherInfoViewModel : ViewModel() {


    val cityListLiveData = MutableLiveData<MutableList<City>>()
    val cityListFailureLiveData = MutableLiveData<String>()
    val weatherInfoLiveData = MutableLiveData<WeatherData>()
    val weatherInfoFailureLiveData = MutableLiveData<String>()
    val progressBarLiveData = MutableLiveData<Boolean>()

    fun getCityList(model: WeatherInfoShowModel) {

        model.getCityList(object :
            RequestCompleteListener<MutableList<City>> {
            override fun onRequestSuccess(data: MutableList<City>) {
                cityListLiveData.postValue(data)
            }


            override fun onRequestFailed(errorMessage: String) {
                cityListFailureLiveData.postValue(errorMessage)
            }
        })
    }


    fun getWeatherInfo(cityId: Int, model: WeatherInfoShowModel) {

        progressBarLiveData.postValue(true)

        model.getWeatherInfo(cityId, object :
            RequestCompleteListener<WeatherInfoResponse> {
            override fun onRequestSuccess(data: WeatherInfoResponse) {

                // business logic and data manipulation tasks should be done here
                val weatherData = WeatherData(
                    dateTime = data.dt.unixTimestampToDateTimeString(),
                    temperature = data.main.temp.kelvinToCelsius().toString(),
                    cityAndCountry = "${data.name}, ${data.sys.country}",
                    weatherConditionIconUrl = "http://openweathermap.org/img/w/${data.weather[0].icon}.png",
                    weatherConditionIconDescription = data.weather[0].description,
                    humidity = "${data.main.humidity}%",
                    pressure = "${data.main.pressure} mBar",
                    visibility = "${data.visibility/1000.0} KM",
                    sunrise = data.sys.sunrise.unixTimestampToTimeString(),
                    sunset = data.sys.sunset.unixTimestampToTimeString()
                )

                progressBarLiveData.postValue(false)


                weatherInfoLiveData.postValue(weatherData)
            }

            override fun onRequestFailed(errorMessage: String) {
                progressBarLiveData.postValue(false)
                weatherInfoFailureLiveData.postValue(errorMessage)
            }
        })
    }
}