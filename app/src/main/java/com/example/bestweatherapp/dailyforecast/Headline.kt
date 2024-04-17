package com.example.bestweatherapp.dailyforecast

data class Headline(
    var Category: String,
    var EffectiveDate: String,
    var EffectiveEpochDate: Int,
    var EndDate: String,
    var EndEpochDate: Int,
    var Link: String,
    var MobileLink: String,
    var Severity: Int,
    var Text: String
)