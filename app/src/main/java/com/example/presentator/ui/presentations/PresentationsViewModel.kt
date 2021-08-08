package com.example.presentator.ui.presentations

import androidx.lifecycle.viewModelScope
import com.example.presentator.arch.StatefulViewModel
import com.example.presentator.domain.usecases.GetPresentationsUseCase
import kotlinx.coroutines.launch

class PresentationsViewModel(
    private val getPresentationsUseCase: GetPresentationsUseCase
) : StatefulViewModel<PresentationsAction, PresentationsViewState, PresentationsEffect>(
    PresentationsViewState.Empty
) {

    override fun handleAction(event: PresentationsAction) {
        when (event) {
            PresentationsAction.RefreshClick -> {
                refresh()
                setEffect { PresentationsEffect.ShowToast("Refreshed") }
            }
            PresentationsAction.ClearClick -> {
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
