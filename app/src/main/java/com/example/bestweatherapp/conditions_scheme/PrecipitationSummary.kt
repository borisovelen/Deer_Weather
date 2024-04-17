package com.example.bestweatherapp.conditions_scheme

data class PrecipitationSummary(
    var Past12Hours: Past12Hours,
    var Past18Hours: Past18Hours,
    var Past24Hours: Past24Hours,
    var Past3Hours: Past3Hours,
    var Past6Hours: Past6Hours,
    var Past9Hours: Past9Hours,
    var PastHour: PastHour,
    var Precipitation: Precipitation
)