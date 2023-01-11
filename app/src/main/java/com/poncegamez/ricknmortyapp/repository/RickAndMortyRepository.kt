package com.poncegamez.ricknmortyapp.repository

import com.poncegamez.ricknmortyapp.models.CharacterDetail
import com.poncegamez.ricknmortyapp.models.Characters
import com.poncegamez.ricknmortyapp.result.Results
import kotlinx.coroutines.flow.Flow

interface RickAndMortyRepository {

    fun getCharactersList(page: Int): Flow<Results<List<Characters>>>
    suspend fun getCharacterDetail(id: Int): Results<CharacterDetail>

}