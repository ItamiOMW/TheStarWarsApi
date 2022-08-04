package com.example.thestarwarsapi.di

import android.app.Application
import com.example.thestarwarsapi.presentation.detail_screen.DetailFragment
import com.example.thestarwarsapi.presentation.favorites_screen.FavoritesFragment
import com.example.thestarwarsapi.presentation.search_screen.SearchFragment
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [ViewModelModule::class, DataModule::class])
interface AppComponent {

    fun inject(searchFragment: SearchFragment)

    fun inject(detailFragment: DetailFragment)

    fun inject(favoritesFragment: FavoritesFragment)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): AppComponent
    }
}