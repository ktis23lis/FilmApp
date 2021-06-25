package com.example.filmsapp.ui.list

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.filmsapp.R
import com.example.filmsapp.ViewBindingDelegate
import com.example.filmsapp.databinding.FragmentListsCategoriesBinding
import com.example.filmsapp.di.App
import com.example.filmsapp.domain.Repository
import com.example.filmsapp.location.LocationRepository
import com.example.filmsapp.ui.list.adapter.CategoriesAdapter
import com.example.filmsapp.ui.router.MainRouter
import com.example.filmsapp.ui.router.RouterHolder
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class ListFragment : Fragment(R.layout.fragment_lists_categories) {

    private val categoryAdapter = CategoriesAdapter {
        (activity as? RouterHolder)?.router?.openFilm(it)
    }

    private val viewBinder: FragmentListsCategoriesBinding by ViewBindingDelegate(
        this, FragmentListsCategoriesBinding::bind
    )

    @Inject
    lateinit var factory: CategoryListViewModelFactory

    @Inject
    lateinit var router: MainRouter

    @Inject
    lateinit var locationRepository: LocationRepository


    private val locationViewModel: LocationViewModel by viewModels {
        factory
    }


    private val viewModel: ListViewModel by viewModels {
        factory
    }

    private val locationPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                locationViewModel.getLocation()

            } else {
                Toast.makeText(
                    requireContext(),
                    "Too sad, you cannot use this amazing feature!",
                    Toast.LENGTH_LONG
                ).show()
                // Failed pass
            }
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

        viewBinder.categoriesToolBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.map -> {
                    checkPermission(
                        locationPermissionRequest,
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ) {
                        locationViewModel.getLocation()

                    }
                    true

                }
                else -> {
                    false
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            locationViewModel.lastLocation.collect {
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            locationRepository.location().collect {
                Toast.makeText(requireContext(), "${it.latitude} ${it.longitude}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checkPermission(
        permissionLauncher: ActivityResultLauncher<String>,
        permission: String,
        onGranted: () -> Unit
    ) {
        when {
            (ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            )) == PackageManager.PERMISSION_GRANTED -> {
                onGranted.invoke()
            }

            shouldShowRequestPermissionRationale(permission) -> {
                Snackbar.make(
                    viewBinder.root,
                    "I need this permission very much!",
                    Snackbar.LENGTH_INDEFINITE
                ).setAction(
                    "Grant"
                ) {
                    permissionLauncher.launch(permission)
                }.show()
            }

            else -> {
                permissionLauncher.launch(permission)
            }
        }

    }
}


class CategoryListViewModelFactory @Inject constructor(
    private val application: Application,
    private val repository: Repository,
    private val locationRepository: LocationRepository
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =

        when {
            modelClass.isAssignableFrom(ListViewModel::class.java) ->
                ListViewModel(application, repository) as T

            modelClass.isAssignableFrom(LocationViewModel::class.java) ->
                LocationViewModel(locationRepository) as T
            else -> throw IllegalAccessException("")
        }

}



