package com.roquebuarque.mvi.presentation

import com.roquebuarque.mvi.data.CounterCallback
import com.roquebuarque.mvi.data.CounterRepository

class CounterPresenter {

    private lateinit var view: CounterView
    private val repository = CounterRepository

    fun start(view: CounterView) {
        this.view = view
    }

    fun increase() {
        view.loading(true)
        repository.increase(object : CounterCallback{
            override fun onSuccess(value: Int) {
                view.loading(false)
                view.updateCounter(value)
            }

            override fun onError(throwable: Throwable) {
                view.loading(false)
                view.error(throwable.message ?: "error")
            }
        })
    }

    fun decrease(){
        view.loading(true)


        repository.decrease(object : CounterCallback{
            override fun onSuccess(value: Int) {
                view.loading(false)
                view.updateCounter(value)
            }

            override fun onError(throwable: Throwable) {
                view.loading(false)
                view.error(throwable.message ?: "error")
            }
        })
    }
}