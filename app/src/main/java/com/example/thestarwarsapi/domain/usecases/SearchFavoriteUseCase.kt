package com.example.thestarwarsapi.domain.usecases

import com.example.thestarwarsapi.domain.repository.StarRepository
import javax.inject.Inject

class SearchFavoriteUseCase @Inject constructor(private val repository: StarRepository)  {

    suspend operator fun invoke(name: String) = repository.searchFavoriteCharacter(name)

}