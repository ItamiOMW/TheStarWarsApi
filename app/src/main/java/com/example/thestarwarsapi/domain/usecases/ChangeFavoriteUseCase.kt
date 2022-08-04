package com.example.thestarwarsapi.domain.usecases

import com.example.thestarwarsapi.domain.model.Character
import com.example.thestarwarsapi.domain.repository.StarRepository
import javax.inject.Inject

class ChangeFavoriteUseCase @Inject constructor (private val repository: StarRepository) {

    suspend operator fun invoke(character: Character) = repository.changeFavorite(character)

}