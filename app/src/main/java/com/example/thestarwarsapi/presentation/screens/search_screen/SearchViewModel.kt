package com.example.thestarwarsapi.presentation.screens.search_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thestarwarsapi.domain.model.Character
import com.example.thestarwarsapi.domain.usecases.ChangeFavoriteUseCase
import com.example.thestarwarsapi.domain.usecases.GetAllCharactersUseCase
import com.example.thestarwarsapi.domain.usecases.SearchCharacterUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val getAllCharactersUseCase: GetAllCharactersUseCase,
    private val searchUseCase: SearchCharacterUseCase,
    private val changeUseCase: ChangeFavoriteUseCase
) : ViewModel() {

    private var page = 0

    private val _listCharacters = MutableLiveData<List<Character>>()
    val listCharacters: LiveData<List<Character>>
        get() = _listCharacters

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _isFavoriteChanged = MutableLiveData<Boolean>()
    val isFavoriteChanged: LiveData<Boolean>
        get() = _isFavoriteChanged

    fun updateList(name: String?) {
        viewModelScope.launch {
            when (name) {
                null -> getCharacters()
                else -> search(name)
            }
        }
    }

    fun changeFavorite(character: Character) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val value = changeUseCase.invoke(character)
                _isFavoriteChanged.postValue(value)
            }
        }
    }

    fun increasePage() {
        getCharacters()
    }

    private fun search(name: String) {
        viewModelScope.launch() {
            _isLoading.value = true
            withContext(Dispatchers.IO) {
                _listCharacters.postValue(searchUseCase.invoke(name))
            }
            _isLoading.value = false
        }
    }

    private fun getCharacters() {
        viewModelScope.launch {
            _isLoading.value = true
            val mutableList = mutableListOf<Character>()
            _listCharacters.value?.toMutableList()?.let { mutableList.addAll(it) }
            withContext(Dispatchers.IO) {
                getAllCharactersUseCase.invoke(++page)?.let { mutableList.addAll(it) }
            }
            _listCharacters.value = mutableList.toList()
            _isLoading.value = false
        }
    }
}