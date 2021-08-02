package com.example.presentator.ui.presentations

import com.example.presentator.arch.Effect

sealed class PresentationsEffect : Effect {

    data class ShowToast(val message: String) : PresentationsEffect()

}