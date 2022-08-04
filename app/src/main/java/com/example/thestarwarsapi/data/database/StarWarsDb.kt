package com.example.thestarwarsapi.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CharacterDbModel::class], exportSchema = false, version = 2)
abstract class StarWarsDb : RoomDatabase() {

    abstract fun characterDao(): CharacterDao

    companion object {

        private const val DB_NAME = "starWars.db"
        private val LOCKER = Any()
        private var INSTANCE: StarWarsDb? = null

        fun getInstance(application: Application): StarWarsDb {
            INSTANCE?.let {
                return it
            }
            synchronized(LOCKER) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    StarWarsDb::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = db
                return db
            }
        }
    }
}