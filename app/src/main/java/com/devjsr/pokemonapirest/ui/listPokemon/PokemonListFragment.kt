package com.devjsr.pokemonapirest.ui.listPokemon

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import com.devjsr.pokemonapirest.databinding.FragmentPokemonListBinding
import com.devjsr.pokemonapirest.ui.listPokemon.adapterRecyclerView.PokemonListAdapter
import com.devjsr.pokemonapirest.ui.viewModel.PokemonViewModel

private const val TAG = "PokemonListFragment"
class PokemonListFragment : Fragment() {

    private var _binding: FragmentPokemonListBinding? = null
    private val binding get() = _binding!!

    private val sharedViewModel: PokemonViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPokemonListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val slidingPaneLayout = binding.slidingPaneLayout
        slidingPaneLayout.lockMode = SlidingPaneLayout.LOCK_MODE_LOCKED
        // Connect the SlidingPaneLayout to the system back button.
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            PokemonListOnBackPressedCallback(slidingPaneLayout)
        )

        // Initialize the adapter and set it to the RecyclerView.
        val adapter = PokemonListAdapter {
            sharedViewModel.updateCurrentPokemon(it)
            binding.slidingPaneLayout.openPane()
        }

        binding.recyclerView.adapter = adapter

        adapter.submitList(sharedViewModel.pokemonList.value)
        Log.i(TAG, "Lista Pokemones2: ${sharedViewModel.pokemonList.value}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

class PokemonListOnBackPressedCallback(
    private val slidingPaneLayout: SlidingPaneLayout
) : OnBackPressedCallback(
    slidingPaneLayout.isSlideable && slidingPaneLayout.isOpen
), SlidingPaneLayout.PanelSlideListener {

    init {
        slidingPaneLayout.addPanelSlideListener(this)
    }

    override fun handleOnBackPressed() {
        // Return to the list pane when the system back button is pressed.
        slidingPaneLayout.closePane()
    }

    override fun onPanelSlide(panel: View, slideOffset: Float) {}

    override fun onPanelOpened(panel: View) {
        // Intercept the system back button when the detail pane becomes visible.
        isEnabled = true
    }

    override fun onPanelClosed(panel: View) {
        // Disable intercepting the system back button when the user returns to the
        // list pane.
        isEnabled = false
    }
}