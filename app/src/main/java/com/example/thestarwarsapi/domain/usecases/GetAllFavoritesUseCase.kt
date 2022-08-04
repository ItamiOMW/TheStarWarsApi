package com.example.thestarwarsapi.domain.usecases

import com.example.thestarwarsapi.domain.repository.StarRepository
import javax.inject.Inject

class GetAllFavoritesUseCase @Inject constructor (private val repository: StarRepository) {

    suspend operator fun invoke() = repository.getAllFavorites()

}