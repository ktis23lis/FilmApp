package com.example.filmsapp.ui.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmsapp.R
import com.example.filmsapp.domain.Film
import com.squareup.picasso.Picasso


typealias ItemClicked = ((film: Film) -> Unit)

class FilmAdapter(
        private val itemClicked: ItemClicked? = null,
        private val list: ArrayList<Film>) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>()
{

    private val STRING_POSTER = "https://image.tmdb.org/t/p/w500"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
            FilmViewHolder(
                    LayoutInflater.from(parent.context).inflate(R.layout.item_film, parent, false)
            )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val film = list[position]
        (holder as? FilmViewHolder)?.let { filmHolder ->
            Picasso.get().load(STRING_POSTER + film.image).into(filmHolder.image)
            filmHolder.nameFilm.text = film.title
            filmHolder.dateFilm.text = film.date




        }
    }

    override fun getItemCount(): Int = list.size

    inner class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imageView)
        val nameFilm: TextView = itemView.findViewById(R.id.films_NameTextView)
        val dateFilm: TextView = itemView.findViewById(R.id.dateReleaseTextView)


        init {
            image.setOnClickListener {
                itemClicked?.invoke(list[adapterPosition])
            }

        }
    }
}