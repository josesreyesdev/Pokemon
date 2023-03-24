package com.devjsr.pokemonapirest.service.network

import com.devjsr.pokemonapirest.modelApi.PokeApiResponse
import com.devjsr.pokemonapirest.modelApi.Pokemon
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
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

    @GET("pokemon/{id}")
    suspend fun getPokemonInfoById(@Path("id") id: Int) : Call<Pokemon>

    @GET("pokemon")
    suspend fun getPokemonList( @Query("limit") limit: Int = 100000, @Query("offset") offset: Int = 0) : Response<PokeApiResponse>

    @GET( "pokemon/{name}")
    suspend fun getPokemonInfoByName(
        @Path("name") name: String
    ) : Pokemon
}

object PokemonApi {
    val retrofitService : PokemonApiService by lazy {
        retrofit.create(PokemonApiService::class.java)
    }
}