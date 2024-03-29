package com.poncegamez.ricknmortyapp.repository

import com.poncegamez.ricknmortyapp.models.CharacterDetail
import com.poncegamez.ricknmortyapp.models.Characters
import com.poncegamez.ricknmortyapp.result.Results
import kotlinx.coroutines.flow.Flow

interface RickAndMortyRepository {

    suspend fun getCharacterDetail(id: Int): Results<CharacterDetail>
    suspend fun searchCharacters(query: String?, page: Int): Results<List<Characters>>
}