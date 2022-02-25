package com.jd.harrypotterapp.presentation.di

import com.jd.harrypotterapp.core.BaseComponent
import com.jd.harrypotterapp.core.PerFragment
import com.jd.harrypotterapp.presentation.HomeFragment
import dagger.Component

@Component(
    modules = [HomeFragmentModule::class]
)

@PerFragment
interface HomeFragmentComponent : BaseComponent<HomeFragment> {

    @Component.Builder
    interface Builder {
        fun build(): HomeFragmentComponent
    }
}