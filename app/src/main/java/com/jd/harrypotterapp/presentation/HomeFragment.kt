package com.jd.harrypotterapp.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.jd.harrypotterapp.core.ViewModelFactory
import com.jd.harrypotterapp.presentation.di.DaggerHomeFragmentComponent
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: HomeViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDependencies()
    }

    override fun onStart() {
        super.onStart()
        viewModel.startDataRetrieval()
    }

    private fun injectDependencies() {
        DaggerHomeFragmentComponent
            .builder()
            .build()
            .inject(this)
    }
}