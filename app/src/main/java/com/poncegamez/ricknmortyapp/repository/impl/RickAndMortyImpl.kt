package com.poncegamez.ricknmortyapp.repository.impl

import com.poncegamez.ricknmortyapp.api.RickAndMortyApi
import com.poncegamez.ricknmortyapp.mappers.CharacterDetailMapper
import com.poncegamez.ricknmortyapp.mappers.CharactersMapper
import com.poncegamez.ricknmortyapp.models.CharacterDetail
import com.poncegamez.ricknmortyapp.models.Characters
import com.poncegamez.ricknmortyapp.repository.RickAndMortyRepository
import com.poncegamez.ricknmortyapp.result.Results
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class RickAndMortyImpl @Inject constructor(
    private val rickAndMortyApi: RickAndMortyApi
    ): RickAndMortyRepository {

    override fun getCharactersList(page: Int): Flow<Results<List<Characters>>> = flow {
        emit(Results.Loading())
        try {
            val response = rickAndMortyApi.getCharactersList(page)
            val charactersList = response.results.map { CharactersMapper.map(it) }
            emit(Results.Success(charactersList))
        } catch (e: HttpException) {
            emit(Results.Error(
                message = "Something went wrong!!",
                data = null
            ))
        } catch (e: IOException){
            emit(Results.Error(
                message = "Check your internet connection",
                data = null
            ))
        }
    }

    override suspend fun getCharacterDetail(id: Int): Results<CharacterDetail> {
        val response = try {
            rickAndMortyApi.getCharacterDetail(id)
        } catch (e: Exception) {
            return Results.Error("An unknown error occurred")
        }
        val characterDetail = CharacterDetailMapper.map(response)
        return Results.Success(characterDetail)
    }
}