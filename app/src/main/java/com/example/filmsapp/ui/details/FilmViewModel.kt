package com.example.filmsapp.ui.details

import android.app.Application
import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmsapp.R
import com.example.filmsapp.domain.*
import java.util.concurrent.Executors


class  FilmViewModel(

        private val app : Application,
        private val repository: Repository


//    private val resourceProvider: ResourceProvider,
//    private val repository: RepositoryImp
) : ViewModel() {

    private val executors = Executors.newSingleThreadExecutor()

    private val _loadingLiveData = MutableLiveData(false)
    private val _errorLiveData = MutableLiveData<String?>()
    private val _filmLiveData = MutableLiveData<FilmInfo?>()

    val loadingLiveData : LiveData<Boolean> = _loadingLiveData
    val errorLiveData : LiveData<String?> = _errorLiveData
    val filmLiveData : LiveData<FilmInfo?> = _filmLiveData

    fun fetchFilm() {
        _loadingLiveData.value = true
        repository.getFilm (executor = executors, callback = {
            when (it) {
                is Success -> {
                    val film = it.value
                    _filmLiveData.value =
                        FilmInfo(
                        Category("a", Direction.UPCOMING),
                        R.drawable.emoticon,
                       "A",
                        "a",
                        2.3f,
                        "a")
//                        FilmInfo(film.category,
//                            film.image,
//                            film.title,
//                            film.overview,
//                            film.rating,
//                            film.date,
//
//                        )
                    _errorLiveData.value = null
                }
                is Error -> {
                    _errorLiveData.value = app.getString(R.string.error)
                    _filmLiveData.value = null
                }
            }
            _loadingLiveData.value = false
        })
    }

    override fun onCleared() {
        super.onCleared()
        executors.shutdown()
    }

    data class FilmInfo(
        val filmCAtefory : Category,
        val image: Int,
        val title: String,
        val overview: String,
        val rating: Float,
        val date: String

    )
}