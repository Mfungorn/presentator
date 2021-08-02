package com.example.presentator

import android.app.Application
import com.example.presentator.di.networkModule
import com.example.presentator.di.repositoryModule
import com.example.presentator.di.uiModules
import com.example.presentator.di.usecaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PresentatorApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PresentatorApp)

            modules(networkModule + repositoryModule + usecaseModule + uiModules)
        }
    }
}