package com.roquebuarque.mvi.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.roquebuarque.mvi.R
import com.roquebuarque.mvi.presentation.CounterState
import com.roquebuarque.mvi.presentation.CounterSyncState
import com.roquebuarque.mvi.utils.setOnClickListenerFlow
import com.roquebuarque.mvi.presentation.reducer.CounterEvent
import com.roquebuarque.mvi.presentation.CounterViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private val viewModel = CounterViewModel.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindEvents()
        setupObserver()
    }

    private fun bindEvents() {
        lifecycleScope.launch {
            viewModel.process(
                merge(
                    btnDecrease.setOnClickListenerFlow()
                        .map { CounterEvent.Decrease(txtCounter.text.toString().toInt()) },
                    btnIncrease.setOnClickListenerFlow()
                        .map { CounterEvent.Increase(txtCounter.text.toString().toInt()) }
                )
            )
        }
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            viewModel.state.collect { render(it) }
        }
    }

    private fun render(state: CounterState) {
        Log.d(MainActivity::class.java.name, state.toString())
        when (state.syncState) {
            CounterSyncState.Loading -> progressBar.isVisible = true
            CounterSyncState.Content -> {
                progressBar.isVisible = false
                txtCounter.text = state.counter.value.toString()
            }
            is CounterSyncState.Message -> {
                progressBar.isVisible = false
                txtCounter.text = state.syncState.msg
            }
        }
    }
}
