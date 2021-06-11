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
        val result = listOf(
                Category("a", (Direction.POPULAR)),
                Category("b", (Direction.NOW_PLAYING)),
                Category("c", (Direction.TOP_RATED)),
                Category("d", (Direction.UPCOMING)),
        )
        callback.invoke(Success(result))
    }

    override fun getFilm(executor: Executor, callback: (result: RepositoryResult<Film>) -> Unit) {
        executor.execute {
            val url =
                    URL("https://api.themoviedb.org/3/movie/632357?api_key=1d8addf97a9886e6124b23d2897be981&language=en-US")
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
    }

    override fun getPopular(executor: Executor, callback: (RepositoryResultApi<List<Film>>) -> Unit) {
        executor.execute {
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
                        callback.invoke(SuccessApi(filmsData))
                    }
                }
            } catch (e: Exception) {
                mainThreadHandler.post {
                    callback.invoke(ErrorApi(e))

                }
            } finally {
                connection.disconnect()
            }
        }


    }

    override fun getNowPlaying(executor: Executor, callback: (result: RepositoryResultApi<List<Film>>) -> Unit) {
        executor.execute {

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
                        callback.invoke(SuccessApi(filmsData))

                    }
                }
            } catch (e: Exception) {
                mainThreadHandler.post {
                    callback.invoke(ErrorApi(e))
                }

            } finally {
                connection.disconnect()
            }

        }

    }

    override fun getTopRated(executor: Executor, callback: (result: RepositoryResultApi<List<Film>>) -> Unit) {
        executor.execute {
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
                        callback.invoke(SuccessApi(filmsData))
                    }
                }
            } catch (e: Exception) {
                mainThreadHandler.post {
                    callback.invoke(ErrorApi(e))
                }
            } finally {
                connection.disconnect()
            }

        }


    }

    override fun getUpcoming(executor: Executor, callback: (result: RepositoryResultApi<List<Film>>) -> Unit) {
        executor.execute {
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

                        filmsData = arrayListOf(Film(Category("a", Direction.POPULAR), R.drawable.tv, filmName,
                                filmOverview, filmRating, filmDate))
                    }
                    mainThreadHandler.post {
                        callback.invoke(SuccessApi(filmsData))
                    }
                }
            } catch (e: Exception) {
                mainThreadHandler.post {
                   e.printStackTrace()
                }
            } finally {
                connection.disconnect()
            }

        }
        }

}

sealed class RepositoryResultApi<T>
data class SuccessApi<T>(val value: T) : RepositoryResultApi<T>()
data class ErrorApi<T>(val value: Throwable) : RepositoryResultApi<T>()