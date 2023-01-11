package com.poncegamez.ricknmortyapp.models

import com.poncegamez.ricknmortyapp.dto.Location
import com.poncegamez.ricknmortyapp.dto.Origin
import java.io.Serializable


data class CharacterDetail(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Origin,
    val location: Location,
    val image: String
) : Serializable
