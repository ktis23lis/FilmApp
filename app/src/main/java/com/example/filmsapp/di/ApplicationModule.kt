package com.example.filmsapp.di

import android.app.Application
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val app: Application) {

    @Provides
    fun providesApplication() : Application = app
}