package com.example.thestarwarsapi.presentation.detail_screen

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thestarwarsapi.R
import com.example.thestarwarsapi.domain.model.Character
import com.example.thestarwarsapi.domain.usecases.ChangeFavoriteUseCase
import com.example.thestarwarsapi.domain.usecases.GetAllFavoritesUseCase
import com.example.thestarwarsapi.domain.usecases.SearchCharacterUseCase
import com.example.thestarwarsapi.domain.usecases.SearchFavoriteUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    val application: Application,
    private val changeFavoriteUseCase: ChangeFavoriteUseCase,
    private val searchFavoriteUseCase: SearchFavoriteUseCase
) : ViewModel() {

    private lateinit var character: Character

    private val _isFavoriteText = MutableLiveData<String>()
    val isFavoriteText: LiveData<String>
        get() = _isFavoriteText

    fun sendCharacter(character: Character) {
        this.character = character
        setIsFavoriteText()
    }

    fun changeFavorite() {
        viewModelScope.launch {
            changeFavoriteUseCase.invoke(character)
            setIsFavoriteText()
        }
    }

    private fun setIsFavoriteText() {
        viewModelScope.launch {
            val character = searchFavoriteUseCase.invoke(character.name)
            if (character.isEmpty()) {
                _isFavoriteText.value = application.getString(R.string.add_to_favorite)
            } else {
                _isFavoriteText.value = application.getString(R.string.delete_from_favorite)
            }
        }
    }
}