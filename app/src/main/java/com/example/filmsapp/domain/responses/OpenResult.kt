package com.example.filmsapp.domain.responses

import com.google.gson.annotations.SerializedName

data class OpenResult(
        @SerializedName("results")
        val results : ArrayList<OpenFilm>
)