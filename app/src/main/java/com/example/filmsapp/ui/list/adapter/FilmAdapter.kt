package com.example.filmsapp.ui.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmsapp.R
import com.example.filmsapp.domain.Film


typealias ItemClicked = ((film: Film) -> Unit)

class FilmAdapter(
        private val itemClicked: ItemClicked? = null,
        private val list: ArrayList<Film>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            FilmViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_film, parent, false)
            )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val film = list[position]
        (holder as? FilmViewHolder)?.let { filmHolder ->
            filmHolder.image.setImageResource(film.image)
            filmHolder.nameFilm.text = film.title
            filmHolder.dateFilm.text = film.date

//            filmHolder.overviewFilm.text = film.overview
//            filmHolder.ratingFilm.text = film.rating.toString()


        }
    }

    override fun getItemCount(): Int = list.size

    inner class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imageView)
        val nameFilm: TextView = itemView.findViewById(R.id.films_NameTextView)
        val dateFilm: TextView = itemView.findViewById(R.id.dateReleaseTextView)

        //        val overviewFilm: TextView = itemView.findViewById(R.id.overview)
//        val ratingFilm: TextView = itemView.findViewById(R.id.raring)


        init {
            image.setOnClickListener {
                itemClicked?.invoke(list[adapterPosition])
            }

        }
    }
}