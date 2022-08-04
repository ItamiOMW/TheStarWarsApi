package com.example.thestarwarsapi.di

import androidx.lifecycle.ViewModel
import dagger.MapKey
import kotlin.reflect.KClass

@MapKey
@Retention(AnnotationRetention.RUNTIME)
annotation class ViewModuleKey(val value: KClass<out ViewModel>)
