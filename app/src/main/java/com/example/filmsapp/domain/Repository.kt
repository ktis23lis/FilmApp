package com.example.filmsapp.domain


import java.util.concurrent.Executor

interface Repository {

    fun  getFilm(executor : Executor, callback: (result : RepositoryResult<Film>) -> Unit)
    fun  getCategory(executor : Executor, callback: (result : RepositoryResult<List<Category>>) -> Unit)
    fun  getPopular() : Array<Film>
    fun  getNowPlaying() : Array<Film>
    fun  getTopRated() : Array<Film>
    fun  getUpcoming() : Array<Film>



}