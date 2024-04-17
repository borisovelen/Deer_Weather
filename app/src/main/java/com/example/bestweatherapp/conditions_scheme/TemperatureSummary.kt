package com.example.bestweatherapp.conditions_scheme

data class TemperatureSummary(
    var Past12HourRange: Past12HourRange,
    var Past24HourRange: Past24HourRange,
    var Past6HourRange: Past6HourRange
)