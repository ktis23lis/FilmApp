package com.example.filmsapp.domain

import android.os.Parcelable
import com.example.filmsapp.R
import com.example.filmsapp.domain.Direction
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film(
    val category : Category,
    val image: Int,
    val title: String,
    val overview: String,
    val rating: Float,
    val date: String
): Parcelable

//fun getHardPopular(): ArrayList<Film> {
//    return arrayListOf(
//        (Film(Category("a",(Direction.POPULAR)), R.drawable.tv, "a", "a", 1.4f, "a")),
//        (Film(Category("a",(Direction.POPULAR)), R.drawable.tv, "b", "a", 1.4f, "a"))
//    )
//}
//
//fun getHardNowPlaying(): ArrayList<Film> {
//    return arrayListOf(
//        (Film(Category("b",(Direction.NOW_PLAYING)), R.drawable.ratio, "a", "a", 1.4f, "a")),
//        (Film(Category("b",(Direction.NOW_PLAYING)), R.drawable.ratio, "b", "a", 1.4f, "a"))
//    )
//}
//
//fun getHardTopRated(): ArrayList<Film> {
//    return arrayListOf(
//        (Film(Category("c",(Direction.TOP_RATED)), R.drawable.emoticon, "a", "a", 1.4f, "a")),
//        (Film(Category("c",(Direction.TOP_RATED)), R.drawable.emoticon, "b", "a", 1.4f, "a"))
//    )
//}
//
//fun getHardUpcoming(): ArrayList<Film> {
//    return arrayListOf(
//        (Film(Category("d",(Direction.UPCOMING)), R.drawable.laptop, "a", "a", 1.4f, "a")),
//        (Film(Category("d",(Direction.UPCOMING)), R.drawable.laptop, "b", "a", 1.4f, "a")),
//        (Film(Category("d",(Direction.UPCOMING)), R.drawable.laptop, "b", "a", 1.4f, "a"))
//    )
//}