package com.example.presentator.data.repositories

import com.example.presentator.domain.models.Presentation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class PresentationRepository(
//    private val presentationService: PresentationService
) {

    suspend fun getItems() = withContext(Dispatchers.IO) {
        listOf(
            Presentation(1, "Location 1", "10:00", "Title #1", emptyList()),
            Presentation(2, "Location 2", "11:00", "Title #2", emptyList()),
            Presentation(3, "Location 3", "12:00", "Title #3", emptyList())
        )
    }
}