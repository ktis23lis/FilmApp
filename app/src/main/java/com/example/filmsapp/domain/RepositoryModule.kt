package com.example.filmsapp.domain

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(): Repository = RepositoryApiImpl()
}