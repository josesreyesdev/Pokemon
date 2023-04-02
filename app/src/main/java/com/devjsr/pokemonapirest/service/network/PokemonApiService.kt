package com.devjsr.pokemonapirest.service.network

import com.devjsr.pokemonapirest.modelApi.PokemonListResponse
import com.devjsr.pokemonapirest.modelApi.Pokemon
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

private const val BASE_URL_POKEMON = "https://pokeapi.co/api/v2/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit= Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL_POKEMON)
    .build()

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

object PokemonApi {
    val retrofitService : PokemonApiService by lazy {
        retrofit.create(PokemonApiService::class.java)
    }
}