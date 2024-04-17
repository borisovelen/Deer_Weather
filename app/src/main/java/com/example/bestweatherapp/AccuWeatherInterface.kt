package com.example.bestweatherapp

import com.example.bestweatherapp.autocomplete_scheme.AutocompleteScheme
import com.example.bestweatherapp.conditions_scheme.ConditionsCurrent
import com.example.bestweatherapp.dailyforecast.DailyForecastScheme
import com.example.bestweatherapp.hourlyforecast.HourlyForecastScheme
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AccuWeatherInterface {
    @GET("locations/v1/cities/geoposition/search")
    fun getLocationByLatLon(
        @Query("apikey") apiKey: String,
        @Query("q") lat_lon: String?
        ): Call<LocationClass>

    @GET("currentconditions/v1/{cityKey}")
    fun getCurrentConditions(
        @Path("cityKey") cityKey: String,
        @Query("apikey") apiKey: String,
        @Query("details") details: Boolean
    ): Call<ConditionsCurrent>

    @GET("forecasts/v1/daily/5day/{cityKey}")
    fun getDailyForecast(
        @Path("cityKey") cityKey: String,
        @Query("apikey") apiKey: String,
        @Query("metric") metric: Boolean
    ): Call<DailyForecastScheme>

    @GET("forecasts/v1/hourly/12hour/{cityKey}")
    fun getHourlyForecast(
        @Path("cityKey") cityKey: String,
        @Query("apikey") apiKey: String,
        @Query("metric") metric: Boolean
    ): Call<HourlyForecastScheme>

    @GET("locations/v1/cities/autocomplete")
    fun getAutocomplete(
        @Query("apikey") apiKey: String,
        @Query("q") textSearch: String
    ): Call<AutocompleteScheme>
}