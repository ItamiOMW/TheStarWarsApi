package com.example.thestarwarsapi.data.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CharacterDto(
    @SerializedName("birth_year")
    @Expose
    val birth_year: String,
    @SerializedName("created")
    @Expose
    val created: String,
    @SerializedName("edited")
    @Expose
    val edited: String,
    @SerializedName("eye_color")
    @Expose
    val eye_color: String,
    @SerializedName("films")
    @Expose
    val films: List<String>,
    @SerializedName("gender")
    @Expose
    val gender: String,
    @SerializedName("hair_color")
    @Expose
    val hair_color: String,
    @SerializedName("height")
    @Expose
    val height: String,
    @SerializedName("homeworld")
    @Expose
    val homeworld: String,
    @SerializedName("mass")
    @Expose
    val mass: String,
    @SerializedName("name")
    @Expose
    val name: String,
    @SerializedName("skin_color")
    @Expose
    val skin_color: String,
    @SerializedName("species")
    @Expose
    val species: List<String>,
    @SerializedName("starships")
    @Expose
    val starships: List<String>,
    @SerializedName("url")
    @Expose
    val url: String,
    @SerializedName("vehicles")
    @Expose
    val vehicles: List<String>
)