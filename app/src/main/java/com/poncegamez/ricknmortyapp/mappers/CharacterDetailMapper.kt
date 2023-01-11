package com.poncegamez.ricknmortyapp.mappers

import com.poncegamez.ricknmortyapp.dto.CharacterDto
import com.poncegamez.ricknmortyapp.models.CharacterDetail

object CharacterDetailMapper {

    fun map(characterDto: CharacterDto): CharacterDetail{
        return CharacterDetail(
            id = characterDto.id,
            name = characterDto.name,
            status = characterDto.status,
            species = characterDto.species,
            type = characterDto.type,
            gender = characterDto.gender,
            origin = characterDto.origin,
            location = characterDto.location,
            image = characterDto.image
        )
    }

}