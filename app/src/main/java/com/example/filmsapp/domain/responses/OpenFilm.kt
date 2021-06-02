package com.example.filmsapp.domain.responses

import com.google.gson.annotations.SerializedName

data class OpenFilm(
        @SerializedName("original_title")
        val title : String,
        @SerializedName("overview")
        val overview : String,
        @SerializedName("release_date")
        val date : String,
        @SerializedName("vote_average")
        val rating : Int
)

