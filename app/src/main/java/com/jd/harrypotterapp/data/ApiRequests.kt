package com.jd.harrypotterapp.data

import retrofit2.Call
import retrofit2.http.GET

interface ApiRequests {
    @GET("/api/characters")
    fun getCharacters(): Call<List<CharacterEntity>>
}