package com.poncegamez.ricknmortyapp.models

import java.io.Serializable

data class Characters(
    val id: Int,
    val name: String,
    val species: String,
    val image: String
) : Serializable
