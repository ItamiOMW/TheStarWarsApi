package com.example.thestarwarsapi.di

import android.app.Application
import android.widget.Toast
import com.example.thestarwarsapi.data.api.ApiService
import com.example.thestarwarsapi.data.database.CharacterDao
import com.example.thestarwarsapi.data.database.StarWarsDb
import com.example.thestarwarsapi.data.repository_impl.RepositoryImpl
import com.example.thestarwarsapi.domain.repository.StarRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
interface DataModule {

    @Binds
    @AppScope
    fun bindRepository(repositoryImpl: RepositoryImpl): StarRepository

    companion object {

        @Provides
        @AppScope
        fun provideDao(
            application: Application
        ): CharacterDao {
            return StarWarsDb.getInstance(application).characterDao()
        }

        @Provides
        @AppScope
        fun provideApi(): ApiService {
            return Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)

        }
    }
}