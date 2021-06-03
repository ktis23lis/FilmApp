package com.example.filmsapp.domain

import android.os.Handler
import android.os.Looper
import com.example.filmsapp.R
import java.lang.RuntimeException
import java.util.concurrent.Executor
import kotlin.random.Random


class RepositoryImp : Repository {


    private val mainThreadHandler = Handler(Looper.getMainLooper())

    override fun getCategory(
            executor: Executor,
            callback: (result: RepositoryResult<List<Category>>) -> Unit
    ) {
        executor.execute {
            Thread.sleep(2000)
            val isEverythingAllright = Random.nextBoolean()
            if (isEverythingAllright) {
                val result = listOf<Category>(
                        Category("a",(Direction.POPULAR)),
                        Category("b",(Direction.NOW_PLAYING)),
                        Category("c",(Direction.TOP_RATED)),
                        Category("d",(Direction.UPCOMING)),
                )
                mainThreadHandler.post {
                    callback(
                            Success(result)
                    )
                }
            } else {
                mainThreadHandler.post {
                    callback(
                            Error<List<Category>>(RuntimeException(" It is not film!"))
                    )
                }
            }
        }
    }

    override fun getFilm(
            executor: Executor,
            callback: (result: RepositoryResult<Film>) -> Unit
    ) {
        executor.execute {
            Thread.sleep(250)
//            val isEverythingAllright = Random.nextBoolean()
//            if (isEverythingAllright) {

            val film = Film(
                    Category("a",(Direction.POPULAR)),
                    R.drawable.tv,
                    "title",
                    "overview",
                    2,
                    "2020"
            )
            mainThreadHandler.post {
                callback(
                        Success(film)
                )
            }
        }
//        else {
//                mainThreadHandler.post {
//                    callback(
//                        Error<Film>(RuntimeException(" It is not film!"))
//                    )
//                }
    }



override fun getPopular() = getHardPopular()

override fun getNowPlaying() = getHardNowPlaying()

override fun getTopRated() = getHardTopRated()

override fun getUpcoming() = getHardUpcoming()



}


sealed class RepositoryResult<T>
data class Success<T>(val value:T) : RepositoryResult<T>()
data class Error<T>(val value: Throwable) : RepositoryResult<T>()


