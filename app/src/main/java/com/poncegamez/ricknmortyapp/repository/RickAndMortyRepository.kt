package com.poncegamez.ricknmortyapp.repository

import com.poncegamez.ricknmortyapp.models.CharacterDetail
import com.poncegamez.ricknmortyapp.models.Characters
import com.poncegamez.ricknmortyapp.result.Results
import kotlinx.coroutines.flow.Flow

interface RickAndMortyRepository {

    suspend fun getCharactersList(page: Int): Results<List<Characters>>
    suspend fun getCharacterDetail(id: Int): Results<CharacterDetail>

}