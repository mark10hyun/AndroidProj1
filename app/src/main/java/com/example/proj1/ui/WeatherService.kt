package com.example.proj1.ui
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
interface WeatherService {
    @GET("data/2.5/weather?")
    fun getCurrentWeatherData(@Query("lat")lat:String, @Query("Ion") Ion: String, @Query("APPID")  app_id: String): Call<WeatherResponse>
}