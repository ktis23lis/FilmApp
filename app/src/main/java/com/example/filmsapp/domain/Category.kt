package com.example.filmsapp.domain

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

enum class Direction{
    POPULAR,
    TOP_RATED,
    NOW_PLAYING,
    UPCOMING
}

@Parcelize
data class Category(val id: String, val category: Direction) : Parcelable
