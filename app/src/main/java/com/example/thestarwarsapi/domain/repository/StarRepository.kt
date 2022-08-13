package com.example.thestarwarsapi.domain.repository

import androidx.lifecycle.LiveData
import com.example.thestarwarsapi.domain.model.Character

interface StarRepository {

    //WORK WITH FAVORITE
    suspend fun changeFavorite(character: Character): Boolean

    suspend fun getAllFavorites(): List<Character>

    suspend fun searchFavoriteCharacter(name: String): List<Character>

    //WORK WITH API
    suspend fun searchCharacter(name: String): List<Character>?

    suspend fun getAllCharacters(page: Int): List<Character>?
}