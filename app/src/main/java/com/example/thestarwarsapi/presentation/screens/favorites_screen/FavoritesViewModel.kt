package com.example.thestarwarsapi.presentation.screens.favorites_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thestarwarsapi.domain.model.Character
import com.example.thestarwarsapi.domain.usecases.ChangeFavoriteUseCase
import com.example.thestarwarsapi.domain.usecases.GetAllFavoritesUseCase
import com.example.thestarwarsapi.domain.usecases.SearchFavoriteUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(
    private val getAllFavoritesUseCase: GetAllFavoritesUseCase,
    private val changeFavoriteUseCase: ChangeFavoriteUseCase,
    private val searchFavoriteUseCase: SearchFavoriteUseCase
) : ViewModel() {

    private val _favoriteList = MutableLiveData<List<Character>>()
    val favoriteList: LiveData<List<Character>>
        get() = _favoriteList

    private val _isFavoriteChanged = MutableLiveData<Boolean>()
    val isFavoriteChanged: LiveData<Boolean>
        get() = _isFavoriteChanged


    fun changeFavorite(character: Character) {
        viewModelScope.launch {
            val value = changeFavoriteUseCase.invoke(character)
            _isFavoriteChanged.value = value
            updateList(null)
        }
    }

    fun updateList(name: String?) {
        viewModelScope.launch {
            _favoriteList.value = when (name) {
                null -> getAllFavoritesUseCase.invoke()
                else -> searchFavoriteUseCase.invoke(name)
            }
        }
    }
}