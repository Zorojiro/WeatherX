package com.trial.weatherx

import android.provider.SyncStateContract.Constants
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trial.weatherx.api.NetworkResponse
import com.trial.weatherx.api.constants
import com.trial.weatherx.api.RetrofitInstance
import com.trial.weatherx.api.WeatherModel
import kotlinx.coroutines.launch

class WeatherViewModel  : ViewModel() {
    private val weatherApi = RetrofitInstance.weatherapi
    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult: LiveData<NetworkResponse<WeatherModel>> = _weatherResult

    fun getData(city: String) {

        viewModelScope.launch {
           try{
               val response = weatherApi.getWeather(constants.apiKey, city)
               if (response.isSuccessful) {
                   response.body()?.let {
                       _weatherResult.value = NetworkResponse.Success(it)
                   }
               }
               else{
                   _weatherResult.value = NetworkResponse.Error("Failed to load data")
               }
           }
           catch (e:Exception){
               _weatherResult.value = NetworkResponse.Error("Failed to fetch data")
           }
        }







    }
}