package com.example.filmsapp.ui.router

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.filmsapp.R
import com.example.filmsapp.domain.Film
import com.example.filmsapp.ui.details.FilmFragment
import com.example.filmsapp.ui.list.ListFragment

class MainRouter(private val activity: AppCompatActivity) {

    fun openList(){
        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.container, ListFragment())
            .commit()

    }

    fun openFilm(film : Film){
        val manager = activity.supportFragmentManager
        manager?.let { manager ->
            val bundle = Bundle().apply {
                putParcelable(FilmFragment.BUNDLE_EXTRA, film)
            }
            manager.beginTransaction()
                    .replace(R.id.container, FilmFragment.newInstance(bundle))
                    .addToBackStack("MainDetailsFilmFragment")
                    .commit()




        }

    }
}