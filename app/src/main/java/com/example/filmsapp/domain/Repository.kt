package com.example.filmsapp.domain


import java.util.concurrent.Executor

interface Repository {

    fun  getFilm(executor : Executor, callback: (result : RepositoryResult<Film>) -> Unit)
    fun  getCategory(executor : Executor, callback: (result : RepositoryResult<List<Category>>) -> Unit)

    fun  getPopular(executor : Executor, callback: (result : RepositoryResultApi<ArrayList<Film>>) -> Unit)
    fun  getNowPlaying(executor : Executor, callback: (result : RepositoryResultApi<ArrayList<Film>>) -> Unit)
    fun  getTopRated(executor : Executor, callback: (result : RepositoryResultApi<ArrayList<Film>>) -> Unit)
    fun  getUpcoming(executor : Executor, callback: (result : RepositoryResultApi<ArrayList<Film>>) -> Unit)



}