package com.jd.harrypotterapp.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jd.harrypotterapp.data.entity.CharacterEntity
import com.jd.harrypotterapp.domain.GetDataInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    val getDataInteractor: GetDataInteractor
) : ViewModel() {

    private val mutableDataLoadedEvent: MutableLiveData<List<CharacterEntity>> = MutableLiveData()
    val dataLoadedEvent: LiveData<List<CharacterEntity>> = mutableDataLoadedEvent

    fun startDataRetrieval() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = getDataInteractor.getCharacterData()

            if (response.isSuccessful) {
                val data = response.body()!!
                mutableDataLoadedEvent.postValue(data)
            } else {
                Log.e("API Response Unsuccessful: ", response.message())
            }
        }
    }
}