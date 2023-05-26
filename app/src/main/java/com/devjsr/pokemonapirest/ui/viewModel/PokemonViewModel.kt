package com.devjsr.pokemonapirest.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devjsr.pokemonapirest.modelApi.Pokemon
import com.devjsr.pokemonapirest.modelApi.PokemonDreamWorld
import com.devjsr.pokemonapirest.modelApi.PokemonListItem
import com.devjsr.pokemonapirest.modelApi.PokemonOfficialArtwork
import com.devjsr.pokemonapirest.modelApi.PokemonOther
import com.devjsr.pokemonapirest.modelApi.PokemonSprites
import com.devjsr.pokemonapirest.service.network.PokemonApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val TAG = "PokeList"

class PokemonViewModel : ViewModel() {

    private val _pokemonList = MutableLiveData<List<PokemonListItem>>()
    val pokemonList: LiveData<List<PokemonListItem>> = _pokemonList

    private val currentPokemon = MutableLiveData<PokemonListItem>()

    private val _pokemon = MutableLiveData<Pokemon>()
    val pokemon: LiveData<Pokemon> = _pokemon

    init {
        getPokemonList()
    }

    private fun getPokemonList() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = PokemonApi.retrofitService.getPokemonList()
                val pokeList = response.body()?.results ?: emptyList()
                _pokemonList.postValue(pokeList)
                currentPokemon.postValue(pokeList[0])
                currentPokemon.value?.let { pokemon(it.name) }
            } catch (e: Exception) {
                _pokemonList.value = emptyList()
            }
        }
    }

    fun updateCurrentPokemon(pokemon: PokemonListItem) {
        currentPokemon.value = pokemon
        currentPokemon.value?.let { pokemon(it.name) }
    }

    private fun pokemon(pokemonName: String) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val pokemonResponse = PokemonApi.retrofitService.getPokemonByName(pokemonName)
                _pokemon.postValue(pokemonResponse.body())
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i(TAG, "Error load data of pokemon ${e.message}")
                _pokemon.postValue(
                    Pokemon(0, "Error load Pokemon", 0, 0,
                        listOf(),
                        listOf(),
                        PokemonSprites(
                            null,
                            null,
                            null,
                            null,
                            PokemonOther(
                                PokemonDreamWorld(null),
                                PokemonOfficialArtwork(null, null)
                            )
                        )
                    )
                )
            }
        }
    }
}
