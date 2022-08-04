package com.example.thestarwarsapi.presentation.search_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thestarwarsapi.domain.model.Character
import com.example.thestarwarsapi.domain.usecases.ChangeFavoriteUseCase
import com.example.thestarwarsapi.domain.usecases.GetAllCharactersUseCase
import com.example.thestarwarsapi.domain.usecases.SearchCharacterUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val getAllCharactersUseCase: GetAllCharactersUseCase,
    private val searchUseCase: SearchCharacterUseCase,
    private val changeUseCase: ChangeFavoriteUseCase
) : ViewModel() {

    private var page = 1

    private val _listCharacters = MutableLiveData<List<Character>>()
    val listCharacters: LiveData<List<Character>>
        get() = _listCharacters

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

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
            changeUseCase.invoke(character)
        }
    }

    fun increasePage() {
        page += 1
        getCharacters()
    }

    private fun search(name: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _listCharacters.value = searchUseCase.invoke(name)
            _isLoading.value = false
        }
    }

    private fun getCharacters() {
        viewModelScope.launch {
            _isLoading.value = true
            val mutableList = mutableListOf<Character>()
            _listCharacters.value?.toMutableList()?.let { mutableList.addAll(it) }
            getAllCharactersUseCase.invoke(page)?.let { mutableList.addAll(it) }
            _listCharacters.value = mutableList.toList()
            _isLoading.value = false
        }
    }
}