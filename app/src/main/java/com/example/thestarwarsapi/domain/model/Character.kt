package com.example.thestarwarsapi.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    val name: String,
    val height: String,
    val mass: String,
    val hairColor: String,
    val eyeColor: String,
    val birthYear: String,
    val gender: String,
    val films: String,
): Parcelable