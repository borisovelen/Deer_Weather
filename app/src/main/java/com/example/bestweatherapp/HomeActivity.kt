package com.example.bestweatherapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.icu.text.SimpleDateFormat
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.bestweatherapp.conditions_scheme.ConditionsCurrentItem
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.time.OffsetDateTime
import java.time.ZoneId
import java.util.Date


class HomeActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var lat: Double = 10.0;
    private var lon: Double = 10.0;
    private lateinit var cityKeyLocation: String
    private lateinit var pickedLocation: String
    private val iconName = mapOf(
        1 to "sun",
        2 to "sun",
        3 to "sun",
        4 to "sun_cloud",
        5 to "sun",
        6 to "sun_cloud",
        7 to "cloud_cloud",
        8 to "cloud_cloud",
        11 to "cloud_cloud",
        12 to "rain",
        13 to "rain",
        14 to "rain",
        15 to "lightning",
        16 to "lightning",
        17 to "lightning",
        18 to "rain",
        19 to "cloud_cloud",
        20 to "sun_cloud",
        21 to "sun_cloud",
        22 to "snow",
        23 to "snow",
        24 to "snow",
        25 to "snow",
        26 to "rain",
        29 to "rain",
        30 to "sun",
        31 to "snow",
        32 to "wind",
        33 to "moon",
        34 to "moon",
        35 to "moon_cloud",
        36 to "moon_cloud",
        37 to "moon_cloud",
        38 to "moon_cloud",
        39 to "rain",
        40 to "rain",
        41 to "lightning",
        42 to "lightning",
        43 to "cloud_cloud",
        44 to "cloud_cloud"
        )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        var swipeView: SwipeRefreshLayout = findViewById(R.id.swipe_to_refresh)
        swipeView.setOnRefreshListener {
            if(pickedLocation!=null){
                FetchInformation(pickedLocation, findViewById<TextView>(R.id.Location).text.toString())
            }
            swipeView.setRefreshing(false)
        }

        var mainScroll: View = findViewById(R.id.scroll_main)

        val searchButton: ImageButton = findViewById(R.id.image_button)
        searchButton.setOnClickListener { view ->
            mainScroll.visibility = View.GONE;
        }

        val search_card: LinearLayout = findViewById(R.id.search_my_location_card)
        search_card.setOnClickListener { view ->
            mainScroll.visibility = View.VISIBLE
            if(findViewById<TextView>(R.id.Condition_temp).text!=findViewById<TextView>(R.id.search_curr_temp).text){
                FetchInformation(cityKeyLocation, findViewById<TextView>(R.id.search_curr_name).text.toString())
            }
        }

        val search_box: SearchView = findViewById(R.id.search_bar)

        val context = this;

        search_box.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Handle the query submission
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                FetchAutocomplete(newText, context, findViewById(R.id.search_results))
                return true
            }
        })
        search_box.setOnCloseListener {
            findViewById<LinearLayout>(R.id.search_results).removeAllViews()
            true
        }
        
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        // Check for location permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permissions already granted, start location updates
            getLocation()
        } else {
            // Request location permissions if not granted
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun FetchAutocomplete(searchText: String, context: Context, parent: LinearLayout){

        AccuWeatherUtils.AutoComplete(searchText) {responseBody ->
            val cities = responseBody
            parent.removeAllViews()
            for((index, element) in cities.withIndex()){
                val linearLayout = LinearLayout(context)

                // Set LinearLayout properties
                linearLayout.orientation = LinearLayout.HORIZONTAL
                val layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, // width
                    LinearLayout.LayoutParams.WRAP_CONTENT // height
                )
                linearLayout.layoutParams = layoutParams
                linearLayout.setPadding(10,10,10,10)

                val text_view = TextView(context)
                text_view.text = element.LocalizedName+", "+element.Country.LocalizedName
                text_view.setTextColor(ContextCompat.getColor(context, R.color.text_color_light))
                text_view.setBackgroundResource(R.drawable.layout_secondary)
                text_view.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                val marginParams = ViewGroup.MarginLayoutParams(text_view.layoutParams)
                marginParams.setMargins(0, 10, 0,10)
                text_view.layoutParams = marginParams
                var paddingDp = 20;
                var density: Float = context.getResources().getDisplayMetrics().density
                var paddingPixel: Int = ((paddingDp * density).toInt());
                text_view.setPadding(paddingPixel);
                linearLayout.addView(text_view)
                linearLayout.setOnClickListener {view ->
                    var main: View = findViewById(R.id.scroll_main)
                    main.visibility = View.VISIBLE
                    val searchView: SearchView = findViewById(R.id.search_bar)
                    searchView.setQuery("", false)
                    searchView.isIconified = true
                    FetchInformation(element.Key, element.LocalizedName)
                    val inputMethodManager =
                        getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

                    // on below line hiding our keyboard.
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0)
                }
                parent.addView(linearLayout)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun FetchInformation(cityKey:String, cityName:String){
        pickedLocation = cityKey
        AccuWeatherUtils.FetchCondition(cityKey) {responseBody ->
            val body = responseBody.firstOrNull()
            if(body is ConditionsCurrentItem){
                val condition_temp: TextView = findViewById(R.id.Condition_temp)
                val condition_text: TextView = findViewById(R.id.condition_text)
                val condition_icon: ImageView = findViewById(R.id.condition_icon);
                val condition_feel_temp: TextView = findViewById(R.id.condition_feel_temp)
                val condition_humidity: TextView = findViewById(R.id.condition_humidity)
                val condition_rain_poss: TextView = findViewById(R.id.condition_rain_poss)
                val condition_wind_speed: TextView = findViewById(R.id.condition_wind_speed)
                val searchCurrCondition: TextView = findViewById(R.id.search_curr_condition)
                val searchCurrTemp: TextView = findViewById(R.id.search_curr_temp)
                val searchCurrIcon: ImageView = findViewById(R.id.search_curr_icon)
                val locationText: TextView = findViewById(R.id.Location);
                val updatedOn: TextView = findViewById(R.id.updated_on)
                val currentDate = SimpleDateFormat("HH:mm dd MMM").format(Date())
                val currLocationName: TextView = findViewById(R.id.search_curr_name)
                locationText.text = cityName;
                updatedOn.text = "Updated on: "+currentDate;

                condition_temp.text = ""+body.Temperature.Metric.Value+"°C"
                condition_text.text = body.WeatherText
                var condition_icon_text = iconName[body.WeatherIcon]
                var condition_icon_id = resources.getIdentifier(condition_icon_text, "drawable", packageName);
                if(condition_icon_id != 0){
                    condition_icon.setImageResource(condition_icon_id);
                    searchCurrIcon.setImageResource(condition_icon_id);
                }
                condition_feel_temp.text = ""+body.RealFeelTemperature.Metric.Value+"°C"
                condition_humidity.text = ""+body.RelativeHumidity+"%"
                condition_rain_poss.text = ""+body.PrecipitationSummary.PastHour.Metric.Value+"mm"
                condition_wind_speed.text = ""+body.Wind.Speed.Metric.Value+"m/s"

                if(cityKey==cityKeyLocation){
                    currLocationName.text = cityName;
                    searchCurrCondition.text = body.WeatherText;
                    searchCurrTemp.text = ""+body.Temperature.Metric.Value+"°C"
                }
            }
        }
        AccuWeatherUtils.FetchDailyForecast(cityKey) {responseBody ->
            var dailyForecastList = responseBody.DailyForecasts
            fun String.capitalizeFirstLetter(): String {
                return if (isNotEmpty()) {
                    this.substring(0, 1).uppercase() + this.substring(1).lowercase()
                } else {
                    this
                }
            }
            for((index, element) in dailyForecastList.withIndex()){
                val day_text_name = "day_${index+1}_text"
                val day_text_id = resources.getIdentifier(day_text_name, "id", packageName)
                if (day_text_id != 0) {
                    val component = findViewById<TextView>(day_text_id)
                    component.text = OffsetDateTime.parse(element.Date).toLocalDateTime().dayOfWeek.toString().capitalizeFirstLetter()
                }
                val day_min_name = "day_${index+1}_min"
                val day_min_id = resources.getIdentifier(day_min_name, "id", packageName)
                if(day_min_id != 0){
                    val component = findViewById<TextView>(day_min_id)
                    component.text = ""+element.Temperature.Minimum.Value+"°C"
                }
                val day_max_name = "day_${index+1}_max"
                val day_max_id = resources.getIdentifier(day_max_name, "id", packageName)
                if(day_max_id != 0){
                    val component = findViewById<TextView>(day_max_id)
                    component.text = ""+element.Temperature.Maximum.Value+"°C"
                }

                var day_icon_name = "day_${index+1}_icon"
                var day_icon_id = resources.getIdentifier(day_icon_name, "id", packageName)
                if(day_icon_id != 0){
                    var icon_component = findViewById<ImageView>(day_icon_id)
                    var icon_text= iconName[element.Day.Icon]
                    var drawable_id = resources.getIdentifier(icon_text,"drawable", packageName)
                    if(drawable_id != 0){
                        icon_component.setImageResource(drawable_id)
                    }
                }
            }
        }

        AccuWeatherUtils.FetchHourlyForecast(cityKey) {responseBody ->
            var hourlyForecastList = responseBody
            for((index, element) in hourlyForecastList.withIndex()){
                var hourly_hours_name = "hourly_${index+1}_hour"
                var hourly_icon_name = "hourly_${index+1}_icon"
                var hourly_temp_name = "hourly_${index+1}_temp"
                val hourly_hours_id = resources.getIdentifier(hourly_hours_name, "id", packageName)
                if(hourly_hours_id != 0){
                    var component = findViewById<TextView>(hourly_hours_id)
                    var hour = OffsetDateTime.parse(element.DateTime).atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime().hour
                    var displayHour = hour.toString()
                    if(hour<10){
                        displayHour = "0"+hour
                    }
                    component.text = displayHour
                }
                val hourly_temp_id = resources.getIdentifier(hourly_temp_name, "id", packageName)
                if(hourly_temp_id != 0){
                    var component = findViewById<TextView>(hourly_temp_id)
                    component.text = ""+element.Temperature.Value+"°"
                }
                var hourly_icon_id = resources.getIdentifier(hourly_icon_name, "id", packageName)
                if(hourly_icon_id != 0){
                    var icon_component = findViewById<ImageView>(hourly_icon_id)
                    var icon_text= iconName[element.WeatherIcon]
                    var drawable_id = resources.getIdentifier(icon_text,"drawable", packageName)
                    if(drawable_id != 0){
                        icon_component.setImageResource(drawable_id)
                    }
                }
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getLocation() {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                // Got last known location. In some rare situations, this can be null.
                if (location != null) {
                    lat = location.latitude
                    lon = location.longitude
                    AccuWeatherUtils.FetchLocation("${lat},${lon}") {responseBody ->
                        cityKeyLocation = responseBody.Key
                        FetchInformation(responseBody.Key, responseBody.EnglishName)
                    };
                    Log.d(TAG, "Latitude: $lat, Longitude: $lon")
                } else {
                    Log.e(TAG, "Last known location is null")
                }
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Failed to get location: ${e.message}")
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Location permission granted, start location updates
                getLocation()
            } else {
                // Location permission denied, handle accordingly (e.g., show rationale, disable location-related functionality)
                AccuWeatherUtils.FetchLocation("10,10") {responseBody ->
                    cityKeyLocation = responseBody.Key;
                    FetchInformation(responseBody.Key, responseBody.EnglishName)
                };
                Log.e(TAG, "Location permission denied")
            }
        }
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val REQUEST_LOCATION_PERMISSION = 1001
    }
}