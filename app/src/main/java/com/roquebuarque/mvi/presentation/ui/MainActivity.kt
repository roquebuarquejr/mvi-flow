package com.roquebuarque.mvi.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.roquebuarque.mvi.R
import com.roquebuarque.mvi.presentation.CounterState
import com.roquebuarque.mvi.presentation.CounterSyncState
import com.roquebuarque.mvi.utils.setOnClickListenerFlow
import com.roquebuarque.mvi.presentation.reducer.CounterEvent
import com.roquebuarque.mvi.presentation.CounterViewModel
import com.roquebuarque.mvi.utils.handleErrors
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: CounterViewModel by viewModels()

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
                        .map { CounterEvent.Decrease },
                    btnIncrease.setOnClickListenerFlow()
                        .map { CounterEvent.Increase }
                )
            )
        }
    }

    private fun setupObserver() {
        viewModel.state.asLiveData().observe(this, Observer {
            render(it)
        })
    }

    private fun render(state: CounterState) {
        Log.d(MainActivity::class.java.name, "Render State $state")
        txtCounter.text = state.counter.value.toString()

        when (state.syncState) {
            CounterSyncState.Loading -> {
                progressBar.isVisible = true
            }
            CounterSyncState.Content -> {
                progressBar.isVisible = false
                txtErrorMessage.isVisible = false
            }
            is CounterSyncState.Message -> {
                progressBar.isVisible = false
                txtErrorMessage.isVisible = true
                txtErrorMessage.text = state.syncState.msg
            }
        }
    }
}
