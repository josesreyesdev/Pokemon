package com.devjsr.pokemonapirest.ui.listPokemon.adapterRecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.devjsr.pokemonapirest.databinding.PokemonListItemBinding
import com.devjsr.pokemonapirest.modelApi.PokemonListItem

class PokemonListAdapter (private val onItemClicked: (PokemonListItem) -> Unit)
    : ListAdapter<PokemonListItem, PokemonListAdapter.PokemonListViewHolder> (DiffCallback) {

    // Representan cada vista de mi lista
    class PokemonListViewHolder(private var binding: PokemonListItemBinding)
        : RecyclerView.ViewHolder(binding.root)  {

        fun bind( pokemon: PokemonListItem) {
            binding.data = pokemon
            binding.executePendingBindings()
        }
    }

    //Crear nuevas interfaces de vista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListViewHolder {
        return PokemonListViewHolder(PokemonListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    //Reemplaza el contenido de una vista de elementos de lista, con una vista y su posicion
    override fun onBindViewHolder(holder: PokemonListViewHolder, position: Int) {
        val pokemon = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(pokemon)
        }
        holder.bind(pokemon)
    }

    //Objeto complementario para DiffCallback => para comparar dos objetos de tipo PokeResult
    companion object DiffCallback : DiffUtil.ItemCallback<PokemonListItem>() {

        //Diffutil llama a este metodo para decidir si dos objetos representan el mismo valor
        override fun areItemsTheSame(oldItem: PokemonListItem, newItem: PokemonListItem): Boolean {
            return oldItem.name == newItem.name
        }

        //DiffUtil llama a este metodo cuando desea verificar si dos elementos tienen los mismos datos.
        override fun areContentsTheSame(oldItem: PokemonListItem, newItem: PokemonListItem): Boolean {
            return oldItem.url == newItem.url
        }

    }

}