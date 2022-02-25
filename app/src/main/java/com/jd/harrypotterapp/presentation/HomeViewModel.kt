package com.jd.harrypotterapp.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jd.harrypotterapp.data.ApiRequests
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class HomeViewModel @Inject constructor() : ViewModel() {

    fun startDataRetrieval() {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        viewModelScope.launch(Dispatchers.IO) {
            val response = api.getCharacters().awaitResponse()

            if (response.isSuccessful) {
                val data = response.body()!!
                Log.d("jht", data.toString())
            } else {
                Log.d("jht", response.toString())
            }
        }
    }

    companion object {
        const val BASE_URL = "http://hp-api.herokuapp.com/"
    }
}