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

const val TAG = "PokeList"

class PokemonViewModel : ViewModel() {

    private val _pokemonList = MutableLiveData<List<PokemonListItem>>()
    val pokemonList: LiveData<List<PokemonListItem>> = _pokemonList

    private val _currentPokemon = MutableLiveData<PokemonListItem>()

    private val _pokemon = MutableLiveData<Pokemon>()
    val pokemon: LiveData<Pokemon> = _pokemon

    init {
        getPokemonList()
        _currentPokemon.value = _pokemonList.value?.get(0)
        _currentPokemon.value?.let { pokemon(it.name) }
    }

    fun updateCurrentPokemon(pokemon: PokemonListItem) {
        _currentPokemon.value = pokemon
        _currentPokemon.value?.let { pokemon(it.name) }
    }

    private fun getPokemonList() {

        viewModelScope.launch {
            try {
                val response = PokemonApi.retrofitService.getPokemonList()
                val pokeList = response.body()?.results ?: emptyList()
                _pokemonList.value = pokeList

            } catch (e: Exception) {
                Log.d(TAG, "Error to load list pokemon ${e.message}")
                _pokemonList.value = listOf()
            }
        }
    }

    private fun pokemon(pokeName: String) {

        viewModelScope.launch {
            try {
                val pokemonResponse = PokemonApi.retrofitService.getPokemonByName(pokeName)
                _pokemon.value = pokemonResponse.body()
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
