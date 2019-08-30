package com.germanofilho.app.core.di

import com.germanofilho.app.feature.home.presentation.viewmodel.HomeViewModel
import com.germanofilho.app.feature.home.repository.HomeRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<HomeViewModel> {
        HomeViewModel(get())
    }

    single<HomeRepository>{
        HomeRepository()
    }
}