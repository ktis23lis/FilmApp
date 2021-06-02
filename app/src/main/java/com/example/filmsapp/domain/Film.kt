package com.example.filmsapp.domain

import android.os.Parcelable
import com.example.filmsapp.R
import com.example.filmsapp.domain.Direction
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film(
    val category: Category,
    val image: Int,
    val title: String,
    val overview: String,
    val rating: Int,
    val date: String
): Parcelable

fun getHardPopular(): Array<Film> {
    return arrayOf(
        (Film(Category("a",(Direction.POPULAR)), R.drawable.tv, "a", "a", 1, "a")),
        (Film(Category("a",(Direction.POPULAR)), R.drawable.tv, "b", "a", 1, "a"))
    )
}

fun getHardNowPlaying(): Array<Film> {
    return arrayOf(
        (Film(Category("b",(Direction.NOW_PLAYING)), R.drawable.ratio, "a", "a", 1, "a")),
        (Film(Category("b",(Direction.NOW_PLAYING)), R.drawable.ratio, "b", "a", 1, "a"))
    )
}

fun getHardTopRated(): Array<Film> {
    return arrayOf(
        (Film(Category("c",(Direction.TOP_RATED)), R.drawable.emoticon, "a", "a", 1, "a")),
        (Film(Category("c",(Direction.TOP_RATED)), R.drawable.emoticon, "b", "a", 1, "a"))
    )
}

fun getHardUpcoming(): Array<Film> {
    return arrayOf(
        (Film(Category("d",(Direction.UPCOMING)), R.drawable.laptop, "a", "a", 1, "a")),
        (Film(Category("d",(Direction.UPCOMING)), R.drawable.laptop, "b", "a", 1, "a")),
        (Film(Category("d",(Direction.UPCOMING)), R.drawable.laptop, "b", "a", 1, "a"))
    )
}