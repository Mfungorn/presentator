package com.example.presentator.ui.presentations

import com.example.presentator.arch.ViewState
import com.example.presentator.domain.models.Presentation

sealed class PresentationsViewState : ViewState {

    object Empty : PresentationsViewState()

    object Loading : PresentationsViewState()

    data class Idle(
        val message: String,
        val items: List<Presentation>,
    ) : PresentationsViewState()

    data class Error(
        val error: String
    ) : PresentationsViewState()

}