package com.devjsr.pokemonapirest.modelApi

import com.squareup.moshi.Json

/* List Pokemons*/
data class PokemonListResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<PokemonListItem>
)

data class PokemonListItem(
    val name: String,
    val url: String
)

/* Pokemon */
data class Pokemon(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<PokemonType>,
    val abilities: List<PokemonAbility>,
    val sprites: PokemonSprites
)

data class PokemonType(
    val slot: Int,
    val type: PokemonTypeInfo
)

data class PokemonTypeInfo(
    val name: String,
    val url: String
)

data class PokemonAbility(
    val ability: PokemonAbilityInfo,
    @Json(name = "is_hidden") val isHidden: Boolean,
    val slot: Int
)

data class PokemonAbilityInfo(
    val name: String,
    val url: String
)

data class PokemonSprites(
    @Json(name = "front_default") val frontDefault: String?,
    @Json(name = "front_shiny") val frontShiny: String?,
    @Json(name = "back_default") val backDefault: String?,
    @Json(name = "back_shiny") val backShiny: String?,
    val other: PokemonOther
)

data class PokemonOther(
    @Json(name = "dream_world") val dreamWorld: PokemonDreamWorld,
    @Json(name = "official-artwork") val officialArtwork: PokemonOfficialArtwork
)

data class PokemonDreamWorld(
    @Json(name = "front_default") val frontDefault: String?
)

data class PokemonOfficialArtwork(
    @Json(name = "front_default") val frontDefault: String?,
    @Json(name = "front_shiny") val frontShiny: String?
)
