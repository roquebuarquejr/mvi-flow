package com.roquebuarque.mvi.presentation

import com.roquebuarque.mvi.data.Counter
import com.roquebuarque.mvi.data.CounterCallback
import com.roquebuarque.mvi.data.CounterModel
import com.roquebuarque.mvi.data.CounterRepository

class CounterPresenter {

    private lateinit var view: CounterView
    private val repository = CounterRepository

    fun start(view: CounterView) {
        this.view = view
    }

    fun increase() {
        view.render(CounterModel(isLoading = true))
        repository.increase(object : CounterCallback{
            override fun onSuccess(counter: Counter) {
                view.render(CounterModel(counter = counter, isLoading = false))
            }

            override fun onError(throwable: Throwable) {
                view.render(CounterModel(throwable = throwable, isLoading = false))
            }
        })
    }

    fun decrease(){
        view.render(CounterModel(isLoading = true))

        repository.decrease(object : CounterCallback{
            override fun onSuccess(counter: Counter) {
                view.render(CounterModel(counter = counter, isLoading = false))
            }

            override fun onError(throwable: Throwable) {
                view.render(CounterModel(throwable = throwable, isLoading = false))
            }
        })
    }
}