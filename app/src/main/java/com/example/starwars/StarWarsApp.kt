package com.example.starwars

import android.app.Application
import com.example.starwars.di.networkModule
import com.example.starwars.di.repositoryModule
import com.example.starwars.di.useCaseModule
import com.example.starwars.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class StarWarsApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@StarWarsApp)
            modules(
                arrayListOf(
                    networkModule, repositoryModule, useCaseModule, viewModelModule
                )
            )
        }
    }
}