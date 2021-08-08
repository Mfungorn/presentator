package com.example.presentator.arch

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.presentator.arch.utils.observe

abstract class StatefulFragment<
        Action : com.example.presentator.arch.Action,
        State : ViewState,
        Effect : com.example.presentator.arch.Effect
        >(@LayoutRes layoutRes: Int) : Fragment(layoutRes) {

    abstract val viewModel: StatefulViewModel<Action, State, Effect>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner, ::handleStateChanged)
        viewModel.effect.observe(viewLifecycleOwner, ::handleEffect)
    }

    abstract fun handleStateChanged(state: State)

    abstract fun handleEffect(effect: Effect)

}