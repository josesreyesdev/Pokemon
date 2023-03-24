package com.devjsr.pokemonapirest.ui.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devjsr.pokemonapirest.modelApi.PokeApiResponse
import com.devjsr.pokemonapirest.modelApi.PokeResult
import com.devjsr.pokemonapirest.service.network.PokemonApi
import kotlinx.coroutines.launch
import retrofit2.Response

class PokemonViewModel: ViewModel() {

    private val _pokemonList = MutableLiveData<List<PokeResult>>()
    val pokemonList: LiveData<List<PokeResult>> = _pokemonList

    private val _currentPokemon = MutableLiveData<PokeResult>()
    val currentPokemon: LiveData<PokeResult> = _currentPokemon

    init {
        getPokemonList()
        _currentPokemon.value = _pokemonList.value?.get(0)
    }

    private fun getPokemonList() {
        Log.d("ViewModel", "Get Pokemon List1: ${pokemonList.value}")
        viewModelScope.launch {
            try {
                val listResult = PokemonApi.retrofitService.getPokemonList(100, 0)
                _pokemonList.value = listResult.body()?.results
                Log.d("ViewModel", "Pokemon Result: ${_pokemonList.value}")

            } catch (e: Exception) {
                Log.d("ViewModel", "Error to load data of internet ${e.message}")
                _pokemonList.value = listOf()
            }
        }
        Log.d("ViewModel", "Get Pokemon List2: ${pokemonList.value}")
    }

    fun updateCurrentPokemon( pokemon: PokeResult) {
        _currentPokemon.value = pokemon
    }
}
