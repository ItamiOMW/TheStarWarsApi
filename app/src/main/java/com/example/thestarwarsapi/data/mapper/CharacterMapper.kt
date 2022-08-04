package com.example.thestarwarsapi.data.mapper

import com.example.thestarwarsapi.data.api.model.CharacterDto
import com.example.thestarwarsapi.data.database.CharacterDbModel
import com.example.thestarwarsapi.domain.model.Character
import javax.inject.Inject

class CharacterMapper @Inject constructor() {

    fun mapDtoListToEntityList(list: List<CharacterDto>) = list.map {
        mapDtoToEntity(it)
    }

    private fun mapDtoToEntity(model: CharacterDto) = Character(
        name = model.name,
        height = model.height,
        mass = model.mass,
        hairColor = model.hair_color,
        eyeColor = model.eye_color,
        birthYear = model.birth_year,
        gender = model.gender,
        films = model.films.toString()
    )

    fun mapDbListToEntityList(list: List<CharacterDbModel>) = list.map {
        mapDbToEntity(it)
    }

    private fun mapDbToEntity(model: CharacterDbModel) = Character(
        name = model.name,
        height = model.height,
        mass = model.mass,
        hairColor = model.hairColor,
        eyeColor = model.eyeColor,
        birthYear = model.birthYear,
        gender = model.gender,
        films = model.films,
    )

    fun mapEntityToDb(model: Character) = CharacterDbModel(
        name = model.name,
        height = model.height,
        mass = model.mass,
        hairColor = model.hairColor,
        eyeColor = model.eyeColor,
        birthYear = model.birthYear,
        gender = model.gender,
        films = model.films
    )

}