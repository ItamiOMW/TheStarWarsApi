package com.example.thestarwarsapi.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterDao {

    @Insert()
    suspend fun insertCharacter(characterDbModel: CharacterDbModel)

    @Query("DELETE FROM favorite_characters WHERE name=:name")
    suspend fun deleteFavorite(name: String)

    @Query("SELECT * FROM favorite_characters")
    suspend fun getAllFavorites(): List<CharacterDbModel>

    @Query("SELECT * FROM favorite_characters WHERE name=:name")
    suspend fun getFavoriteByName(name: String): CharacterDbModel?
}