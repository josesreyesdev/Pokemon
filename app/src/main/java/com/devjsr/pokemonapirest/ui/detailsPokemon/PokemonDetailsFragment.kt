package com.devjsr.pokemonapirest.ui.detailsPokemon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import coil.load
import com.devjsr.pokemonapirest.R
import com.devjsr.pokemonapirest.databinding.FragmentPokemonDetailsBinding
import com.devjsr.pokemonapirest.ui.viewModel.PokemonViewModel


class PokemonDetailsFragment : Fragment() {

    private val sharedViewModel: PokemonViewModel by activityViewModels()

    private var _binding : FragmentPokemonDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPokemonDetailsBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.pokemon.observe(this.viewLifecycleOwner) { pokemonSelected ->
            binding.apply {
                nameDetail.text = pokemonSelected.name
                pokemonImageDetail.load(pokemonSelected.sprites.other.officialArtwork.frontDefault)
                newsTitleDetail.text = getString(R.string.height, pokemonSelected.height.toString())
                newsDetail.text = getString(R.string.weight, pokemonSelected.weight.toString())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}