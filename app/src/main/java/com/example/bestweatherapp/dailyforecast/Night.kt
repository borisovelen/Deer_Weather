package com.example.bestweatherapp.dailyforecast

data class Night(
    var HasPrecipitation: Boolean,
    var Icon: Int,
    var IconPhrase: String,
    var PrecipitationIntensity: String,
    var PrecipitationType: String
)