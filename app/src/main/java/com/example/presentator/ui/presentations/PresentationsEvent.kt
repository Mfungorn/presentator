package com.example.presentator.ui.presentations

import com.example.presentator.arch.Event

sealed class PresentationsEvent : Event {

    object RefreshClick : PresentationsEvent()

    object ClearClick : PresentationsEvent()

}