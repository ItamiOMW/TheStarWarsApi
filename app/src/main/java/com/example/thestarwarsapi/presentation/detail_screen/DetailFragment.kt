package com.example.thestarwarsapi.presentation.detail_screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.thestarwarsapi.R
import com.example.thestarwarsapi.StarWarsApp
import com.example.thestarwarsapi.databinding.FragmentDetailBinding
import com.example.thestarwarsapi.databinding.FragmentFavoritesBinding
import com.example.thestarwarsapi.domain.model.Character
import com.example.thestarwarsapi.presentation.favorites_screen.FavoritesViewModel
import com.example.thestarwarsapi.presentation.viewmodel_factory.ViewModelFactory
import javax.inject.Inject

class DetailFragment : Fragment() {


    private var _binding: FragmentDetailBinding? = null
    private val binding: FragmentDetailBinding
        get() = _binding ?: throw RuntimeException("FragmentDetailBinding is null")

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val component by lazy {
        (requireActivity().application as StarWarsApp).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        parseArgs()
    }

    private lateinit var character: Character

    private fun parseArgs() {
        val args = requireArguments()
        character = args.getParcelable<Character>(CHARACTER_KEY) as Character
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]
        setObserver()
        viewModel.sendCharacter(character)
        setCharacterDetailInfo()
        setOnChangeFavoriteClickListener()
    }

    private fun setOnChangeFavoriteClickListener() {
        binding.tvChangeFavorite.setOnClickListener {
            viewModel.changeFavorite()
        }
    }

    private fun setObserver() {
        viewModel.isFavoriteText.observe(viewLifecycleOwner) {
            binding.tvChangeFavorite.text = it
        }
    }

    private fun setCharacterDetailInfo() {
        val application = requireActivity().application
        with(binding) {
            tvFullName.text = String.format(application.getString(R.string.full_name), character.name)
            tvHeight.text = String.format(application.getString(R.string.height), character.height)
            tvMass.text = String.format(application.getString(R.string.mass), character.mass)
            tvGender.text = String.format(application.getString(R.string.gender), character.gender)
            tvBirthYear.text = String.format(application.getString(R.string.birth_year), character.birthYear)
            tvEyeColor.text = String.format(application.getString(R.string.eye_color), character.eyeColor)
            tvHairColor.text = String.format(application.getString(R.string.hair_color), character.hairColor)
        }

    }

    companion object {

        private const val CHARACTER_KEY = "character_key"

        fun getInstance(character: Character): DetailFragment {
            return DetailFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(CHARACTER_KEY, character)
                }
            }
        }
    }

}