package com.example.filmsapp.domain

import android.os.Handler
import android.os.Looper
import com.example.filmsapp.R
import com.example.filmsapp.domain.responses.OpenFilm
import com.google.gson.Gson
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executor

class RepositoryApiImpl: Repository {

    private val mainThreadHandler = Handler(Looper.getMainLooper())
    private val gson = Gson()

    override fun getCategory(executor: Executor, callback: (result: RepositoryResult<List<Category>>) -> Unit) {

        val result = listOf<Category>(
                Category("a",(Direction.POPULAR)),
                Category("b",(Direction.NOW_PLAYING)),
                Category("c",(Direction.TOP_RATED)),
                Category("d",(Direction.UPCOMING)),
        )

        callback.invoke(Success(result))
    }

    override fun getFilm(executor: Executor, callback: (result: RepositoryResult<Film>) -> Unit) {
        val url =
                URL ("https://api.themoviedb.org/3/movie/popular?api_key=1d8addf97a9886e6124b23d2897be981")
        val connection = url.openConnection() as HttpURLConnection

        try {
            with(connection){
                requestMethod = "GET"
                readTimeout = 30_000

                val response = gson.fromJson(
                        inputStream.bufferedReader(),
                        OpenFilm::class.java
                )

                val film = Film(
                        Category("a", Direction.TOP_RATED),
                        R.drawable.tv,
                        response.title,
                        response.overview,
                        response.rating,
                        response.date
                )
                mainThreadHandler.post{
                    callback.invoke(Success(film))
                }

            }
        }catch (e : Exception){
        mainThreadHandler.post{
            callback.invoke(Error(e))
        }
        }finally {
            connection.disconnect()
        }



    }

    override fun getPopular(): Array<Film> {
        TODO("Not yet implemented")
    }

    override fun getNowPlaying(): Array<Film> {
        TODO("Not yet implemented")
    }

    override fun getTopRated(): Array<Film> {
        TODO("Not yet implemented")
    }

    override fun getUpcoming(): Array<Film> {
        TODO("Not yet implemented")
    }
}