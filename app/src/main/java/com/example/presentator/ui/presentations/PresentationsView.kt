package com.example.presentator.ui.presentations

import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import kotlinx.coroutines.flow.collect
import org.koin.androidx.compose.getViewModel

// https://sonique6784.medium.com/jetpack-compose-and-the-fall-of-fragments-54bf9977da1a
// https://proandroiddev.com/architecture-in-jetpack-compose-mvp-mvvm-mvi-17d8170a13fd
// https://medium.com/koin-developers/whats-next-with-koin-2-2-3-0-releases-6c5464ae5e3d

@Composable
fun PresentationsView() {
    val viewModel = getViewModel<PresentationsViewModel>()

    val snackbarHostState = remember { SnackbarHostState() }

    val state = viewModel.state.collectAsState()

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is PresentationsEffect.ShowToast ->
                    snackbarHostState.showSnackbar(effect.message)
            }
        }
    }


}