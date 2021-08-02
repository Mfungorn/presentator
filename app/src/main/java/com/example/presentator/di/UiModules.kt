package com.example.presentator.di

import com.example.presentator.ui.presentations.PresentationsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val presentationsModule = module {

    viewModel { PresentationsViewModel(get()) }

}

val uiModules = listOf(presentationsModule)