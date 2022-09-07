package com.example.asteroidapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.asteroidapp.R
import com.example.asteroidapp.adapters.AsteroidsListAdapter
import com.example.asteroidapp.adapters.OnClickListener
import com.example.asteroidapp.dataBase.AsteroidDatabase
import com.example.asteroidapp.databinding.FragmentAsteroidsDataListBinding
import com.example.asteroidapp.repositories.AsteroidRepository
import com.example.asteroidapp.viewModels.AsteroidsListViewModel
import com.example.asteroidapp.viewModels.ViewModelListFactory

class AsteroidsDataListFragment : Fragment(),MenuProvider {
    private lateinit var binding: FragmentAsteroidsDataListBinding
    private lateinit var viewModelListFactory: ViewModelListFactory
    private lateinit var viewModel: AsteroidsListViewModel
    private lateinit var repository: AsteroidRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        //binding process
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_asteroids_data_list,container,false)

        //variables initialization
        val databaseDao = AsteroidDatabase.getInstance(requireContext()).databaseDao
        repository = AsteroidRepository(databaseDao)
        viewModelListFactory = ViewModelListFactory(databaseDao, requireNotNull(activity).application) //requireActivity().application
        viewModel = ViewModelProvider(this,viewModelListFactory)[AsteroidsListViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.asteroidsListView.adapter = AsteroidsListAdapter(OnClickListener{
            findNavController().navigate(AsteroidsDataListFragmentDirections
                .actionAsteroidsDataListFragmentToAsteroidDetailsFragment(it))
        })

        viewModel.asteroidsList.observe(viewLifecycleOwner) {
            viewModel.loading.value = it?.isEmpty()
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            it?.let {
                binding.listLoadingBar.visibility = if(it) View.VISIBLE else View.GONE
            }
        }

        return  binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchTodayImage()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.options_menu,menu)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        viewModel.updateFilter(
            when(menuItem.itemId) {
                R.id.filter_items_option -> true
                else -> false
            }
        )
        return false
    }

}