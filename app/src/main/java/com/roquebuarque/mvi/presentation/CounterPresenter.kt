package com.roquebuarque.mvi.presentation

import com.roquebuarque.mvi.data.*

class CounterPresenter {

    private lateinit var view: CounterView
    private val repository = CounterRepository

    fun start(view: CounterView) {
        this.view = view
    }

    fun increase() {
        view.render(CounterState.Loading)
        repository.increase(object : CounterCallback {
            override fun onSuccess(counter: Counter) {
                view.render(CounterState.Content(counter.value))
            }

            override fun onError(throwable: Throwable) {
                view.render(
                    CounterState.Error(
                        msg = throwable.message ?: "Error"
                    )
                )
            }
        })
    }

    fun decrease() {
        view.render(CounterState.Loading)
        repository.decrease(object : CounterCallback {
            override fun onSuccess(counter: Counter) {
                view.render(CounterState.Content(counter.value))
            }

            override fun onError(throwable: Throwable) {
                view.render(
                    CounterState.Error(
                        msg = throwable.message ?: "Error"
                    )
                )
            }
        })
    }
}