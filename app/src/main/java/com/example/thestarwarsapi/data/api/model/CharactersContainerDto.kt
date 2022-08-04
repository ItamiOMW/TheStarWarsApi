package com.example.thestarwarsapi.data.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CharactersContainerDto(
    @SerializedName("count")
    @Expose
    val count: Int,
    @SerializedName("next")
    @Expose
    val next: String,
    @SerializedName("previous")
    @Expose
    val previous: Any,
    @SerializedName("results")
    @Expose
    val charactersDto: List<CharacterDto>
)