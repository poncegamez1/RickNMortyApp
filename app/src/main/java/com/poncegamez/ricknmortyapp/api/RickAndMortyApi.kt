package com.poncegamez.ricknmortyapp.api

import com.poncegamez.ricknmortyapp.dto.CharacterDto
import com.poncegamez.ricknmortyapp.dto.CharactersDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("character/")
    suspend fun getCharactersList(@Query("page") page:Int): CharactersDto

    @GET("character/{id}")
    suspend fun getCharacterDetail(@Path("id") id: Int): CharacterDto

    @GET("character/")
    suspend fun searchCharacters(@Query("name") query: String, @Query("page") page: Int): CharactersDto

}