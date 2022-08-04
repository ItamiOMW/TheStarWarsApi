package com.example.thestarwarsapi.presentation.favorites_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.thestarwarsapi.R
import com.example.thestarwarsapi.StarWarsApp
import com.example.thestarwarsapi.databinding.FragmentFavoritesBinding
import com.example.thestarwarsapi.domain.model.Character
import com.example.thestarwarsapi.presentation.detail_screen.DetailFragment
import com.example.thestarwarsapi.presentation.item_adapter.CharacterAdapter
import com.example.thestarwarsapi.presentation.viewmodel_factory.ViewModelFactory
import javax.inject.Inject


class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding: FragmentFavoritesBinding
        get() = _binding ?: throw RuntimeException("FragmentFavoritesBinding is null")

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val component by lazy {
        (requireActivity().application as StarWarsApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: FavoritesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[FavoritesViewModel::class.java]
        setupRV()
        setupSearchView()
        viewModel.updateList(null)
    }

    private val mainAdapter by lazy {
        CharacterAdapter()
    }

    private fun setupRV() {
        binding.rvCharacters.adapter = mainAdapter
        viewModel.favoriteList.observe(viewLifecycleOwner) {
            mainAdapter.submitList(it)
        }
        mainAdapter.onItemClicked = {
            launchDetailFragment(it)
        }
        mainAdapter.onItemLongClicked = {
            viewModel.changeFavorite(it)
        }
    }

    private fun launchDetailFragment(character: Character) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, DetailFragment.getInstance(character))
            .addToBackStack(null)
            .commit()
    }

    private fun setupSearchView() {
        with(binding.viewSearchFavorite) {
            clearFocus()
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.updateList(newText)
                    return true
                }
            })
        }

    }


    companion object {
        fun getInstance(): FavoritesFragment {
            return FavoritesFragment()
        }
    }
}