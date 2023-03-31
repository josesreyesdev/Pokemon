package com.devjsr.pokemonapirest.modelApi

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
    val is_hidden: Boolean,
    val slot: Int
)

data class PokemonAbilityInfo(
    val name: String,
    val url: String
)

data class PokemonSprites(
    val front_default: String?,
    val front_shiny: String?,
    val back_default: String?,
    val back_shiny: String?
)