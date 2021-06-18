package com.example.filmsapp.domain

import android.os.Parcelable
import com.example.filmsapp.R
import com.example.filmsapp.domain.Direction
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film(
    val category : Category,
    val image: String,
    val title: String,
    val overview: String,
    val rating: Float,
    val date: String
): Parcelable

