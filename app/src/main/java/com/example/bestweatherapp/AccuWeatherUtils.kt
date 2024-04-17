package com.example.bestweatherapp

import android.util.Log
import com.example.bestweatherapp.autocomplete_scheme.AutocompleteScheme
import com.example.bestweatherapp.conditions_scheme.ConditionsCurrent
import com.example.bestweatherapp.dailyforecast.DailyForecastScheme
import com.example.bestweatherapp.hourlyforecast.HourlyForecastScheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AccuWeatherUtils {
    companion object{
        private var ACCU_WEATHER_URL: String = "https://dataservice.accuweather.com/"
        private val API_KEY: String = "olNOuUqyUke4oivu4PAt1wbcReUcMlNY"

        val retrofitBuidler = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(ACCU_WEATHER_URL)
            .build()
            .create(AccuWeatherInterface::class.java)

        fun AutoComplete(textSearch: String, callback: (AutocompleteScheme) -> Unit){
            val retrofitData = retrofitBuidler.getAutocomplete(API_KEY, textSearch);
            retrofitData.enqueue(object: Callback<AutocompleteScheme?> {
                override fun onResponse(call: Call<AutocompleteScheme?>, response: Response<AutocompleteScheme?>) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        callback(responseBody)
                    }
                    Log.d("FetchLocation", "response body is null")
                }

                override fun onFailure(call: Call<AutocompleteScheme?>, t: Throwable) {
                    Log.d("AccuWeatherUtils.FetchLocation", "onFailure: "+t.message)
                }
            })
        }

        fun FetchLocation(lon_lat:String, callback: (LocationClass) -> Unit){
            val retrofitData = retrofitBuidler.getLocationByLatLon(API_KEY, lon_lat);
            retrofitData.enqueue(object: Callback<LocationClass?> {
                override fun onResponse(call: Call<LocationClass?>, response: Response<LocationClass?>) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        callback(responseBody)
                    }
                    Log.d("FetchLocation", "response body is null")
                }

                override fun onFailure(call: Call<LocationClass?>, t: Throwable) {
                    Log.d("AccuWeatherUtils.FetchLocation", "onFailure: "+t.message)
                }
            })
        }

        fun FetchCondition(cityKey:String, callback: (ConditionsCurrent) -> Unit){
            val retrofitData = retrofitBuidler.getCurrentConditions(cityKey, API_KEY, true);
            retrofitData.enqueue(object: Callback<ConditionsCurrent?> {
                override fun onResponse(call: Call<ConditionsCurrent?>, response: Response<ConditionsCurrent?>) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        callback(responseBody)
                    }
                    Log.d("FetchCondition", "response body is null")
                }

                override fun onFailure(call: Call<ConditionsCurrent?>, t: Throwable) {
                    Log.d("AccuWeatherUtils.FetchCondition", "onFailure: "+t.message)
                }
            })
        }

        fun FetchHourlyForecast(cityKey: String, callback: (HourlyForecastScheme) -> Unit){
            val retrofitData = retrofitBuidler.getHourlyForecast(cityKey, API_KEY, true);
            retrofitData.enqueue(object: Callback<HourlyForecastScheme?> {
                override fun onResponse(call: Call<HourlyForecastScheme?>, response: Response<HourlyForecastScheme?>) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        callback(responseBody)
                    }
                    Log.d("FetchDailyForecast", "response body is null")
                }

                override fun onFailure(call: Call<HourlyForecastScheme?>, t: Throwable) {
                    Log.d("AccuWeatherUtils.FetchDailyForecast", "onFailure: "+t.message)
                }
            })
        }
        fun FetchDailyForecast(cityKey: String, callback: (DailyForecastScheme) -> Unit){
            val retrofitData = retrofitBuidler.getDailyForecast(cityKey, API_KEY, true);
            retrofitData.enqueue(object: Callback<DailyForecastScheme?> {
                override fun onResponse(call: Call<DailyForecastScheme?>, response: Response<DailyForecastScheme?>) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        callback(responseBody)
                    }
                    Log.d("FetchDailyForecast", "response body is null")
                }

                override fun onFailure(call: Call<DailyForecastScheme?>, t: Throwable) {
                    Log.d("AccuWeatherUtils.FetchDailyForecast", "onFailure: "+t.message)
                }
            })
        }
    }
}