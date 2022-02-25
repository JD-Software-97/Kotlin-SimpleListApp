package com.jd.harrypotterapp.presentation.di

import androidx.lifecycle.ViewModel
import com.jd.harrypotterapp.core.ViewModelKey
import com.jd.harrypotterapp.presentation.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class HomeFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun provideHomeViewModel(
        viewModel: HomeViewModel
    ): ViewModel
}
