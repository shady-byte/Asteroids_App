package com.example.asteroidapp.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.example.asteroidapp.R
import com.example.asteroidapp.databinding.FragmentAsteroidDetailsBinding
import com.example.asteroidapp.viewModels.AsteroidDetailsViewModel
import com.example.asteroidapp.viewModels.ViewModelDetailsFactory

class AsteroidDetailsFragment : Fragment(){
    private lateinit var binding: FragmentAsteroidDetailsBinding
    private lateinit var viewModelFactory: ViewModelDetailsFactory
    private lateinit var viewModel: AsteroidDetailsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {



        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_asteroid_details,container,false)

        val args = AsteroidDetailsFragmentArgs.fromBundle(requireArguments()).asteroidSelected
        viewModelFactory = ViewModelDetailsFactory(requireNotNull(activity).application,args)
        viewModel = ViewModelProvider(this,viewModelFactory)[AsteroidDetailsViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        //Listeners
        binding.helpCircleImage.setOnClickListener { showAlertDialog() }
        return binding.root
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage(R.string.help_circle_dialog_text)
            .setPositiveButton(R.string.aceptar,DialogInterface.OnClickListener { dialogInterface, i ->
                dialogInterface.dismiss()
            }).create()
            .show()
    }
}