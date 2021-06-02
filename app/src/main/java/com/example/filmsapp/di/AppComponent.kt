package com.example.filmsapp.di

import com.example.filmsapp.MainActivity
import com.example.filmsapp.domain.RepositoryModule
import com.example.filmsapp.ui.details.FilmFragment
import com.example.filmsapp.ui.list.ListFragment
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [ApplicationModule::class, RepositoryModule::class])
interface AppComponent {

    fun inject(main: MainActivity)

    fun inject(main: ListFragment)
    fun inject(detailsFragment: FilmFragment)
}