package com.example.bestweatherapp

data class LocationClass(
    var AdministrativeArea: AdministrativeArea,
    var Country: Country,
    var DataSets: List<String>,
    var EnglishName: String,
    var GeoPosition: GeoPosition,
    var IsAlias: Boolean,
    var Key: String,
    var LocalizedName: String,
    var PrimaryPostalCode: String,
    var Rank: Int,
    var Region: Region,
    var SupplementalAdminAreas: List<Any>,
    var TimeZone: TimeZone,
    var Type: String,
    var Version: Int
)

data class AdministrativeArea(
    var CountryID: String,
    var EnglishName: String,
    var EnglishType: String,
    var ID: String,
    var Level: Int,
    var LocalizedName: String,
    var LocalizedType: String
)

data class Country(
    var EnglishName: String,
    var ID: String,
    var LocalizedName: String
)

data class Elevation(
    var Imperial: Imperial,
    var Metric: Metric
)

data class GeoPosition(
    var Elevation: Elevation,
    var Latitude: Double,
    var Longitude: Double
)

data class Imperial(
    var Unit: String,
    var UnitType: Int,
    var Value: Int
)

data class Metric(
    var Unit: String,
    var UnitType: Int,
    var Value: Int
)

data class Region(
    var EnglishName: String,
    var ID: String,
    var LocalizedName: String
)

data class TimeZone(
    var Code: String,
    var GmtOffset: Int,
    var IsDaylightSaving: Boolean,
    var Name: String,
    var NextOffsetChange: String
)

