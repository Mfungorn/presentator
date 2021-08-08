package com.example.presentator.ui.presentations

import com.example.presentator.arch.Action

sealed class PresentationsAction : Action {

    object RefreshClick : PresentationsAction()

    object ClearClick : PresentationsAction()

}