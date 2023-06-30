package com.example.android_weather_app.network

import com.google.gson.GsonBuilder
import com.example.android_weather_app.BuildConfig
//import java.util.logging.LoggingInterceptor
import okhttp3.OkHttpClient
import okhttp3.internal.platform.Platform
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
import java.util.logging.Level

object RetrofitClient {

    private var retrofit: Retrofit? = null
    private val gson = GsonBuilder().setLenient().create()

    val client: Retrofit
        get() {
            if (retrofit == null) {
                synchronized(Retrofit::class.java) {
                    if (retrofit == null) {

                        val httpClient = OkHttpClient.Builder()
                            .addInterceptor(QueryParameterAddInterceptor())


                        httpClient.addInterceptor(
                            LoggingInterceptor.Builder()
                                .loggable(BuildConfig.DEBUG)
                                .setLevel(Level.BASIC)
                                .log(Platform.INFO)
                                .request("LOG")
                                .response("LOG")
                                .executor(Executors.newSingleThreadExecutor())
                                .build())

                        val client = httpClient.build()

                        retrofit = Retrofit.Builder()
                            .baseUrl(BuildConfig.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .client(client)
                            .build()
                    }
                }

            }
            return retrofit!!
        }
}