package com.example.filmsapp.ui.list

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.filmsapp.R
import com.example.filmsapp.ViewBindingDelegate
import com.example.filmsapp.databinding.FragmentListsCategoriesBinding
import com.example.filmsapp.di.App
import com.example.filmsapp.domain.Repository
import com.example.filmsapp.ui.list.adapter.CategoriesAdapter
import com.example.filmsapp.ui.router.RouterHolder
import javax.inject.Inject

class ListFragment : Fragment(R.layout.fragment_lists_categories) {

    private val categoryAdapter = CategoriesAdapter{
        (activity as? RouterHolder)?.router?.openFilm(it)
    }

    private val viewBinder: FragmentListsCategoriesBinding by ViewBindingDelegate(
        this , FragmentListsCategoriesBinding :: bind
    )

    @Inject
    lateinit var factory: CategoryListViewModelFactory


    private val viewModel: ListViewModel by viewModels() {
        factory
    }

    override fun onAttach(context: Context) {
        (requireActivity().applicationContext as App).appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            viewModel.fetchCategory()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinder.categoriesList.adapter = categoryAdapter
        viewBinder.swipeRefresh.setOnRefreshListener {
            viewModel.fetchCategory()
            viewBinder.swipeRefresh.isRefreshing = false
        }

        viewModel.categoryLiveData.observe(viewLifecycleOwner) {
            categoryAdapter.apply {
                setData(it)
                notifyDataSetChanged()
            }
        }

        viewModel.loadingLiveData.observe(viewLifecycleOwner) {
            viewBinder.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "some error", Toast.LENGTH_LONG).show()
        }
    }
}


class CategoryListViewModelFactory @Inject constructor(
        private val application: Application,
        private val repository: Repository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        ListViewModel(application, repository) as T
}


