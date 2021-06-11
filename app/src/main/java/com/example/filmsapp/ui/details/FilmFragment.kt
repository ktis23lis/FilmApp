package com.example.filmsapp.ui.details

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import com.example.filmsapp.R
import com.example.filmsapp.ViewBindingDelegate
import com.example.filmsapp.databinding.FragmentDetailsBinding
import com.example.filmsapp.di.App
import com.example.filmsapp.domain.Film
import com.example.filmsapp.domain.Repository
import com.example.filmsapp.domain.RepositoryApiImpl
import javax.inject.Inject

class FilmFragment : Fragment(R.layout.fragment_details) {

    companion object {
        const val BUNDLE_EXTRA = "BUNDLE_EXTRA"
        fun newInstance(bundle: Bundle): FilmFragment {
            val fragment = FilmFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private val viewBinding: FragmentDetailsBinding by ViewBindingDelegate(
            this, FragmentDetailsBinding::bind
    )

    override fun onAttach(context: Context) {
        (requireActivity().applicationContext as App).appComponent.inject(this)
        super.onAttach(context)
    }

    @Inject
    lateinit var factory: MainViewModelFactory


    private val viewModel: FilmViewModel by viewModels() {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.fetchFilm()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(viewBinding) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.getParcelable<Film>(BUNDLE_EXTRA)?.let { film ->


            viewBinding?.retry?.setOnClickListener {
                viewModel.fetchFilm()
            }

            viewModel.errorLiveData.observe(viewLifecycleOwner) {
                val error = it ?: return@observe
                Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()
                with(viewBinding) {
                    retry.visibility = View.VISIBLE
                    image.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    name.visibility = View.GONE
                    date.visibility = View.GONE
                    raring.visibility = View.GONE
                    overview.visibility = View.GONE
                }
            }

            viewModel.loadingLiveData.observe(viewLifecycleOwner) {
                viewBinding.progressBar.visibility = if (it) View.VISIBLE else View.GONE

            }

            viewModel.filmLiveData.observe(viewLifecycleOwner) {
                val f = it ?: return@observe

                with(viewBinding) {
                    retry.visibility = View.GONE

                    image.apply {
                        visibility = View.VISIBLE
                        image.setImageResource(film.image)
                    }
                    name.apply {
                        visibility = View.VISIBLE
                        name.text = film.title
                    }
                    overview.apply {
                        visibility = View.VISIBLE
                        overview.text = film.overview
                    }
                    raring.apply {
                        visibility = View.VISIBLE
                        raring.text = film.rating.toString()
                    }
                    date.apply {
                        visibility = View.VISIBLE
                        date.text = film.date
                    }
                }
            }
        }
    }
}

    class MainViewModelFactory @Inject constructor(
            private val application: Application,
            private  val repository: Repository
            ) :
            ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
                FilmViewModel(application, repository) as T

    }


