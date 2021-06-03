package com.example.filmsapp.ui.list.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmsapp.R
import com.example.filmsapp.domain.*


class CategoriesAdapter(
        private val itemClicked: ItemClicked? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val repository: Repository = RepositoryApiImpl()
    private val data = mutableListOf<Category>()

    fun setData(dataToSet: List<Category>) {
        data.apply {
            clear()
            addAll(dataToSet)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        CategoriesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.lists_film, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val category = data[position]
        (holder as? CategoriesViewHolder)?.let { categoryHolder ->
            categoryHolder.categories.text = category.category.toString()
            val filmsData = ArrayList<Film>()
            if (position == 0) {
                val myArray = repository.getPopular()
                for (i in myArray) {
                    filmsData.add(i)
                }
            }
            if (position == 1) {
                val myArray = repository.getNowPlaying()
                for (i in myArray) {
                    filmsData.add(i)
                }
            }
            if (position == 2) {
                val myArray = repository.getTopRated()
                for (i in myArray) {
                    filmsData.add(i)
                }
            }
            if (position == 3) {
                val myArray = repository.getUpcoming()
                for (i in myArray) {
                    filmsData.add(i)
                }
            }
            val filmAdapter = FilmAdapter(itemClicked, filmsData)
            categoryHolder.listFilms.adapter = filmAdapter
            filmAdapter.notifyDataSetChanged()

        }
    }

    override fun getItemCount(): Int = data.size

    inner class CategoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categories: TextView = itemView.findViewById(R.id.categoriesTextView)
        val listFilms: RecyclerView = itemView.findViewById(R.id.filmsList)

    }
}