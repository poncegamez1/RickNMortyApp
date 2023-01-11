package com.poncegamez.ricknmortyapp.mappers

import com.poncegamez.ricknmortyapp.dto.Result
import com.poncegamez.ricknmortyapp.models.Characters

object CharactersMapper {

    fun map(result: Result): Characters{
        return Characters(
            id = result.id,
            name = result.name,
            species = result.species,
            image = result.image
        )
    }

}