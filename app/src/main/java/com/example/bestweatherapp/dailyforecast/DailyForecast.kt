package com.example.bestweatherapp.dailyforecast

data class DailyForecast(
    var Date: String,
    var Day: Day,
    var EpochDate: Int,
    var Link: String,
    var MobileLink: String,
    var Night: Night,
    var Sources: List<String>,
    var Temperature: Temperature
)