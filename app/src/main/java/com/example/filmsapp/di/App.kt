package com.example.filmsapp.di
import android.app.Application

class App : Application() {

    val appComponent: AppComponent = DaggerAppComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
}