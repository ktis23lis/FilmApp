package com.example.filmsapp.domain


import java.util.concurrent.Executor

interface Repository {

    fun  getFilm(executor : Executor, callback: (result : RepositoryResult<Film>) -> Unit)
    fun  getCategory(executor : Executor, callback: (result : RepositoryResult<List<Category>>) -> Unit)
    fun  getPopular() : ArrayList<Film>
    fun  getNowPlaying() : ArrayList<Film>
    fun  getTopRated() : ArrayList<Film>
    fun  getUpcoming() : ArrayList<Film>



}