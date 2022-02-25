package com.jd.harrypotterapp.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jd.harrypotterapp.data.ApiRequests
import com.jd.harrypotterapp.data.entity.CharacterEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class HomeViewModel @Inject constructor() : ViewModel() {

    private val mutableDataLoadedEvent: MutableLiveData<List<CharacterEntity>> = MutableLiveData()
    val dataLoadedEvent: LiveData<List<CharacterEntity>> = mutableDataLoadedEvent

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
                mutableDataLoadedEvent.postValue(data)
            } else {
                Log.e("API Response Unsuccessful: ", response.message())
            }
        }
    }

    companion object {
        const val BASE_URL = "http://hp-api.herokuapp.com/"
    }
}