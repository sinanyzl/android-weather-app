package com.example.android_weather_app.features.weather_info_show.model


import android.content.Context
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.example.android_weather_app.common.RequestCompleteListener
import com.example.android_weather_app.features.weather_info_show.model.data_class.WeatherInfoResponse
import com.example.android_weather_app.network.ApiInterface
import com.example.android_weather_app.RetrofitClient
import com.example.android_weather_app.features.weather_info_show.model.data_class.City
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class WeatherInfoShowModelImpl(private val context: Context): WeatherInfoShowModel{

    override fun getCityList(callback: RequestCompleteListener<MutableList<City>>) {
        try {
            val stream = context.assets.open("city_list.json")

            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            val tContents  = String(buffer)

            val groupListType = object : TypeToken<ArrayList<City>>() {}.type
            val gson = GsonBuilder().create()
            val cityList: MutableList<City> = gson.fromJson(tContents, groupListType)

            callback.onRequestSuccess(cityList) //let presenter know the city list

        } catch (e: IOException) {
            e.printStackTrace()
            callback.onRequestFailed(e.localizedMessage!!) //let presenter know about failure
        }
    }

}