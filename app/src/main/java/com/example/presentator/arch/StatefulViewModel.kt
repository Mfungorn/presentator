package com.example.presentator.arch

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class StatefulViewModel<
        Action : com.example.presentator.arch.Action,
        State : ViewState,
        Effect : com.example.presentator.arch.Effect
        >(initialState: State) : ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(initialState)
    val state: StateFlow<State> = _state.asStateFlow()

    private val _action: MutableSharedFlow<Action> = MutableSharedFlow()
    private val action: SharedFlow<Action> = _action.asSharedFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    val currentState: State
        get() = _state.value

    abstract val actionHandler: (event: Action) -> Unit

    init {
        observeActions()
    }

    fun sendAction(actionBuilder: () -> Action) {
        viewModelScope.launch { _action.emit(actionBuilder()) }
    }

    protected fun setState(reduce: State.() -> State) {
        _state.value = currentState.reduce()
    }

    protected fun setEffect(effectBuilder: () -> Effect) {
        viewModelScope.launch { _effect.send(effectBuilder()) }
    }

    private fun observeActions() {
        viewModelScope.launch {
            action.collect { actionHandler(it) }
        }
    }
}