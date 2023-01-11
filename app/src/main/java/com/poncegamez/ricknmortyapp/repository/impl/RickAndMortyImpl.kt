package com.poncegamez.ricknmortyapp.repository.impl

import android.content.Context
import com.poncegamez.ricknmortyapp.api.RickAndMortyApi
import com.poncegamez.ricknmortyapp.mappers.CharactersMapper
import com.poncegamez.ricknmortyapp.models.CharacterDetail
import com.poncegamez.ricknmortyapp.models.Characters
import com.poncegamez.ricknmortyapp.repository.RickAndMortyRepository
import com.poncegamez.ricknmortyapp.result.Results
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RickAndMortyImpl @Inject constructor(
    private val rickAndMortyApi: RickAndMortyApi
    ): RickAndMortyRepository {



    override fun getCharactersList(page: Int): Flow<Results<List<Characters>>> = flow {
        emit(Results.Loading())
        try {
            val response = rickAndMortyApi.getCharactersList(page)
            response.results.map { CharactersMapper.map(it) }
            //emit(Results.Success(response))
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
        val response = rickAndMortyApi.getCharacterDetail(id)
        return response.



    }


}