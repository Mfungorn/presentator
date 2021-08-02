package com.example.presentator.di

import com.example.presentator.data.api.PresentationService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory { provideOkHttpClient() }
//    factory { provideViewerService(get()) }
    single { provideRetrofit(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
    Retrofit.Builder()
        .baseUrl("todo")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

fun provideOkHttpClient(/*authInterceptor: AuthInterceptor*/): OkHttpClient =
    OkHttpClient().newBuilder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        //.addInterceptor(authInterceptor)
        .build()

fun provideViewerService(retrofit: Retrofit): PresentationService =
    retrofit.create(PresentationService::class.java)