package com.example.filmsapp.domain.responses

import android.os.Handler
import android.os.Looper
import com.example.filmsapp.R
import com.example.filmsapp.domain.Category
import com.example.filmsapp.domain.Direction
import com.example.filmsapp.domain.Error
import com.example.filmsapp.domain.Film
import com.example.filmsapp.domain.Success
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import java.net.HttpURLConnection
import java.net.URL

data class OpenFilm(
        val category: Category,
        @SerializedName("poster_path")
        val image : String,
        @SerializedName("original_title")
        val title: String,
        @SerializedName("overview")
        val overview: String,
        @SerializedName("release_date")
        val date: String,
        @SerializedName("vote_average")
        val rating: Float
)


