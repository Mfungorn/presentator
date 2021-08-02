package com.example.presentator.di

import com.example.presentator.data.repositories.PresentationRepository
import org.koin.dsl.module

val repositoryModule = module {

    factory { PresentationRepository() }

}