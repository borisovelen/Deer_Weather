package com.example.bestweatherapp.autocomplete_scheme

data class AutocompleteSchemeItem(
    var AdministrativeArea: AdministrativeArea,
    var Country: Country,
    var Key: String,
    var LocalizedName: String,
    var Rank: Int,
    var Type: String,
    var Version: Int
)