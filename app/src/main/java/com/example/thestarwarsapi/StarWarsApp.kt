package com.example.thestarwarsapi

import android.app.Application
import com.example.thestarwarsapi.di.DaggerAppComponent

class StarWarsApp: Application() {

    val component by lazy {
        DaggerAppComponent.factory().create(this)
    }
}