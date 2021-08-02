package com.example.presentator.di

import com.example.presentator.domain.usecases.GetPresentationsUseCase
import org.koin.dsl.module

val usecaseModule = module {

    factory { GetPresentationsUseCase(get()) }

}