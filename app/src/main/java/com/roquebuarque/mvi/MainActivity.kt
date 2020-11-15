package com.roquebuarque.mvi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.roquebuarque.mvi.data.CounterState
import com.roquebuarque.mvi.presentation.CounterViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel = CounterViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnIncrease.setOnClickListener {
            viewModel.increase()
        }

        btnDecrease.setOnClickListener {
            viewModel.decrease()
        }

        setupObserver()
    }

    private fun setupObserver() {
        viewModel.state.observe(this, Observer { state ->
           render(state)
        })
    }

    private fun render(state: CounterState) {
        when (state) {
            is CounterState.Loading -> progressBar.isVisible = true
            is CounterState.Content -> {
                progressBar.isVisible = false
                txtCounter.text = state.value.toString()
            }
            is CounterState.Error -> {
                progressBar.isVisible = false
                txtCounter.text = state.msg
            }
        }
    }
}