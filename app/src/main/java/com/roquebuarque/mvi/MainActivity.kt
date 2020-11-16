package com.roquebuarque.mvi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.roquebuarque.mvi.data.CounterState
import com.roquebuarque.mvi.presentation.CounterIntent
import com.roquebuarque.mvi.presentation.CounterViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private val viewModel = CounterViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.intent(
            merge(
                btnDecrease.setOnClickListenerFlow().map { CounterIntent.Decrease(txtCounter.text.toString().toInt()) },
                btnIncrease.setOnClickListenerFlow().map { CounterIntent.Increase(txtCounter.text.toString().toInt()) }
            )
        )
        setupObserver()
    }

    private fun setupObserver() {
        lifecycleScope.launch {
            viewModel.stateFlow(this)
                .collect {
                    render(it)
                }
        }
    }

    private fun render(state: CounterState) {
        when (state) {
            is CounterState.Loading -> progressBar.isVisible = true
            is CounterState.Content -> {
                progressBar.isVisible = false
                txtCounter.text = state.counter.value.toString()
            }
            is CounterState.Error -> {
                progressBar.isVisible = false
                txtCounter.text = state.msg
            }
            is CounterState.Message -> Toast.makeText(this, state.msg, Toast.LENGTH_SHORT).show()
        }
    }
}

@FlowPreview
@ExperimentalCoroutinesApi
fun View.setOnClickListenerFlow()= callbackFlow {
        val listener = View.OnClickListener { offer(Unit) }
        setOnClickListener(listener)
        awaitClose {
            setOnClickListener(null)
        }
    }


