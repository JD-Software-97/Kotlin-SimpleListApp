package com.jd.harrypotterapp.data

import com.jd.harrypotterapp.data.entity.CharacterEntity
import retrofit2.Call
import retrofit2.http.GET

interface ApiRequests {
    @GET("/api/characters")
    fun getCharacters(): Call<List<CharacterEntity>>
}