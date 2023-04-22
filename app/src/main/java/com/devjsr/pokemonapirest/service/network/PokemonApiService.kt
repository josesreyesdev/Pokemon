package com.devjsr.pokemonapirest.service.network

import com.devjsr.pokemonapirest.modelApi.PokemonListResponse
import com.devjsr.pokemonapirest.modelApi.Pokemon
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApiService {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 1281,
        @Query("offset") offset: Int = 0
    ) : Response<PokemonListResponse>

    @GET( "pokemon/{name}")
    suspend fun getPokemonByName(
        @Path("name") name: String
    ) : Response<Pokemon>
}