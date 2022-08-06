package com.example.thestarwarsapi.presentation.search_screen

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.thestarwarsapi.R
import com.example.thestarwarsapi.StarWarsApp
import com.example.thestarwarsapi.databinding.FragmentSearchBinding
import com.example.thestarwarsapi.domain.model.Character
import com.example.thestarwarsapi.presentation.detail_screen.DetailFragment
import com.example.thestarwarsapi.presentation.item_adapter.CharacterAdapter
import com.example.thestarwarsapi.presentation.viewmodel_factory.ViewModelFactory
import javax.inject.Inject


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding: FragmentSearchBinding
        get() = _binding ?: throw RuntimeException("FragmentSearchBinding is null")

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val component by lazy {
        (requireActivity().application as StarWarsApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    private val mainAdapter by lazy {
        CharacterAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[SearchViewModel::class.java]
        setupRV()
        setupSearchView()
        observeLoading()
        viewModel.updateList(null)
        Log.d("TEST_BUG", "onViewCreated")
    }

    private fun setupSearchView() {
        with(binding.viewSearch) {
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

    private fun observeLoading() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            when (it) {
                true -> binding.progressBar.visibility = View.VISIBLE
                false -> binding.progressBar.visibility = View.GONE
            }
        }
    }

    private fun setupRV() {
        binding.rvCharacters.adapter = mainAdapter
        viewModel.listCharacters.observe(viewLifecycleOwner) {
            mainAdapter.submitList(it)
        }
        with(mainAdapter) {
            onItemClicked = {
                launchDetailFragment(it)
            }
            onItemLongClicked = {
                viewModel.changeFavorite(it)
                Toast.makeText(requireContext(), "Changed", Toast.LENGTH_SHORT).show()
            }
            onReachEndListener = {
                viewModel.increasePage()
            }
        }
    }

    private fun launchDetailFragment(character: Character) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, DetailFragment.getInstance(character))
            .addToBackStack(null)
            .commit()
    }

    companion object {

        fun getInstance(): SearchFragment {
            return SearchFragment()
        }
    }
}