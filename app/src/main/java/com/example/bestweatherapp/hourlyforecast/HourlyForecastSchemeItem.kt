package com.example.bestweatherapp.hourlyforecast

data class HourlyForecastSchemeItem(
    var DateTime: String,
    var EpochDateTime: Int,
    var HasPrecipitation: Boolean,
    var IconPhrase: String,
    var IsDaylight: Boolean,
    var Link: String,
    var MobileLink: String,
    var PrecipitationIntensity: String,
    var PrecipitationProbability: Int,
    var PrecipitationType: String,
    var Temperature: Temperature,
    var WeatherIcon: Int
)