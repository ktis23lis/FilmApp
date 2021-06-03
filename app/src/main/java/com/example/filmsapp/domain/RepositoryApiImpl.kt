package com.example.filmsapp.domain

import android.os.Handler
import android.os.Looper
import com.example.filmsapp.R
import com.example.filmsapp.domain.responses.OpenFilm
import com.example.filmsapp.domain.responses.OpenResult
import com.google.gson.Gson
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executor

class RepositoryApiImpl: Repository {

    private val mainThreadHandler = Handler(Looper.getMainLooper())
    private val gson = Gson()
    private var filmsData = ArrayList<Film>()




    override fun getCategory(executor: Executor, callback: (result: RepositoryResult<List<Category>>) -> Unit) {

        val result = listOf<Category>(
                Category("a", (Direction.POPULAR)),
                Category("b", (Direction.NOW_PLAYING)),
                Category("c", (Direction.TOP_RATED)),
                Category("d", (Direction.UPCOMING)),
        )

        callback.invoke(Success(result))
    }

    override fun getFilm(executor: Executor, callback: (result: RepositoryResult<Film>) -> Unit) {
        val url =
                URL("https://api.themoviedb.org/3/movie/popular?api_key=1d8addf97a9886e6124b23d2897be981")
        val connection = url.openConnection() as HttpURLConnection

        try {
            with(connection) {
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
                mainThreadHandler.post {
                    callback.invoke(Success(film))
                }

            }
        } catch (e: Exception) {
            mainThreadHandler.post {
                callback.invoke(Error(e))
            }
        } finally {
            connection.disconnect()
        }


    }

    override fun getPopular(): ArrayList<Film> {
        val url =
                URL("https://api.themoviedb.org/3/movie/popular?api_key=1d8addf97a9886e6124b23d2897be981")
        val connection = url.openConnection() as HttpURLConnection

        try {
            with(connection) {
                requestMethod = "GET"
                readTimeout = 30_000

                val response = gson.fromJson(
                        inputStream.bufferedReader(),
                        OpenResult::class.java
                )
                val array = response.results
                for (i in array){
                    val filmName = i.title
                    val filmOverview  = i.overview
                    val filmRating = i.rating
                    val filmDate = i.date

                    filmsData = arrayListOf(Film(Category("a", Direction.POPULAR), R.drawable.tv, filmName,
                    filmOverview, filmRating, filmDate))
                }
                mainThreadHandler.post {
                    SuccessApi(filmsData)
                }
            }
        } catch (e: Exception) {
            mainThreadHandler.post {

            }
        } finally {
            connection.disconnect()
        }
        return filmsData
    }

    override fun getNowPlaying(): ArrayList<Film> {
        val url =
                URL("https://api.themoviedb.org/3/movie/now_playing?api_key=1d8addf97a9886e6124b23d2897be981")
        val connection = url.openConnection() as HttpURLConnection

        try {
            with(connection) {
                requestMethod = "GET"
                readTimeout = 30_000

                val response = gson.fromJson(
                        inputStream.bufferedReader(),
                        OpenResult::class.java
                )
                val array = response.results
                for (i in array){
                    val filmName = i.title
                    val filmOverview  = i.overview
                    val filmRating = i.rating
                    val filmDate = i.date

                    filmsData = arrayListOf(Film(Category("b", Direction.NOW_PLAYING), R.drawable.tv, filmName,
                            filmOverview, filmRating, filmDate))
                }
                mainThreadHandler.post {
                    SuccessApi(filmsData)

                }
            }
        } catch (e: Exception) {
            mainThreadHandler.post {

            }

        } finally {
            connection.disconnect()
        }
        return filmsData
    }

    override fun getTopRated(): ArrayList<Film> {
        val url =
                URL("https://api.themoviedb.org/3/movie/top_rated?api_key=1d8addf97a9886e6124b23d2897be981")
        val connection = url.openConnection() as HttpURLConnection

        try {
            with(connection) {
                requestMethod = "GET"
                readTimeout = 30_000

                val response = gson.fromJson(
                        inputStream.bufferedReader(),
                        OpenResult::class.java
                )
                val array = response.results
                for (i in array){
                    val filmName = i.title
                    val filmOverview  = i.overview
                    val filmRating = i.rating
                    val filmDate = i.date

                    filmsData = arrayListOf(Film(Category("c", Direction.TOP_RATED), R.drawable.tv, filmName,
                            filmOverview, filmRating, filmDate))
                }
                mainThreadHandler.post {
                    SuccessApi(filmsData)
                }
            }
        } catch (e: Exception) {
            mainThreadHandler.post {

            }
        } finally {
            connection.disconnect()
        }
        return filmsData
    }

    override fun getUpcoming(): ArrayList<Film> {
        val url =
                URL("https://api.themoviedb.org/3/movie/upcoming?api_key=1d8addf97a9886e6124b23d2897be981")
        val connection = url.openConnection() as HttpURLConnection

        try {
            with(connection) {
                requestMethod = "GET"
                readTimeout = 30_000

                val response = gson.fromJson(
                        inputStream.bufferedReader(),
                        OpenResult::class.java
                )
                val array = response.results
                for (i in array){
                    val filmName = i.title
                    val filmOverview  = i.overview
                    val filmRating = i.rating
                    val filmDate = i.date

                    filmsData = arrayListOf(Film(Category("в", Direction.UPCOMING), R.drawable.tv, filmName,
                            filmOverview, filmRating, filmDate))
                }
                mainThreadHandler.post {
                    SuccessApi(filmsData)
                }
            }
        } catch (e: Exception) {
            mainThreadHandler.post {

            }
        } finally {
            connection.disconnect()
        }
        return filmsData
    }
}

sealed class RepositoryResultApi<T>
data class SuccessApi<Film>(val value: ArrayList<Film>) : RepositoryResultApi<Film>()
data class ErrorApi<T>(val value: Exception) : RepositoryResultApi<T>()