package com.example.thestarwarsapi.data.api

import com.example.thestarwarsapi.data.api.model.CharacterDto
import com.example.thestarwarsapi.data.api.model.CharactersContainerDto
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(END_POINT_PEOPLE)
    suspend fun getAllCharacters(
        @Query(QUERY_PAGE_PARAM) page: String
    ): Response<CharactersContainerDto>

    @GET(END_POINT_PEOPLE)
    suspend fun search(
        @Query(QUERY_SEARCH_PARAM) name: String
    ): Response<CharactersContainerDto>


    companion object {

        //QUERY PARAMS
        private const val QUERY_PAGE_PARAM = "page"
        private const val QUERY_SEARCH_PARAM = "search"

        //END POINTS
        private const val END_POINT_PEOPLE = "people"

        //BASE URL
        const val BASE_URL = "https://swapi.dev/api/"

        //DOCUMENTATION = https://swapi.dev/documentation#json
    }
}