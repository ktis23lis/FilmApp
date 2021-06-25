package com.example.filmsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.filmsapp.di.App
import com.example.filmsapp.domain.Repository
import com.example.filmsapp.ui.router.MainRouter
import com.example.filmsapp.ui.router.RouterHolder
import javax.inject.Inject

class MainActivity : AppCompatActivity(R.layout.main_activity), RouterHolder {

    override val router =  MainRouter(this)

    @Inject
    lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as? App)?.appComponent?.inject(this)

        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
                   router.openList()
        }
    }


}
