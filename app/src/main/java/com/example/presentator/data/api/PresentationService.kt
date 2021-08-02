package com.example.presentator.data.api

import com.example.presentator.domain.models.Presentation
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface PresentationService {
    @GET("presentations.json")
    fun getAll(): Deferred<Response<List<Presentation>>>
}