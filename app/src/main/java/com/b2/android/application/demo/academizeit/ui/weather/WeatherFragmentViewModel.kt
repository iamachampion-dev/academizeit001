package com.b2.android.application.demo.academizeit.ui.weather

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.*
import com.b2.android.application.demo.academizeit.ui.weather.data.WeatherDetailData
import com.b2.android.application.demo.academizeit.ui.weather.network.OpenWeatherHttpClient
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WeatherFragmentViewModel: ViewModel() {
    // Two-way databinding, exposing MutableLiveData
    var temperature = MutableLiveData<String>()
    // Two-way databinding, exposing MutableLiveData
    var weatherInfo = MutableLiveData<String>()
    // Two-way databinding, exposing MutableLiveData
    var jsonFromHttp = MutableLiveData<String>()
    // Two-way databinding, exposing MutableLiveData
    var weatherInfoImage = MutableLiveData<Bitmap>()

    init {
        temperature.value = "--"
        weatherInfo.value = "foggy"
    }

    fun getJSONDataFromHttp(/*weatherData: String*/) {
//        val tempJSONString = "{\"coord\":{\"lon\":77.2167,\"lat\":28.6667},\"weather\":[{\"id\":741,\"main\":\"Fog\",\"description\":\"fog\",\"icon\":\"50d\"}],\"base\":\"stations\",\"main\":{\"temp\":285.2,\"feels_like\":284.92,\"temp_min\":285.2,\"temp_max\":285.2,\"pressure\":1011,\"humidity\":94},\"visibility\":700,\"wind\":{\"speed\":2.57,\"deg\":140},\"clouds\":{\"all\":100},\"dt\":1642913536,\"sys\":{\"type\":1,\"id\":9165,\"country\":\"IN\",\"sunrise\":1642902197,\"sunset\":1642940528},\"timezone\":19800,\"id\":1273294,\"name\":\"Delhi\",\"cod\":200}"
        viewModelScope.launch(Dispatchers.IO) {
            jsonFromHttp.postValue(OpenWeatherHttpClient.getWeatherData("Delhi, IN"))
        }
    }

    fun getDataFromJSON(jsonData: String) {
//        println("JSON string >> $jsonData")
        var gson = Gson()
        var testModel = gson.fromJson(jsonData, WeatherDetailData::class.java)

        temperature.value = testModel?.main?.temp.toString()
        weatherInfo.value = testModel?.weather?.get(0)?.main
        getWeatherInfoImage(testModel?.weather?.get(0)?.icon)
    }

    private fun getWeatherInfoImage(icon: String?) {
        println("icon code - $icon")
        icon?.let {
            viewModelScope.launch(Dispatchers.IO) {
                var byteArray = OpenWeatherHttpClient.getImage(icon)
                weatherInfoImage.postValue(BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size))
            }
        }
    }
}
