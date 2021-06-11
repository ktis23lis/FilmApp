package com.example.filmsapp.ui.list.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.filmsapp.R
import com.example.filmsapp.domain.*
import java.util.concurrent.Executors
import kotlin.Error


class CategoriesAdapter(
        private val itemClicked: ItemClicked? = null) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val executors = Executors.newSingleThreadExecutor()

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
                repository.getPopular(executor = executors, callback = {
                    when (it) {
                        is SuccessApi -> {
                            val arr = it.value
                            for (i in arr) {
                                filmsData.add(i )
                            }
                        }
                        is ErrorApi -> {
                            val e =it.value

                        }
                    }
                })
            }
            if (position == 1) {
                repository.getNowPlaying(executor = executors, callback = {
                    when(it){
                        is SuccessApi ->{
                            val arr = it.value
                            for (i in arr){
                                filmsData.add(i )
                            }
                        }
                        is ErrorApi -> {
                            val e =it.value

                        }
                    }
                })
            }
            if (position == 2) {
                repository.getTopRated(executor = executors, callback = {
                    when(it){
                        is SuccessApi ->{
                            val arr = it.value
                            for (i in arr){
                                filmsData.add(i)
                            }
                        }
                        is ErrorApi -> {
                            val e =it.value

                        }
                    }
                })
            }
            if (position == 3) {
                 repository.getUpcoming(executor = executors, callback = {
                    when(it){
                        is SuccessApi ->{
                            val arr = it.value
                            for (i in arr){
                                filmsData.add(i )
                            }

                        }
                        is ErrorApi -> {
                            val e =it.value

                        }
                    }
                })
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