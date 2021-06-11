package com.example.filmsapp.ui.list

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.filmsapp.R
import com.example.filmsapp.domain.*
import java.util.concurrent.Executors

class ListViewModel (
    private val app: Application,
    private val repository: Repository
): ViewModel() {

    private val executors = Executors.newSingleThreadExecutor()

    private val _loadingLiveData = MutableLiveData(false)
    private val _errorLiveData = MutableLiveData<String>()
    private val _categoryLiveData = MutableLiveData<List<Category>> ()

    val loadingLiveData : LiveData<Boolean> = _loadingLiveData
    val errorLiveData : LiveData<String> = _errorLiveData
    val categoryLiveData : LiveData<List<Category>> = _categoryLiveData


    fun fetchCategory(){
            _loadingLiveData.value = true
            repository.getCategory (executors){
                when (it) {
                    is Success -> {
                        val result : List<Category> =  it.value
                        _categoryLiveData.value = result

                    }
                    is Error -> {
                        _errorLiveData.value = app.getString(R.string.error)

                    }
                }
                _loadingLiveData.value = false
            }
    }
}