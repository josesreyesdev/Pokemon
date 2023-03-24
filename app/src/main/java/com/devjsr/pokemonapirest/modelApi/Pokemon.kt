package com.devjsr.pokemonapirest.modelApi

import com.squareup.moshi.Json

//Lista de pokemones
data class PokeApiResponse(
    val count : Int,
    val next: String,
    val results: List<PokeResult>
)


//Results of pokemones
data class PokeResult(
    val name: String,
    val url: String
)

//Details of pokemon
data class Pokemon(
    val id: Int,
    val name : String,
    val weight: Int,
    val height: Int,
    val sprites: Sprites
)

//images of pokemon
data class Sprites (
    @Json (name = "front_default") val frontDefault: String?,
    @Json (name = "front_shiny") val frontShiny: String?
)