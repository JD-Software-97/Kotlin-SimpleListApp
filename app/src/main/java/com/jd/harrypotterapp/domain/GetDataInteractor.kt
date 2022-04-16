package com.jd.harrypotterapp.domain

import com.jd.harrypotterapp.data.ApiRequests
import com.jd.harrypotterapp.data.entity.CharacterEntity
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class GetDataInteractor @Inject constructor() {

    suspend fun getCharacterData(): Response<List<CharacterEntity>> {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        return api.getCharacters().awaitResponse()
    }

    companion object {
        const val BASE_URL = "http://hp-api.herokuapp.com/"
    }
}