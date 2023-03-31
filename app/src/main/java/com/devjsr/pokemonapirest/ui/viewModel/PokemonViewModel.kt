package com.devjsr.pokemonapirest.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devjsr.pokemonapirest.modelApi.Pokemon
import com.devjsr.pokemonapirest.modelApi.PokemonListItem
import com.devjsr.pokemonapirest.modelApi.PokemonSprites
import com.devjsr.pokemonapirest.service.network.PokemonApi
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

const val TAG = "ViewModel"
class PokemonViewModel: ViewModel() {

    private val _pokemonList = MutableLiveData<List<PokemonListItem>>()
    val pokemonList: LiveData<List<PokemonListItem>> = _pokemonList

    private val _currentPokemon = MutableLiveData<PokemonListItem>()
    val currentPokemon = _currentPokemon as LiveData<PokemonListItem>

    private val _pokemon = MutableLiveData<Pokemon?>()
    val pokemon: LiveData<Pokemon> = _pokemon as LiveData<Pokemon>

    init {
        getPokemonList()
        _currentPokemon.value = _pokemonList.value?.get(0)
        _currentPokemon.value?.let { pokemon(it.name) }
    }

    fun updateCurrentPokemon( pokemon: PokemonListItem) {
        _currentPokemon.value = pokemon
    }

    private fun getPokemonList() {
        /*
        val pokeList = arrayListOf<PokemonListItem>()
        (0..20).forEach { _ ->
            pokeList.addAll(
                listOf(
                    PokemonListItem("beedrill", "https://pokeapi.co/api/v2/pokemon/15/"),
                    PokemonListItem("pidgey", "https://pokeapi.co/api/v2/pokemon/16/"),
                    PokemonListItem("weedle", "https://pokeapi.co/api/v2/pokemon/13/")
                )
            )
        }
        _pokemonList.value = pokeList */

        viewModelScope.launch {
            try {
                val response = PokemonApi.retrofitService.getPokemonList()
                if (response.isSuccessful) {
                    val pokeList = response.body()?.results ?: emptyList()
                    _pokemonList.value = pokeList
                    Log.d(TAG, "Pokemon List Result: ${_pokemonList.value}")
                }

            } catch (e: Exception) {
                Log.d(TAG, "Error to load data of internet ${e.message}")
                _pokemonList.value = listOf()
            }
        }
    }

    private fun pokemon(pokeName: String) {

        viewModelScope.launch {
            try {
                val pokemonResponse = PokemonApi.retrofitService.getPokemonByName(pokeName)
                _pokemon.value = pokemonResponse.awaitResponse().body()
                Log.d(TAG, "Pokemon: ${_pokemon.value}")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i(TAG, "Error load data of pokemon ${e.message}")
                _pokemon.value = Pokemon(
                    0,
                    "No Pokemon",
                    0,
                    0,
                    listOf(),
                    listOf(),
                    PokemonSprites(
                        "No link",
                        "No link",
                        "No link",
                        "No link"
                    )
                )
            }
        }
    }
}
