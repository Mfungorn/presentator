package com.example.presentator.domain.usecases

import com.example.presentator.data.repositories.PresentationRepository

class GetPresentationsUseCase(
    private val presentationRepository: PresentationRepository
) {
    suspend operator fun invoke() = presentationRepository.getItems()
}