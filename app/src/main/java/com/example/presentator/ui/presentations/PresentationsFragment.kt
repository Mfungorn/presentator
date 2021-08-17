package com.example.presentator.ui.presentations

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.presentator.R
import com.example.presentator.arch.StatefulFragment
import com.example.presentator.databinding.FragmentMainBinding
import com.example.presentator.databinding.ItemPresentationBinding
import com.example.presentator.domain.models.Presentation
import com.example.presentator.ui.common.ext.viewBinding
import com.google.android.material.snackbar.Snackbar
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import org.koin.android.viewmodel.ext.android.viewModel

class PresentationsFragment :
    StatefulFragment<PresentationsAction, PresentationsViewState, PresentationsEffect>(R.layout.fragment_main) {
    override val viewModel: PresentationsViewModel by viewModel()
    private val binding: FragmentMainBinding by viewBinding(FragmentMainBinding::bind)

    private val presentationsAdapter by lazy {
        ListDelegationAdapter(
            adapterDelegateViewBinding<Presentation, Presentation, ItemPresentationBinding>(
                viewBinding = { layoutInflater, parent ->
                    ItemPresentationBinding.inflate(
                        layoutInflater,
                        parent,
                        false
                    )
                }
            ) {
                bind {
                    with(binding) {
                        title.text = item.title
                        description.text = item.startAtUtc
                    }
                }
            }
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            list.apply {
                addItemDecoration(
                    DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
                )
                adapter = presentationsAdapter
            }
            buttonsContainer.setContent {
                MaterialTheme {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 4.dp),
                            shape = RoundedCornerShape(8.dp),
                            onClick = {
                                viewModel.sendAction {
                                    PresentationsAction.RefreshClick
                                }
                            }) {
                            Text(text = "Refresh")
                        }
                        Button(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 4.dp),
                            shape = RoundedCornerShape(8.dp),
                            onClick = {
                                viewModel.sendAction {
                                    PresentationsAction.ClearClick
                                }
                            }) {
                            Text(text = "Clear")
                        }
                    }
                }
            }
        }
    }

    override fun handleStateChanged(state: PresentationsViewState) {
        binding.progress.isVisible = state == PresentationsViewState.Loading
        with(binding) {
            when (state) {
                PresentationsViewState.Empty -> {
                }
                is PresentationsViewState.Error -> {
                    error.text = state.error
                }
                is PresentationsViewState.Idle -> {
                    presentationsAdapter.apply {
                        items = state.items
                        notifyDataSetChanged()
                    }
                }
                else -> {
                }
            }
        }
    }

    override fun handleEffect(effect: PresentationsEffect) {
        if (effect is PresentationsEffect.ShowToast)
            Snackbar
                .make(binding.root, effect.message, Snackbar.LENGTH_SHORT)
                .show()
    }
}
