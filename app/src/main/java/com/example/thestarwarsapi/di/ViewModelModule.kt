package com.example.thestarwarsapi.di

import androidx.lifecycle.ViewModel
import com.example.thestarwarsapi.presentation.detail_screen.DetailViewModel
import com.example.thestarwarsapi.presentation.favorites_screen.FavoritesViewModel
import com.example.thestarwarsapi.presentation.search_screen.SearchViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModuleKey(SearchViewModel::class)
    fun bindsSearchViewModel(viewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModuleKey(FavoritesViewModel::class)
    fun bindsFavoritesViewModel(viewModel: FavoritesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModuleKey(DetailViewModel::class)
    fun bindsDetailViewModel(viewModel: DetailViewModel): ViewModel
}