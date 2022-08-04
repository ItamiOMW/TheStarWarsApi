package com.example.thestarwarsapi.data.repository_impl

import android.widget.Toast
import com.example.thestarwarsapi.data.api.ApiService
import com.example.thestarwarsapi.data.api.model.CharactersContainerDto
import com.example.thestarwarsapi.data.database.CharacterDao
import com.example.thestarwarsapi.data.mapper.CharacterMapper
import com.example.thestarwarsapi.domain.model.Character
import com.example.thestarwarsapi.domain.repository.StarRepository
import retrofit2.Response
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mapper: CharacterMapper,
    private val characterDao: CharacterDao
) : StarRepository {

    override suspend fun changeFavorite(character: Character) {
        when (characterDao.getFavoriteByName(character.name)) {
            null -> characterDao.insertCharacter(mapper.mapEntityToDb(character))
            else -> characterDao.deleteFavorite(character.name)
        }
    }

    override suspend fun searchFavoriteCharacter(name: String) =
        mapper.mapDbListToEntityList(
            characterDao.getAllFavorites()
                .filter { it.name.lowercase().contains(name.lowercase()) })


    override suspend fun getAllFavorites(): List<Character> =
        mapper.mapDbListToEntityList(characterDao.getAllFavorites())


    override suspend fun searchCharacter(name: String): List<Character>? {
        val responseAllCharacters = getResponseSearchCharacters(name)
        return when (responseAllCharacters.isSuccessful) {
            true -> responseAllCharacters.body()?.charactersDto?.let {
                mapper.mapDtoListToEntityList(it)
            }
            false -> null
        }
    }

    override suspend fun getAllCharacters(page: Int): List<Character>? {
        val responseAllCharacters = getResponseAllCharacters(page.toString())
        return when (responseAllCharacters.isSuccessful) {
            true -> responseAllCharacters.body()?.charactersDto?.let {
                mapper.mapDtoListToEntityList(
                    it
                )
            }
            false -> null
        }
    }

    private suspend fun getResponseSearchCharacters(name: String): Response<CharactersContainerDto> {
        return try {
            apiService.search(name)
        } catch (e: Exception) {
            getResponseSearchCharacters(name)
        }
    }

    private suspend fun getResponseAllCharacters(page: String): Response<CharactersContainerDto> {
        return try {
            apiService.getAllCharacters(
                page
            )
        } catch (e: Exception) {
            getResponseAllCharacters(page)
        }
    }
}