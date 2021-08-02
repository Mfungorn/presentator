package com.example.presentator.ui.presentations

import androidx.lifecycle.viewModelScope
import com.example.presentator.arch.StatefulViewModel
import com.example.presentator.domain.usecases.GetPresentationsUseCase
import kotlinx.coroutines.launch

class PresentationsViewModel(
    private val getPresentationsUseCase: GetPresentationsUseCase
) : StatefulViewModel<PresentationsEvent, PresentationsViewState, PresentationsEffect>(
    PresentationsViewState.Empty
) {

    override fun handleEvent(event: PresentationsEvent) {
        when (event) {
            PresentationsEvent.RefreshClick -> {
                refresh()
                setEffect { PresentationsEffect.ShowToast("Refreshed") }
            }
            PresentationsEvent.ClearClick -> {
                clearItems()
                setEffect { PresentationsEffect.ShowToast("Cleared") }
            }
        }
    }

    private fun clearItems() {
        setState { PresentationsViewState.Idle("Cleared", emptyList()) }
    }

    private fun refresh() {
        setState { PresentationsViewState.Loading }
        viewModelScope.launch {
            kotlin.runCatching { getPresentationsUseCase() }
                .onSuccess {
                    setState { PresentationsViewState.Idle("Refreshed", it) }
                }.onFailure {
                    setState { PresentationsViewState.Error(it.message.orEmpty()) }
                }
        }
    }
}
